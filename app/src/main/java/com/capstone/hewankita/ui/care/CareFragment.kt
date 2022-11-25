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
import com.capstone.hewankita.databinding.FragmentCareBinding
import com.capstone.hewankita.ui.bottom.BottomActivity
import com.capstone.hewankita.utils.OptionDialogFragment

class CareFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentCareBinding? = null
    private val binding get() = _binding!!

    private lateinit var btnNext: ButtonValidation
    private lateinit var tvOutlet: EditTextValidation
    private lateinit var tvCheckIn: EditTextValidation
    private lateinit var tvCheckOut: EditTextValidation
    private lateinit var tvTimeOfArrival: EditTextValidation

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
        if(v == binding.tvOutlet) {
            val mOptionDialogFragment = OptionDialogFragment()
            val mFragmentManager = childFragmentManager
            mOptionDialogFragment.show(mFragmentManager, OptionDialogFragment::class.java.simpleName)
        }
        if (v === binding.tvCheckIn) {
            val c = Calendar.getInstance()
            mYear = c[Calendar.YEAR]
            mMonth = c[Calendar.MONTH]
            mDay = c[Calendar.DAY_OF_MONTH]

            val datePickerDialog = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
                binding.tvCheckIn.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
            }, mYear, mMonth, mDay)

            datePickerDialog.show()
        }
        if (v === binding.tvCheckOut) {
            val c = Calendar.getInstance()
            mYear = c[Calendar.YEAR]
            mMonth = c[Calendar.MONTH]
            mDay = c[Calendar.DAY_OF_MONTH]

            val datePickerDialog = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
                binding.tvCheckOut.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
            }, mYear, mMonth, mDay)

            datePickerDialog.show()
        }
        if (v === binding.tvTimeOfArrival) {
            val c = Calendar.getInstance()
            mHour = c[Calendar.HOUR_OF_DAY]
            mMinute = c[Calendar.MINUTE]

            val timePickerDialog = TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
                binding.tvTimeOfArrival.setText("$hourOfDay:$minute")
            }, mHour, mMinute, true)

            timePickerDialog.show()
        }

        if(v == binding.btnNext) {
            val intent = Intent(requireActivity(), BottomActivity::class.java)
            startActivity(intent)
            Toast.makeText(requireActivity(), resources.getString(R.string.booking_success), Toast.LENGTH_SHORT).show()
            activity?.finish()
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
}