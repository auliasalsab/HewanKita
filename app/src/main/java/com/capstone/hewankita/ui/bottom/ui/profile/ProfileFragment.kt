package com.capstone.hewankita.ui.bottom.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.capstone.hewankita.R
import com.capstone.hewankita.data.remote.response.LoginResult
import com.capstone.hewankita.data.session.UserSession
import com.capstone.hewankita.databinding.FragmentProfileBinding
import com.capstone.hewankita.ui.CekJadwalActivity
import com.capstone.hewankita.ui.EditProfileActivity
import com.capstone.hewankita.ui.InformationActivity
import com.capstone.hewankita.ui.MyPetActivity
import com.capstone.hewankita.ui.login.LoginActivity

class ProfileFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var pref: UserSession

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        activity?.setTitle(R.string.title_profile)

        pref = UserSession(requireContext())

        binding.column1.setOnClickListener(this)
        binding.column2.setOnClickListener(this)
        binding.column3.setOnClickListener(this)
        binding.column4.setOnClickListener(this)
        binding.column5.setOnClickListener(this)
        return root
    }

    override fun onClick(v: View) {
        if(v == binding.column1) {
            val intent = Intent(requireActivity(), MyPetActivity::class.java)
            startActivity(intent)
        }
        if(v == binding.column2) {
            val intent = Intent(requireActivity(), CekJadwalActivity::class.java)
            startActivity(intent)
        }
        if(v == binding.column3) {
            val intent = Intent(requireActivity(), EditProfileActivity::class.java)
            startActivity(intent)
        }
        if(v == binding.column4) {
            val intent = Intent(requireActivity(), InformationActivity::class.java)
            startActivity(intent)
        }
        if(v == binding.column5) {
            pref.getUser(
                LoginResult(
                    name = null,
                    token = null,
                    isLogin = false
                )
            )
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(requireActivity(), resources.getString(R.string.logout_success), Toast.LENGTH_SHORT).show()
            activity?.finish()
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}