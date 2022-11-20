package com.capstone.hewankita.ui.vaccination

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.capstone.hewankita.R
import com.capstone.hewankita.databinding.ActivityMenuBinding

class VaccinationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mFragmentManager = supportFragmentManager
        val mHomeFragment = VaccinationFragment()
        val fragment = mFragmentManager.findFragmentByTag(VaccinationFragment::class.java.simpleName)

        if (fragment !is VaccinationFragment) {
            mFragmentManager.commit {
                add(R.id.frame_container, mHomeFragment, VaccinationFragment::class.java.simpleName)
            }
        }
    }
}