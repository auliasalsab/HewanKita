package com.capstone.hewankita.ui.grooming

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.capstone.hewankita.R
import com.capstone.hewankita.databinding.ActivityMenuBinding

class GroomingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mFragmentManager = supportFragmentManager
        val mHomeFragment = GroomingFragment()
        val fragment = mFragmentManager.findFragmentByTag(GroomingFragment::class.java.simpleName)

        if (fragment !is GroomingFragment) {
            mFragmentManager.commit {
                add(R.id.frame_container, mHomeFragment, GroomingFragment::class.java.simpleName)
            }
        }
    }
}