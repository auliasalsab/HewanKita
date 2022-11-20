package com.capstone.hewankita.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.capstone.hewankita.R
import com.capstone.hewankita.data.Result
import com.capstone.hewankita.databinding.ActivityRegisterBinding
import com.capstone.hewankita.utils.ViewModelFactory
import com.capstone.hewankita.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: RegisterViewModel by viewModels {
            factory
        }
        binding.progressBar.visibility = View.GONE

        binding.apply {
            btnRegister.setOnClickListener {
                viewModel.register(
                    etName.text.toString(),
                    etEmail.text.toString(),
                    etPassword.text.toString()
                ).observe(this@RegisterActivity) {
                    if (it != null) {
                        when (it) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(this@RegisterActivity,
                                    resources.getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                            }
                            is Result.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(this@RegisterActivity,
                                    resources.getString(R.string.register_error), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }

            tvLocalization.setOnClickListener{
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
       }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
