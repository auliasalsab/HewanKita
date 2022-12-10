package com.capstone.hewankita.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.capstone.hewankita.R
import com.capstone.hewankita.databinding.ActivityRegisterBinding
import com.capstone.hewankita.ui.bottom.BottomActivity
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

                registerUserPetShop(username, email, password)
            }

            etEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    setButtonEnable()
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })
            etPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    setButtonEnable()
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })
            etName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    setButtonEnable()
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })
        }
    }
    private fun registerUserPetShop(username: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { it ->
                if (it.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    val userId: String = user!!.uid


                    databaseReference = FirebaseDatabase.getInstance().getReference(Constants.TABLE_DATA_USER).child(userId)

                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap[Constants.CONST_USER_USERNAME] = username
                    hashMap[Constants.CONST_USER_EMAIL] = email
                    hashMap[Constants.CONST_KEY] = userId

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

    private fun setButtonEnable() {
        binding.apply {
            val email = etEmail.text
            val password = etPassword.text
            val username = etName.text
            btnRegister.isEnabled = email.toString().isNotEmpty() && password.toString().isNotEmpty() && password.toString().length >= 6 && username.toString().isNotEmpty()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
