package id.auliasalsabila.hewankita.ui.outlet

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.auliasalsabila.hewankita.data.Outlet
import id.auliasalsabila.hewankita.adapter.OutletAdapter
import id.auliasalsabila.hewankita.R
import id.auliasalsabila.hewankita.databinding.ActivityOutletBinding
import id.auliasalsabila.hewankita.ui.doctor.DoctorActivity

class OutletActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOutletBinding
    private val list = ArrayList<Outlet>()
    private lateinit var tvSearch: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.outlet)

        tvSearch = findViewById(R.id.tvOutlet)

        list.addAll(listOutlet)
        showRecyclerList()
    }

    private val listOutlet: ArrayList<Outlet>
        get(){
            val dataOutlet = resources.getStringArray(R.array.OutletName)
            val dataAddress = resources.getStringArray(R.array.OutletAddress)
            val outletList = ArrayList<Outlet>()
            for (i in dataOutlet.indices) {
                val outlet = Outlet(dataOutlet[i], dataAddress[i])
                outletList.add(outlet)
            }
            return outletList
        }

    private fun showRecyclerList() {
        binding.rvOutlet.layoutManager = LinearLayoutManager(this)
        val outletAdapter = OutletAdapter(list)
        outletAdapter.setOnItemClickCallback(object : OutletAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Outlet) {
                val intent = Intent(this@OutletActivity, DoctorActivity::class.java)
                intent.putExtra(DoctorActivity.EXTRA_DETAIL, tvSearch.text.toString())
                startActivity(intent)
            }
        })

        binding.rvOutlet.adapter = outletAdapter
        binding.rvOutlet.setHasFixedSize(true)
    }
}