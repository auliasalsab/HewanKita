package com.capstone.hewankita.ui.bottom.ui.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.capstone.hewankita.R
import com.capstone.hewankita.data.remote.response.LoginResult
import com.capstone.hewankita.data.session.UserSession
import com.capstone.hewankita.databinding.FragmentHomeBinding
import com.capstone.hewankita.ui.care.CareActivity
import com.capstone.hewankita.ui.consultation.ConsultationActivity
import com.capstone.hewankita.ui.doctor.DoctorActivity
import com.capstone.hewankita.ui.doctor.DoctorFragment
import com.capstone.hewankita.ui.grooming.GroomingActivity
import com.capstone.hewankita.ui.login.LoginActivity
import com.capstone.hewankita.ui.vaccination.VaccinationActivity


class HomeFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var pref: UserSession

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        activity?.setTitle(R.string.title_home)

        pref = UserSession(requireContext())

        binding.ivDoctor.setOnClickListener(this)
        binding.ivConsultation.setOnClickListener(this)
        binding.ivGrooming.setOnClickListener(this)
        binding.ivCare.setOnClickListener(this)
        binding.ivVaccination.setOnClickListener(this)
        return root
    }

    override fun onClick(v: View) {
        if(v == binding.ivDoctor) {
            val intent = Intent(requireActivity(), DoctorActivity::class.java)
            startActivity(intent)
        }
        if(v == binding.ivConsultation) {
            val intent = Intent(requireActivity(), ConsultationActivity::class.java)
            startActivity(intent)
        }
        if(v == binding.ivGrooming) {
            val intent = Intent(requireActivity(), GroomingActivity::class.java)
            startActivity(intent)
        }
        if(v == binding.ivCare) {
            val intent = Intent(requireActivity(), CareActivity::class.java)
            startActivity(intent)
        }
        if(v == binding.ivVaccination) {
            val intent = Intent(requireActivity(), VaccinationActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}