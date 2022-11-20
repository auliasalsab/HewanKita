package com.capstone.hewankita.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.capstone.hewankita.R
import com.capstone.hewankita.data.session.UserSession
import com.capstone.hewankita.ui.bottom.BottomActivity
import com.capstone.hewankita.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        val login = UserSession(this).isLogin()
        Handler(Looper.getMainLooper()).postDelayed({
            if (login) {
                val intent = Intent(this@SplashScreenActivity, BottomActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)
    }
}