package com.capstone.hewankita.ui.consultation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.hewankita.R

class ConsultationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultation)

        title = getString(R.string.consultation)
    }
}