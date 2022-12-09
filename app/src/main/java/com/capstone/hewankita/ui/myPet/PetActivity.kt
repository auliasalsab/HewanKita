package com.capstone.hewankita.ui.myPet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.capstone.hewankita.R
import com.capstone.hewankita.data.AllData
import com.capstone.hewankita.databinding.ActivityPetBinding
import com.capstone.hewankita.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPetBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.apply {
            fabEdit.setOnClickListener{
                val intent = Intent(this@PetActivity, MyPetActivity::class.java)
                startActivity(intent)
            }
        }

        getDataPet()
    }

    private fun getDataPet(){

        val user: FirebaseUser = auth.currentUser!!
        val userId: String = user.uid

        val dbRef = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATA_PET).child(userId)
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val pet = snapshot.getValue(AllData::class.java)
                binding.tvNamePet.text = pet!!.PetName
                binding.tvGenderPet.text = pet.Gender
                Glide.with(this@PetActivity)
                    .load(pet.PetImg)
                    .into(binding.imgPet)
                binding.tvBeratPet.text = pet.Weight
                binding.tvBuluPet.text = pet.FeatherColour
                binding.tvSpeciesPet.text = pet.Species
                binding.tvTtlPet.text = pet.DateOfBirth
            }
        })

    }
}