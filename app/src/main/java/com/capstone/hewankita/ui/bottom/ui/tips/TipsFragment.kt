package com.capstone.hewankita.ui.bottom.ui.tips

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.hewankita.R
import com.capstone.hewankita.adapter.ListTipsAdapter
import com.capstone.hewankita.adapter.ScheduleAllAdapter
import com.capstone.hewankita.data.AllData
import com.capstone.hewankita.databinding.FragmentTipsBinding
import com.capstone.hewankita.ui.login.LoginActivity
import com.capstone.hewankita.utils.Constants
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class TipsFragment : Fragment() {
    private var _binding: FragmentTipsBinding? = null
    private val binding get() = _binding!!
    private val tipsList = ArrayList<AllData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        ViewModelProvider(this)[TipsViewModel::class.java]
        activity?.setTitle(R.string.title_tips)
        _binding = FragmentTipsBinding.inflate(inflater, container, false)

        binding.apply {
            rvTips.layoutManager = LinearLayoutManager(requireActivity())
            rvTips.setHasFixedSize(true)
            getDoctorService()
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

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

    private fun logOut(){
        Firebase.auth.signOut()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getDoctorService(){

        val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child(Constants.TABLE_DATA_TIPS).addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                showSnackbarMessage(getString(R.string.no_data))
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (doctorServiceSnapshot in snapshot.children){
                        val tips = doctorServiceSnapshot.getValue(AllData::class.java)
                        tipsList.add(tips!!)
                    }
                    binding.rvTips.adapter = ListTipsAdapter(tipsList)
                }
                else{
                    showSnackbarMessage(getString(R.string.no_data))
                }
            }
        })
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvTips, message, Snackbar.LENGTH_SHORT).show()
    }
}