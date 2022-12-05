package com.capstone.hewankita.ui.check_schedule.vaccination

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.hewankita.R
import com.capstone.hewankita.adapter.ScheduleAllAdapter
import com.capstone.hewankita.data.AllData
import com.capstone.hewankita.databinding.ActivityAllScheduleBinding
import com.capstone.hewankita.utils.Constants
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class VaccinationScheduleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllScheduleBinding
    private val doctorList = ArrayList<AllData>()
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        title = getString(R.string.vaccination_schedule)

        binding.apply {
            rvSchedule.layoutManager = LinearLayoutManager(this@VaccinationScheduleActivity)
            rvSchedule.setHasFixedSize(true)
        }
        getDoctorService()
    }

    private fun getDoctorService(){

        val user: FirebaseUser = auth.currentUser!!
        val userEmail: String = user.email.toString()

        val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child(Constants.TABLE_DATA_SERVICE).child(Constants.CHILD_SERVICE_VACCINATION_SERVICE).orderByChild(
            Constants.CONST_USER_EMAIL).equalTo(userEmail).addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                showSnackbarMessage(getString(R.string.no_data))
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (doctorServiceSnapshot in snapshot.children){
                        val doctor = doctorServiceSnapshot.getValue(AllData::class.java)
                        doctorList.add(doctor!!)
                    }
                    binding.rvSchedule.adapter = ScheduleAllAdapter(doctorList)
                }
                else{
                    showSnackbarMessage(getString(R.string.no_data))
                }
            }
        })
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvSchedule, message, Snackbar.LENGTH_SHORT).show()
    }
}