package com.capstone.hewankita.ui.check_schedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.hewankita.R
import com.capstone.hewankita.adapter.ScheduleAllAdapter
import com.capstone.hewankita.data.local.database.ScheduleHelper
import com.capstone.hewankita.data.local.entity.ScheduleAll
import com.capstone.hewankita.databinding.ActivityAllScheduleBinding
import com.capstone.hewankita.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DoctorScheduleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllScheduleBinding
    private var adapter = ScheduleAllAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.doctor_schedule)

        binding.rvSchedule.layoutManager = LinearLayoutManager(this)
        binding.rvSchedule.setHasFixedSize(true)

        binding.rvSchedule.adapter = adapter

        loadAsync()

        if (savedInstanceState == null) {
            loadAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<ScheduleAll>(EXTRA_STATE)
            if (list != null) {
                adapter.listScheduleAll = list
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listScheduleAll)
    }

    private fun loadAsync() {
        lifecycleScope.launch {
            binding.progressbar.visibility = View.VISIBLE
            val scheduleHelper = ScheduleHelper.getInstance(applicationContext)
            scheduleHelper.open()
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = scheduleHelper.queryDoctorAll()
                MappingHelper.mapCursorDoctorToArrayList(cursor)
            }
            binding.progressbar.visibility = View.INVISIBLE
            val schedule = deferredNotes.await()
            if (schedule.size > 0) {
                adapter.listScheduleAll = schedule
            } else {
                adapter.listScheduleAll = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
            scheduleHelper.close()
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvSchedule, message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }
}