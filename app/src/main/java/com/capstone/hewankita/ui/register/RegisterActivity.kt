package com.capstone.hewankita.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.capstone.hewankita.R
import com.capstone.hewankita.databinding.ActivityRegisterBinding
import com.capstone.hewankita.ui.bottom.BottomActivity
import com.capstone.hewankita.utils.ViewModelFactory
import com.capstone.hewankita.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private var factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val viewModel: RegisterViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        binding.progressBar.visibility = View.GONE

        binding.apply {
            tvLocalization.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            btnRegister.setOnClickListener {
                val username = binding.etName.text.toString()
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val type = binding.tvTypeRegister.text.toString()

                registerUserPetShop(username, email, password, type)
            }
            swType.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked){
                    tvTypeRegister.text = Constants.CHILD_USER_PET_SHOP
                }
                else{
                    tvTypeRegister.text = Constants.CHILD_USER_CUSTOMER
                }
            }
        }
    }
    private fun registerUserPetShop(username: String, email: String, password: String, type: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { it ->
                if (it.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    val userId: String = user!!.uid


                    databaseReference = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATA_USER).child(userId)

                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap[Constants.CONST_USER_USERNAME] = username
                    hashMap[Constants.CONST_USER_EMAIL] = email
                    hashMap[Constants.CONST_USER_TYPE] = type

                    databaseReference.setValue(hashMap).addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            val intent = Intent(this@RegisterActivity, BottomActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        } else {
                            val message = it.exception.toString()
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this@RegisterActivity, getString(R.string.register_error), Toast.LENGTH_SHORT).show()
                }
            }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
