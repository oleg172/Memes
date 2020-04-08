package com.olmi.android.memes.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.olmi.android.memes.MainActivity
import com.olmi.android.memes.R
import java.util.Timer
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {

    var splashDelay: Long = 300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        openLoginScreen()
    }

    private fun openLoginScreen() {
        val intent = Intent(this, MainActivity::class.java)
        Timer().schedule(splashDelay) {
            startActivity(intent)
        }
    }
}
