package id.auliasalsabila.hewankita.ui.doctor

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import id.auliasalsabila.hewankita.R
import id.auliasalsabila.hewankita.databinding.ActivityDokterHewanBinding
import id.auliasalsabila.hewankita.ui.outlet.OutletActivity
import java.lang.reflect.Array.getInt

class DoctorActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDokterHewanBinding
    private var mYear = 0
    private var mMonth = 0
    private var mDay = 0
    private var mHour = 0
    private var mMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDokterHewanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.animalDoctor)

        binding.tvOutlet.setOnClickListener(this)
        binding.tvBookingDate.setOnClickListener(this)
        binding.tvBookingTime.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v == binding.tvOutlet) {
            val intent = Intent(this@DoctorActivity, OutletActivity::class.java)
            startActivity(intent)
        }
        if (v === binding.tvBookingDate) {
            val c = Calendar.getInstance()
            mYear = c[Calendar.YEAR]
            mMonth = c[Calendar.MONTH]
            mDay = c[Calendar.DAY_OF_MONTH]

            val datePickerDialog = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                binding.tvBookingDate.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
            }, mYear, mMonth, mDay)

            datePickerDialog.show()
        }
        if (v === binding.tvBookingTime) {
            val c = Calendar.getInstance()
            mHour = c[Calendar.HOUR_OF_DAY]
            mMinute = c[Calendar.MINUTE]

            val timePickerDialog = TimePickerDialog(this, { _, hourOfDay, minute ->
                binding.tvBookingTime.setText("$hourOfDay:$minute")
            }, mHour, mMinute, true)

            timePickerDialog.show()
        }
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }
}