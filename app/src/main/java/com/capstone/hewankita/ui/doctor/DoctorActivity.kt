package com.capstone.hewankita.ui.doctor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.capstone.hewankita.R
import com.capstone.hewankita.databinding.ActivityMenuBinding

class DoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mFragmentManager = supportFragmentManager
        val mHomeFragment = DoctorFragment()
        val fragment = mFragmentManager.findFragmentByTag(DoctorFragment::class.java.simpleName)

        if (fragment !is DoctorFragment) {
            mFragmentManager.commit {
                add(R.id.frame_container, mHomeFragment, DoctorFragment::class.java.simpleName)
            }
        }
    }
}