package com.capstone.hewankita.ui.bottom.ui.profile

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.capstone.hewankita.R
import com.capstone.hewankita.databinding.FragmentProfileBinding
import com.capstone.hewankita.ui.check_schedule.CheckSchedule
import com.capstone.hewankita.ui.editProfile.EditProfileActivity
import com.capstone.hewankita.ui.information.InformationActivity
import com.capstone.hewankita.ui.myPet.MyPetActivity
import com.capstone.hewankita.ui.login.LoginActivity
import com.capstone.hewankita.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        activity?.setTitle(R.string.title_profile)

        auth = FirebaseAuth.getInstance()

        binding.column1.setOnClickListener(this)
        binding.column2.setOnClickListener(this)
        binding.column3.setOnClickListener(this)
        binding.column4.setOnClickListener(this)
        binding.column5.setOnClickListener(this)

        getUserData()

        return root
    }

    override fun onClick(v: View) {
        if(v == binding.column1) {
            val intent = Intent(requireActivity(), MyPetActivity::class.java)
            startActivity(intent)
        }
        if(v == binding.column2) {
            val intent = Intent(requireActivity(), CheckSchedule::class.java)
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
            logOut()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(requireActivity(), resources.getString(R.string.logout_success), Toast.LENGTH_SHORT).show()
            activity?.finish()
        }
    }
    private fun logOut(){
        Firebase.auth.signOut()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    private fun getUserData(){
        val user: FirebaseUser? = auth.currentUser
        val userID: String = user!!.uid
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATA_USER)

        databaseReference.child(userID).get().addOnSuccessListener{
            if (it.exists()){
                val usernameProfile = it.child(Constants.CONST_USER_USERNAME).value
                val emailProfile = it.child(Constants.CONST_USER_EMAIL).value
                val photoProfile = it.child(Constants.CONST_USER_IMG).value

                binding.tvName.text = usernameProfile.toString()
                binding.tvEmail.text = emailProfile.toString()
                Glide.with(this)
                    .load(photoProfile)
                    .into(binding.imageViewProfile)
            }
            else{
                Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}