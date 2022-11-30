package com.capstone.hewankita.ui.check_schedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.hewankita.R
import com.capstone.hewankita.adapter.ScheduleCareAdapter
import com.capstone.hewankita.data.local.database.ScheduleHelper
import com.capstone.hewankita.data.local.entity.ScheduleCare
import com.capstone.hewankita.databinding.ActivityCareScheduleBinding
import com.capstone.hewankita.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CareScheduleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCareScheduleBinding
    private var adapter = ScheduleCareAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCareScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.care_schedule)

        binding.rvSchedule.layoutManager = LinearLayoutManager(this)
        binding.rvSchedule.setHasFixedSize(true)

        binding.rvSchedule.adapter = adapter

        loadAsync()

        if (savedInstanceState == null) {
            loadAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<ScheduleCare>(EXTRA_STATE)
            if (list != null) {
                adapter.listScheduleCare = list
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listScheduleCare)
    }

    private fun loadAsync() {
        lifecycleScope.launch {
            binding.progressbar.visibility = View.VISIBLE
            val scheduleHelper = ScheduleHelper.getInstance(applicationContext)
            scheduleHelper.open()
            val deferredSchedule = async(Dispatchers.IO) {
                val cursor = scheduleHelper.queryCareAll()
                MappingHelper.mapCursorCareToArrayList(cursor)
            }
            binding.progressbar.visibility = View.INVISIBLE
            val schedule = deferredSchedule.await()
            if (schedule.size > 0) {
                adapter.listScheduleCare = schedule
            } else {
                adapter.listScheduleCare = ArrayList()
                showSnackbarMessage(getString(R.string.no_data))
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