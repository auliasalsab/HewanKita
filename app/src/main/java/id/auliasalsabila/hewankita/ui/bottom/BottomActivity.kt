package id.auliasalsabila.hewankita.ui.bottom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.auliasalsabila.hewankita.R
import id.auliasalsabila.hewankita.databinding.ActivityBottomBinding

class BottomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_bottom)
        navView.setupWithNavController(navController)
    }
}