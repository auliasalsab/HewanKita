package com.capstone.hewankita.ui.editProfile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Contacts.Data
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.capstone.hewankita.R
import com.capstone.hewankita.databinding.ActivityEditProfileBinding
import com.capstone.hewankita.ui.bottom.BottomActivity
import com.capstone.hewankita.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.Base64

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.apply {
            tvAddProfile.setOnClickListener {
                startGallery()
            }

            binding.btnSaveProfile.setOnClickListener {

                val username = etName.text.toString().trim()
                val phone = etNoHp.text.toString().trim()
                val address = etAddress.text.toString().trim()

                if (username.isNotEmpty() && phone.isNotEmpty() && address.isNotEmpty()) {
                    saveProfile(username, phone, address)
                    val intent = Intent(this@EditProfileActivity, BottomActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this@EditProfileActivity, getString(R.string.error_field), Toast.LENGTH_SHORT).show()
                }
            }
        }
        getData()
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.picture))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {

            val user = auth.currentUser
            val userID = user!!.uid
            val selectedImage: Uri = it.data?.data as Uri

            val storageRef = FirebaseStorage.getInstance().getReference("image/user/${selectedImage.lastPathSegment}")
            storageRef.putFile(selectedImage).addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {

                    val database = Firebase.database
                    val databaseReference = database.getReference(Constants.TABLE_DATA_USER)
                    val hashMap = mapOf(
                        Constants.CONST_USER_IMG to it.toString()
                    )
                    databaseReference.child(userID).updateChildren(hashMap)
                }
            }.addOnFailureListener{
                Toast.makeText(this@EditProfileActivity, "Request Time Out", Toast.LENGTH_SHORT).show()
            }
            binding.imageViewProfile.setImageURI(selectedImage)
        }
    }

    private fun saveProfile(username: String, phone: String, address: String) {

        val user: FirebaseUser? = auth.currentUser

        val userID: String = user!!.uid
        val userEmail: String? = user.email

        val database = Firebase.database
        val databaseReference = database.getReference(Constants.TABLE_DATA_USER)

        val hashMap = mapOf(

            Constants.CONST_USER_USERNAME to username,
            Constants.CONST_USER_PHONE to phone,
            Constants.CONST_USER_ADDRESS to address,
            Constants.CONST_USER_EMAIL to userEmail.toString()
        )
        databaseReference.child(userID).updateChildren(hashMap)
        Toast.makeText(this, getString(R.string.profile_has_been_updated), Toast.LENGTH_SHORT).show()
    }

    private fun getData(){
        val user: FirebaseUser? = auth.currentUser
        val userID: String = user!!.uid

        val databaseReference:DatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATA_USER)
        databaseReference.child(userID).get().addOnSuccessListener {
            if (it.exists()){
                val imageProfile = it.child(Constants.CONST_USER_IMG).value
                Glide.with(this)
                    .load(imageProfile)
                    .into(binding.imageViewProfile)
            }
        }
    }
}