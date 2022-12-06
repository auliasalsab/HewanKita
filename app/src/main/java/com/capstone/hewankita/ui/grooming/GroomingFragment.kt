package com.capstone.hewankita.ui.grooming

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
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
import com.capstone.hewankita.databinding.FragmentMenuBinding
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
import java.text.SimpleDateFormat

class GroomingFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var btnNext: ButtonValidation
    private lateinit var tvBookingDate: EditTextValidation
    private lateinit var tvBookingTime: EditTextValidation
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
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        activity?.title = getString(R.string.grooming)

        binding.tvBookingDate.setOnClickListener(this)
        binding.tvBookingTime.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)

        btnNext = binding.btnNext
        tvBookingDate = binding.tvBookingDate
        tvBookingTime = binding.tvBookingTime
        auth = FirebaseAuth.getInstance()

        setButton()


        tvBookingDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setButton()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        tvBookingTime.addTextChangedListener(object : TextWatcher {
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
        val tvBookingDate = tvBookingDate.text
        val tvBookingTime = tvBookingTime.text
        btnNext.isEnabled =
                tvBookingDate != null && tvBookingDate.toString().isNotEmpty() &&
                tvBookingTime != null && tvBookingTime.toString().isNotEmpty()
    }

    override fun onClick(v: View?) {
        if (v === binding.tvBookingDate) {
            val c = Calendar.getInstance()
            mYear = c[Calendar.YEAR]
            mMonth = c[Calendar.MONTH]
            mDay = c[Calendar.DAY_OF_MONTH]

            val datePickerDialog =
                DatePickerDialog(requireActivity(), { _, year, monthOfYear, dayOfMonth ->
                    binding.tvBookingDate.setText(StringBuilder("$dayOfMonth-${monthOfYear + 1}-$year"))
                }, mYear, mMonth, mDay)

            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
            datePickerDialog.show()
        }
        if (v === binding.tvBookingTime) {
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
                if (!compareTwoTimes(getCurrentTime()!!, time)) {
                    binding.tvBookingTime.setText(getString(R.string.cannot_use_past_time))
                } else {
                    binding.tvBookingTime.setText(time)
                }

            }, mHour, mMinute, false)

            timePicker.show()
        }
        if (v == binding.btnNext) {
            val outletId = binding.tvOutletId.text.toString().trim()
            val outletEmail = binding.tvOutletEmail.text.toString().trim()
            val outlet = binding.tvOutlet.text.toString().trim()
            val bookingDate = "${getString(R.string.bookingDate)}:  ${
                binding.tvBookingDate.text.toString().trim()
            }"
            val bookingTime = "${getString(R.string.bookingTime)}:  ${
                binding.tvBookingTime.text.toString().trim()
            }"

            addService(outlet, bookingDate, bookingTime, outletId, outletEmail)
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

    private fun getCurrentTime(): String? {
        val simpleDateFormat = SimpleDateFormat("hh:mm a")
        return simpleDateFormat.format(Calendar.getInstance().time)
    }

    private fun compareTwoTimes(fromTime: String, currentTime: String): Boolean {
        val sdf = SimpleDateFormat("hh:mm a")
        val time1 = sdf.parse(fromTime)
        val time2 = sdf.parse(currentTime)
        return !time2!!.before(time1)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addService(outlet: String, bookingDate: String, bookingTime: String, outletEmail: String, outletId: String) {
        val user: FirebaseUser? = auth.currentUser
        val userEmail: String? = user!!.email

        val database = Firebase.database
        val databaseReference = database.getReference(Constants.TABLE_DATA_SERVICE)
            .child(Constants.CHILD_SERVICE_GROOMING_SERVICE)
        val key: String = databaseReference.push().key.toString()

        val hashMap = mapOf<String, Any>(
            Constants.CONST_SERVICE_OUTLET to outlet,
            Constants.CONST_SERVICE_DATE to bookingDate,
            Constants.CONST_SERVICE_TIME to bookingTime,
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

    companion object{
        const val OUTLET_ID = "Id"
    }
}