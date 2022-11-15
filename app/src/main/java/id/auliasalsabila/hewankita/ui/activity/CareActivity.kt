package id.auliasalsabila.hewankita.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.auliasalsabila.hewankita.R
import id.auliasalsabila.hewankita.databinding.ActivityCareBinding
import id.auliasalsabila.hewankita.databinding.ActivityDokterHewanBinding

class CareActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCareBinding
    private var mYear = 0
    private var mMonth = 0
    private var mDay = 0
    private var mHour = 0
    private var mMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.custody)

        binding.tvOutlet.setOnClickListener(this)
        binding.tvCheckIn.setOnClickListener(this)
        binding.tvCheckOut.setOnClickListener(this)
        binding.tvTimeOfArrival.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v == binding.tvOutlet) {
            val intent = Intent(this@CareActivity, OutletActivity::class.java)
            startActivity(intent)
        }
        if (v === binding.tvCheckIn) {
            val c = Calendar.getInstance()
            mYear = c[Calendar.YEAR]
            mMonth = c[Calendar.MONTH]
            mDay = c[Calendar.DAY_OF_MONTH]

            val datePickerDialog = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                binding.tvCheckIn.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
            }, mYear, mMonth, mDay)

            datePickerDialog.show()
        }
        if (v === binding.tvCheckOut) {
            val c = Calendar.getInstance()
            mYear = c[Calendar.YEAR]
            mMonth = c[Calendar.MONTH]
            mDay = c[Calendar.DAY_OF_MONTH]

            val datePickerDialog = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                binding.tvCheckOut.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
            }, mYear, mMonth, mDay)

            datePickerDialog.show()
        }
        if (v === binding.tvTimeOfArrival) {
            val c = Calendar.getInstance()
            mHour = c[Calendar.HOUR_OF_DAY]
            mMinute = c[Calendar.MINUTE]

            val timePickerDialog = TimePickerDialog(this, { _, hourOfDay, minute ->
                binding.tvTimeOfArrival.setText("$hourOfDay:$minute")
            }, mHour, mMinute, true)

            timePickerDialog.show()
        }
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }
}