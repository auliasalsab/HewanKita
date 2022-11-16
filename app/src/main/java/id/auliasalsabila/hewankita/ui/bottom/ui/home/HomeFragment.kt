package id.auliasalsabila.hewankita.ui.bottom.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.auliasalsabila.hewankita.databinding.FragmentHomeBinding
import id.auliasalsabila.hewankita.ui.care.CareActivity
import id.auliasalsabila.hewankita.ui.doctor.DoctorActivity
import id.auliasalsabila.hewankita.ui.grooming.GroomingActivity
import id.auliasalsabila.hewankita.ui.vaccination.VaccinationActivity

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
        /*if(v == binding.ivConsultation) {
            val intent = Intent(this@HomeActivity, ConsultationActivity::class.java)
            startActivity(intent)
        }*/
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}