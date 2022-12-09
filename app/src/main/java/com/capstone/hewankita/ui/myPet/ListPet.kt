package com.capstone.hewankita.ui.myPet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.hewankita.R
import com.capstone.hewankita.adapter.PetAdapter
import com.capstone.hewankita.data.AllData
import com.capstone.hewankita.databinding.ActivityListPetBinding
import com.capstone.hewankita.utils.Constants
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ListPet : AppCompatActivity() {

    private lateinit var binding: ActivityListPetBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var adapter: PetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding = ActivityListPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            fabAddPet.setOnClickListener{
                val pet = tvPet.text.toString().trim()
                addNewPet(pet)
                Toast.makeText(this@ListPet, getString(R.string.add_pet_successful), Toast.LENGTH_SHORT).show()
            }
            rvPet.layoutManager = LinearLayoutManager(this@ListPet)
        }
            db = Firebase.database
            val user: FirebaseUser = auth.currentUser!!
            val userEmail : String = user.email!!
            val messagesRef = db.reference.child(Constants.TABLE_DATA_PET).orderByChild(Constants.CONST_USER_EMAIL).equalTo(userEmail)
            val manager = LinearLayoutManager(this)
            binding.rvPet.layoutManager = manager
            val options = FirebaseRecyclerOptions.Builder<AllData>()
                .setQuery(messagesRef, AllData::class.java)
                .build()
            adapter = PetAdapter(options)
            binding.rvPet.adapter = adapter
    }

    public override fun onResume() {
        super.onResume()
        adapter.startListening()
    }

    public override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }

    private fun addNewPet(pet: String){
        val user: FirebaseUser? = auth.currentUser
        val userEmail: String = user?.email.toString()

        val database = Firebase.database
        val databaseReference = database.getReference(Constants.TABLE_DATA_PET)
        val key: String = databaseReference.push().key.toString()
        val hashMap = mapOf<String, Any>(
            Constants.CONST_USER_EMAIL to userEmail,
            Constants.CONST_PET_NAME to pet,
            Constants.CONST_PET_ID to key
        )

        databaseReference.child(key).setValue(hashMap)
    }
}