package com.capstone.hewankita.ui.care

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
import com.capstone.hewankita.databinding.FragmentCareBinding
import com.capstone.hewankita.utils.Constants
import com.capstone.hewankita.utils.OptionDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class CareFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentCareBinding? = null
    private val binding get() = _binding!!

    private lateinit var btnNext: ButtonValidation
    private lateinit var tvOutlet: EditTextValidation
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
        tvOutlet = binding.tvOutlet
        tvCheckIn = binding.tvCheckIn
        tvCheckOut = binding.tvCheckOut
        tvTimeOfArrival = binding.tvTimeOfArrival

        auth = FirebaseAuth.getInstance()

        setButton()

        tvOutlet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setButton()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

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

        return root
    }

    private fun setButton() {
        val tvOutlet = tvOutlet.text
        val tvCheckIn = tvCheckIn.text
        val tvCheckOut = tvCheckOut.text
        val tvTimeOfArrival = tvTimeOfArrival.text
        btnNext.isEnabled = tvOutlet != null && tvOutlet.toString().isNotEmpty() &&
                tvCheckIn != null && tvCheckIn.toString().isNotEmpty() &&
                tvCheckOut != null && tvCheckOut.toString().isNotEmpty() &&
                tvTimeOfArrival != null && tvTimeOfArrival.toString().isNotEmpty()
    }

    override fun onClick(v: View?) {
        if (v == binding.tvOutlet) {
            val mOptionDialogFragment = OptionDialogFragment()
            val mFragmentManager = childFragmentManager
            mOptionDialogFragment.show(
                mFragmentManager,
                OptionDialogFragment::class.java.simpleName
            )
        }
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
                if (!compareTwoTimes(getCurrentTime()!!, time)) {
                    binding.tvTimeOfArrival.setText(getString(R.string.cannot_use_past_time))
                } else {
                    binding.tvTimeOfArrival.setText(time)
                }

            }, mHour, mMinute, false)

            timePicker.show()
        }
        if (v == binding.btnNext) {
            val outlet = binding.tvOutlet.text.toString().trim()
            val checkIn =
                "${getString(R.string.checkIn)}:  ${binding.tvCheckIn.text.toString().trim()}"
            val checkOut =
                "${getString(R.string.checkOut)}:  ${binding.tvCheckOut.text.toString().trim()}"
            val timeOfArrival = "${getString(R.string.timeOfArrival)}:  ${
                binding.tvTimeOfArrival.text.toString().trim()
            }"
            addService(outlet, checkIn, checkOut, timeOfArrival)

            Toast.makeText(
                requireActivity(),
                resources.getString(R.string.booking_success),
                Toast.LENGTH_SHORT
            ).show()
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

    internal var optionDialogListener: OptionDialogFragment.OnOptionDialogListener =
        object : OptionDialogFragment.OnOptionDialogListener {
            override fun onOptionChosen(text: String?) {
                binding.tvOutlet.setText(text)
            }
        }

    private fun addService(outlet: String, checkIn: String, checkOut: String, timeOA: String) {
        val user: FirebaseUser? = auth.currentUser
        val userEmail: String? = user!!.email

        val database = Firebase.database
        val databaseReference = database.getReference(Constants.TABLE_DATA_SERVICE)
            .child(Constants.CHILD_SERVICE_CARE_SERVICE)

        val hashMap = mapOf<String, Any>(
            Constants.CONST_SERVICE_OUTLET to outlet,
            Constants.CONST_SERVICE_CHECK_IN to checkIn,
            Constants.CONST_SERVICE_CHECK_OUT to checkOut,
            Constants.CONST_SERVICE_TIME_OA to timeOA,
            Constants.CONST_USER_EMAIL to userEmail.toString()
        )

        databaseReference.push().setValue(hashMap)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}