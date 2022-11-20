package com.capstone.hewankita.ui.care

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.capstone.hewankita.R
import com.capstone.hewankita.databinding.ActivityMenuBinding

class CareActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mFragmentManager = supportFragmentManager
        val mHomeFragment = CareFragment()
        val fragment = mFragmentManager.findFragmentByTag(CareFragment::class.java.simpleName)

        if (fragment !is CareFragment) {
            mFragmentManager.commit {
                add(R.id.frame_container, mHomeFragment,CareFragment::class.java.simpleName)
            }
        }
    }
}