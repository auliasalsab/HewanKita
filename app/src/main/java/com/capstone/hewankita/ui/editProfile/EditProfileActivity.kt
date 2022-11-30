package com.capstone.hewankita.ui.editProfile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.capstone.hewankita.R
import com.capstone.hewankita.databinding.ActivityEditProfileBinding
import com.capstone.hewankita.utils.Constants
import com.capstone.hewankita.utils.uriToFile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.File
import java.security.Key
import java.util.Base64

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private var getFile: File? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.apply {
            tvAddProfile.setOnClickListener{
                startGallery()
            }

            binding.btnSaveProfile.setOnClickListener{
                val username = etEmail.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val phone = etNoHp.text.toString().trim()
                val address = etAddress.text.toString().trim()
                val img = imageViewProfile.toString().trim()

                saveProfile(username, email, phone, address, img)
            }
        }
    }

    private fun startGallery(){
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.picture))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == RESULT_OK){
            val selectedImage: Uri = it.data?.data as Uri
            val myFile = uriToFile(selectedImage, this)
            getFile = myFile
            binding.imageViewProfile.setImageURI(selectedImage)
        }
    }

    private fun saveProfile(username: String, email: String, phone: String, address: String, profImg: String){
        val user: FirebaseUser? = auth.currentUser
        val userEmail: String? = user!!.email

        val database = Firebase.database
        val databaseReference = database.getReference(Constants.TABLE_DATA_USER)

        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[Constants.CONST_USER_USERNAME] = username
        hashMap[Constants.CONST_USER_EMAIL] = email
        hashMap[Constants.CONST_USER_PHONE] = phone
        hashMap[Constants.CONST_USER_ADDRESS] = address
        hashMap[Constants.CONST_USER_IMG] = profImg

        databaseReference.push().setValue(hashMap)
    }
}