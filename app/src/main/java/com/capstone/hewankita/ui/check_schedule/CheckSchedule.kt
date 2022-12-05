package com.capstone.hewankita.ui.check_schedule

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.capstone.hewankita.R
import com.capstone.hewankita.databinding.ActivityCheckScheduleBinding
import com.capstone.hewankita.ui.check_schedule.care.CareScheduleActivity
import com.capstone.hewankita.ui.check_schedule.doctor.DoctorScheduleActivity
import com.capstone.hewankita.ui.check_schedule.grooming.GroomingScheduleActivity
import com.capstone.hewankita.ui.check_schedule.vaccination.VaccinationScheduleActivity

class CheckSchedule : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCheckScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.check_schedule)

        binding.ivDoctor.setOnClickListener(this)
        binding.ivVaccination.setOnClickListener(this)
        binding.ivGrooming.setOnClickListener(this)
        binding.ivCare.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v == binding.ivDoctor) {
            val intent = Intent(this@CheckSchedule, DoctorScheduleActivity::class.java)
            startActivity(intent)
        }
        if(v == binding.ivVaccination) {
            val intent = Intent(this@CheckSchedule, VaccinationScheduleActivity::class.java)
            startActivity(intent)
        }
        if(v == binding.ivGrooming) {
            val intent = Intent(this@CheckSchedule, GroomingScheduleActivity::class.java)
            startActivity(intent)
        }
        if(v == binding.ivCare) {
            val intent = Intent(this@CheckSchedule, CareScheduleActivity::class.java)
            startActivity(intent)
        }
    }
}