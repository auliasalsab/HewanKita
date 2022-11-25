package com.capstone.hewankita.ui.vaccination

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
import com.capstone.hewankita.data.local.database.DatabaseContract
import com.capstone.hewankita.data.local.database.ScheduleHelper
import com.capstone.hewankita.data.local.entity.ScheduleAll
import com.capstone.hewankita.databinding.FragmentMenuBinding
import com.capstone.hewankita.ui.bottom.BottomActivity
import com.capstone.hewankita.ui.grooming.GroomingFragment
import com.capstone.hewankita.utils.OptionDialogFragment

class VaccinationFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var btnNext: ButtonValidation
    private lateinit var tvOutlet: EditTextValidation
    private lateinit var tvBookingDate: EditTextValidation
    private lateinit var tvBookingTime: EditTextValidation

    private var mYear = 0
    private var mMonth = 0
    private var mDay = 0
    private var mHour = 0
    private var mMinute = 0

    private var scheduleAll: ScheduleAll? = null
    private var position: Int = 0
    private lateinit var scheduleHelper: ScheduleHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        activity?.title = getString(R.string.vaccination)

        binding.tvBookingDate.setOnClickListener(this)
        binding.tvBookingTime.setOnClickListener(this)
        binding.tvOutlet.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)

        btnNext = binding.btnNext
        tvOutlet = binding.tvOutlet
        tvBookingDate = binding.tvBookingDate
        tvBookingTime = binding.tvBookingTime

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

        scheduleHelper = ScheduleHelper.getInstance(requireContext())
        scheduleHelper.open()

        return root
    }

    private fun setButton() {
        val tvOutlet = tvOutlet.text
        val tvBookingDate = tvBookingDate.text
        val tvBookingTime = tvBookingTime.text
        btnNext.isEnabled = tvOutlet != null && tvOutlet.toString().isNotEmpty() &&
                tvBookingDate != null && tvBookingDate.toString().isNotEmpty() &&
                tvBookingTime != null && tvBookingTime.toString().isNotEmpty()
    }

    override fun onClick(v: View?) {
        if(v == binding.tvOutlet) {
            val mOptionDialogFragment = OptionDialogFragment()
            val mFragmentManager = childFragmentManager
            mOptionDialogFragment.show(mFragmentManager, OptionDialogFragment::class.java.simpleName)
        }
        if (v === binding.tvBookingDate) {
            val c = Calendar.getInstance()
            mYear = c[Calendar.YEAR]
            mMonth = c[Calendar.MONTH]
            mDay = c[Calendar.DAY_OF_MONTH]

            val datePickerDialog = DatePickerDialog(requireActivity(), { _, year, monthOfYear, dayOfMonth ->
                binding.tvBookingDate.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
            }, mYear, mMonth, mDay)

            datePickerDialog.show()
        }
        if (v === binding.tvBookingTime) {
            val c = Calendar.getInstance()
            mHour = c[Calendar.HOUR_OF_DAY]
            mMinute = c[Calendar.MINUTE]

            val timePickerDialog = TimePickerDialog(requireActivity(), { _, hourOfDay, minute ->
                binding.tvBookingTime.setText("$hourOfDay:$minute")
            }, mHour, mMinute, true)

            timePickerDialog.show()
        }
        if (v == binding.btnNext) {
            val outlet = binding.tvOutlet.text.toString().trim()
            val bookingDate = binding.tvBookingDate.text.toString().trim()
            val bookingTime = binding.tvBookingTime.text.toString().trim()

            scheduleAll?.outlet = outlet
            scheduleAll?.booking_date = bookingDate
            scheduleAll?.booking_time = bookingTime

            val intent = Intent()
            intent.putExtra(EXTRA_SCHEDULE, scheduleAll)
            intent.putExtra(EXTRA_SCHEDULE, position)

            val values = ContentValues()
            values.put(DatabaseContract.VaccinationColumns.OUTLET, outlet)
            values.put(DatabaseContract.VaccinationColumns.BOOKING_DATE, bookingDate)
            values.put(DatabaseContract.VaccinationColumns.BOOKING_TIME, bookingTime)

            val result = scheduleHelper.insertTableVaccination(values)

            if (result > 0) {
                scheduleAll?.id = result.toInt()
                Toast.makeText(requireActivity(), resources.getString(R.string.booking_success), Toast.LENGTH_SHORT).show()
                activity?.setResult(RESULT_ADD, intent)
                activity?.finish()
            } else {
                Toast.makeText(requireActivity(), "Gagal menambah data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    internal var optionDialogListener: OptionDialogFragment.OnOptionDialogListener = object : OptionDialogFragment.OnOptionDialogListener {
        override fun onOptionChosen(text: String?) {
            binding.tvOutlet.setText(text)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_SCHEDULE = "extra_schedule"
        const val RESULT_ADD = 101
    }
}