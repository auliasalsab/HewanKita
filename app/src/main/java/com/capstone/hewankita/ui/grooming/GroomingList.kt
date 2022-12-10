package com.capstone.hewankita.ui.grooming

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.hewankita.R
import com.capstone.hewankita.adapter.GroomingAdapter
import com.capstone.hewankita.data.AllData
import com.capstone.hewankita.databinding.ActivityListOutletBinding
import com.capstone.hewankita.utils.Constants
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*

class GroomingList : AppCompatActivity() {

    private lateinit var binding: ActivityListOutletBinding
    private var listPetShop = ArrayList<AllData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListOutletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            rvConsult.layoutManager = LinearLayoutManager(this@GroomingList)
            rvConsult.setHasFixedSize(true)
        }
        getOutletData()
    }

    private fun getOutletData(){
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child(Constants.TABLE_DATA_USER).orderByChild(Constants.CONST_USER_TYPE).equalTo(
            Constants.CHILD_USER_PET_SHOP).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (outletSnapshot in snapshot.children){
                        val outlet = outletSnapshot.getValue(AllData::class.java)
                        listPetShop.add(outlet!!)
                    }
                    binding.rvConsult.adapter = GroomingAdapter(listPetShop)
                }
                else{
                    showSnackbarMessage(getString(R.string.no_data))
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvConsult, message, Snackbar.LENGTH_SHORT).show()
    }
}