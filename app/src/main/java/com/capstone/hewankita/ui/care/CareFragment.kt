package com.capstone.hewankita.ui.care

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.capstone.hewankita.R
import com.capstone.hewankita.customview.ButtonValidation
import com.capstone.hewankita.customview.EditTextValidation
import com.capstone.hewankita.data.AllData
import com.capstone.hewankita.databinding.FragmentCareBinding
import com.capstone.hewankita.ui.bottom.BottomActivity
import com.capstone.hewankita.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CareFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentCareBinding? = null
    private val binding get() = _binding!!

    private lateinit var btnNext: ButtonValidation
    private lateinit var tvCheckIn: EditTextValidation
    private lateinit var tvCheckOut: EditTextValidation
    private lateinit var tvTimeOfArrival: EditTextValidation
    private lateinit var auth: FirebaseAuth

    private var mYear = 0
    private var mMonth = 0
    private var mDay = 0
    private var mHour = 0
    private var mMinute = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCareBinding.inflate(inflater, container, false)
        val root: View = binding.root

        activity?.title = getString(R.string.care)

        binding.tvOutlet.setOnClickListener(this)
        binding.tvCheckIn.setOnClickListener(this)
        binding.tvCheckOut.setOnClickListener(this)
        binding.tvTimeOfArrival.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)

        btnNext = binding.btnNext
        tvCheckIn = binding.tvCheckIn
        tvCheckOut = binding.tvCheckOut
        tvTimeOfArrival = binding.tvTimeOfArrival

        auth = FirebaseAuth.getInstance()

        setButton()

        tvCheckIn.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setButton()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        tvCheckOut.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setButton()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        tvTimeOfArrival.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setButton()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        getDataOutlet()

        return root
    }

    private fun setButton() {
        val tvCheckIn = tvCheckIn.text
        val tvCheckOut = tvCheckOut.text
        val tvTimeOfArrival = tvTimeOfArrival.text
        btnNext.isEnabled =
                tvCheckIn != null && tvCheckIn.toString().isNotEmpty() &&
                tvCheckOut != null && tvCheckOut.toString().isNotEmpty() &&
                tvTimeOfArrival != null && tvTimeOfArrival.toString().isNotEmpty()
    }

    override fun onClick(v: View?) {
        if (v === binding.tvCheckIn) {
            val c = Calendar.getInstance()
            mYear = c[Calendar.YEAR]
            mMonth = c[Calendar.MONTH]
            mDay = c[Calendar.DAY_OF_MONTH]

            val datePickerDialog =
                DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
                    binding.tvCheckIn.setText(StringBuilder("$dayOfMonth-${monthOfYear + 1}-$year"))
                }, mYear, mMonth, mDay)

            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 1000
            datePickerDialog.show()
        }
        if (v === binding.tvCheckOut) {
            val c = Calendar.getInstance()
            mYear = c[Calendar.YEAR]
            mMonth = c[Calendar.MONTH]
            mDay = c[Calendar.DAY_OF_MONTH]

            val datePickerDialog =
                DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
                    binding.tvCheckOut.setText(StringBuilder("$dayOfMonth-${monthOfYear + 1}-$year"))
                }, mYear, mMonth, mDay)

            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
            datePickerDialog.show()
        }
        if (v === binding.tvTimeOfArrival) {
            var amPm = ""
            val c = Calendar.getInstance()
            mHour = c[Calendar.HOUR_OF_DAY]
            mMinute = c[Calendar.MINUTE]

            val timePicker = TimePickerDialog(requireActivity(), { _, hourOfDay, minute ->
                c.set(Calendar.HOUR_OF_DAY, hourOfDay)
                c.set(Calendar.MINUTE, minute)
                if (c.get(Calendar.AM_PM) == Calendar.AM) {
                    amPm = "AM"
                } else if (c.get(Calendar.AM_PM) == Calendar.PM) {
                    amPm = "PM"
                }
                val hrs = if (c.get(Calendar.HOUR) == 0) "12" else c.get(Calendar.HOUR).toString()
                val showHrs =
                    if (c.get(Calendar.HOUR) <= 9 && c.get(Calendar.HOUR) != 0) "0$hrs" else hrs
                val showMinutes =
                    if (c.get(Calendar.MINUTE) <= 9) "0${c.get(Calendar.MINUTE)}" else "${
                        c.get(Calendar.MINUTE)
                    }"
                val time = "$showHrs:$showMinutes $amPm"
                binding.tvTimeOfArrival.setText(time)

            }, mHour, mMinute, false)

            timePicker.show()
        }
        if (v == binding.btnNext) {
            val outletId = binding.tvOutletId.text.toString().trim()
            val outletEmail = binding.tvOutletEmail.text.toString().trim()
            val outlet = binding.tvOutlet.text.toString().trim()
            val checkIn =
                "${getString(R.string.checkIn)}:  ${binding.tvCheckIn.text.toString().trim()}"
            val checkOut =
                "${getString(R.string.checkOut)}:  ${binding.tvCheckOut.text.toString().trim()}"
            val timeOfArrival = "${getString(R.string.timeOfArrival)}:  ${
                binding.tvTimeOfArrival.text.toString().trim()
            }"
            addService(outlet, checkIn, checkOut, timeOfArrival, outletId, outletEmail)

            Toast.makeText(
                requireActivity(),
                resources.getString(R.string.booking_success),
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(requireActivity(), BottomActivity::class.java)
            startActivity(intent)
            activity?.finish()
        } else {
            Toast.makeText(
                requireActivity(),
                getString(R.string.booking_failed),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun addService(outlet: String, checkIn: String, checkOut: String, timeOA: String, outletEmail: String, outletId: String) {
        val user: FirebaseUser? = auth.currentUser
        val userEmail: String? = user!!.email

        val database = Firebase.database
        val databaseReference = database.getReference(Constants.TABLE_DATA_SERVICE)
            .child(Constants.CHILD_SERVICE_CARE_SERVICE)
        val key: String = databaseReference.push().key.toString()

        val hashMap = mapOf<String, Any>(
            Constants.CONST_SERVICE_OUTLET to outlet,
            Constants.CONST_SERVICE_CHECK_IN to checkIn,
            Constants.CONST_SERVICE_CHECK_OUT to checkOut,
            Constants.CONST_SERVICE_TIME_OA to timeOA,
            Constants.CONST_USER_EMAIL to userEmail.toString(),
            Constants.CONST_KEY to key,
            Constants.CONST_SERVICE_OUTLET_ID to outletId,
            Constants.CONST_SERVICE_OUTLET_EMAIL to outletEmail
        )

        databaseReference.push().setValue(hashMap)
    }

    private fun getDataOutlet(){
        val outletID = requireActivity().intent.getStringExtra(OUTLET_ID)

        val dbRef = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATA_USER).child(outletID!!)
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(AllData::class.java)
                binding.tvOutlet.text = user!!.Username
                binding.tvOutletId.text = user.Key
                binding.tvOutletEmail.text = user.Email
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val OUTLET_ID = "Id"
    }
}