package com.capstone.hewankita.ui.myPet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.hewankita.R
import com.capstone.hewankita.databinding.ActivityMyPetBinding
import com.capstone.hewankita.ui.bottom.ui.profile.ProfileFragment
import com.capstone.hewankita.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MyPetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPetBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.apply {
            fabSave.setOnClickListener{

                val etSpecies = etSpesies.text.toString().trim()
                val etGender = etJenisKelamin.text.toString().trim()
                val etDatOB = etTgl.text.toString().trim()
                val etWeight = etBerat.text.toString().trim()
                val etFeatherColour = etWarna.text.toString().trim()
                val etOther = etCiri.text.toString().trim()
                addPet(etSpecies, etGender, etDatOB, etWeight, etFeatherColour, etOther)
            }
        }
    }

    private fun addPet(species: String, gender: String, dateOB: String, weight: String, featherColour: String, other: String){

        val user: FirebaseUser? = auth.currentUser
        val userEmail: String = user!!.email.toString()

        val data = Firebase.database
        val databaseReference = data.getReference(Constants.TABLE_DATA_PET)

        val hashMap = mapOf<String, Any>(
            Constants.CONST_PET_SPECIES to species,
            Constants.CONST_PET_GENDER to gender,
            Constants.CONST_PET_DATE_OF_BIRTH to dateOB,
            Constants.CONST_PET_WEIGHT to weight,
            Constants.CONST_PET_FEATHER_COLOUR to featherColour,
            Constants.CONST_PET_OTHER to other,
            Constants.CONST_USER_EMAIL to userEmail
        )

        databaseReference.push().setValue(hashMap).addOnCompleteListener{
            val intent = Intent(this@MyPetActivity, ProfileFragment::class.java)
            startActivity(intent)
            finish()
        }
    }
}