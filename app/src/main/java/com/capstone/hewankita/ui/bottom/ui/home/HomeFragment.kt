package com.capstone.hewankita.ui.bottom.ui.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.capstone.hewankita.R
import com.capstone.hewankita.databinding.FragmentHomeBinding
import com.capstone.hewankita.ui.care.CareActivity
import com.capstone.hewankita.ui.care.CareList
import com.capstone.hewankita.ui.consultation.ConsultationActivity
import com.capstone.hewankita.ui.consultation.ListConsultant
import com.capstone.hewankita.ui.doctor.DoctorActivity
import com.capstone.hewankita.ui.doctor.DoctorFragment
import com.capstone.hewankita.ui.doctor.DoctorList
import com.capstone.hewankita.ui.grooming.GroomingActivity
import com.capstone.hewankita.ui.grooming.GroomingList
import com.capstone.hewankita.ui.login.LoginActivity
import com.capstone.hewankita.ui.vaccination.VaccinationActivity
import com.capstone.hewankita.ui.vaccination.VaccinationList
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        activity?.setTitle(R.string.title_home)

        binding.ivDoctor.setOnClickListener(this)
        binding.ivConsultation.setOnClickListener(this)
        binding.ivGrooming.setOnClickListener(this)
        binding.ivCare.setOnClickListener(this)
        binding.ivVaccination.setOnClickListener(this)
        return root
    }

    override fun onClick(v: View) {
        if(v == binding.ivDoctor) {
            val intent = Intent(requireActivity(), DoctorList::class.java)
            startActivity(intent)
        }
        if(v == binding.ivConsultation) {
            val intent = Intent(requireActivity(), ListConsultant::class.java)
            startActivity(intent)
        }
        if(v == binding.ivGrooming) {
            val intent = Intent(requireActivity(), GroomingList::class.java)
            startActivity(intent)
        }
        if(v == binding.ivCare) {
            val intent = Intent(requireActivity(), CareList::class.java)
            startActivity(intent)
        }
        if(v == binding.ivVaccination) {
            val intent = Intent(requireActivity(), VaccinationList::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.localization -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.logout -> {
                logOut()
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(requireActivity(), resources.getString(R.string.logout_success), Toast.LENGTH_SHORT).show()
                activity?.finish()
                true
            }
            else -> false
        }
    }

    private fun logOut(){
        Firebase.auth.signOut()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}