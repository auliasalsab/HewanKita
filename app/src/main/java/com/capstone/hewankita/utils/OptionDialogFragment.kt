package com.capstone.hewankita.utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.capstone.hewankita.R
import com.capstone.hewankita.databinding.FragmentOptionDialogBinding
import com.capstone.hewankita.ui.care.CareFragment
import com.capstone.hewankita.ui.doctor.DoctorFragment
import com.capstone.hewankita.ui.grooming.GroomingFragment
import com.capstone.hewankita.ui.vaccination.VaccinationFragment

class OptionDialogFragment : DialogFragment() {
    private var _binding: FragmentOptionDialogBinding? = null
    private val binding get() = _binding!!
    private var optionDialogListener: OnOptionDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptionDialogBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChoose.setOnClickListener {
            val checkedRadioButtonId = binding.rgOptions.checkedRadioButtonId
            if (checkedRadioButtonId != -1) {
                val coach: String? = when (checkedRadioButtonId) {
                    R.id.rb_1 -> binding.rb1.text.toString().trim()
                    R.id.rb_2 -> binding.rb2.text.toString().trim()
                    else -> null
                }
                optionDialogListener?.onOptionChosen(coach)
                dialog?.dismiss()
            }
        }
        binding.btnClose.setOnClickListener {
            dialog?.cancel()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragment = parentFragment
        if (fragment is DoctorFragment) {
            this.optionDialogListener = fragment.optionDialogListener
        }
        if (fragment is GroomingFragment) {
            this.optionDialogListener = fragment.optionDialogListener
        }
        if (fragment is VaccinationFragment) {
            this.optionDialogListener = fragment.optionDialogListener
        }
        if (fragment is CareFragment) {
            this.optionDialogListener = fragment.optionDialogListener
        }
    }
    override fun onDetach() {
        super.onDetach()
        this.optionDialogListener = null
    }

    interface OnOptionDialogListener {
        fun onOptionChosen(text: String?)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}