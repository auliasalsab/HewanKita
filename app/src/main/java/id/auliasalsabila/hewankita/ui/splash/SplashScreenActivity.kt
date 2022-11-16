package id.auliasalsabila.hewankita.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import id.auliasalsabila.hewankita.R
import id.auliasalsabila.hewankita.ui.bottom.BottomActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreenActivity, BottomActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)

        /*val login = UserSession(this).isLogin()
        Handler(Looper.getMainLooper()).postDelayed({
            if (login) {
                val intent = Intent(this@SplashScreenActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000) */
    }
}