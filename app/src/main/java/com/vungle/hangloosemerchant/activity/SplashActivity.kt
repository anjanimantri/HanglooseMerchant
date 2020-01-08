package com.vungle.hangloosemerchant.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.vungle.hangloosemerchant.R
import com.vungle.hangloosemerchant.utils.PreferenceHelper
import com.vungle.hangloosemerchant.utils.X_AUTH_TOKEN

import com.vungle.hangloosemerchant.utils.PreferenceHelper.get

class SplashActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000 //3 seconds

    var mHeader: String? = null
    private var mPreference: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //Initialize the Handler
        mDelayHandler = Handler()
        mPreference = PreferenceHelper.defaultPrefs(this)
        mHeader = mPreference!![X_AUTH_TOKEN]
        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            if (mHeader == null) {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(applicationContext, DrawerActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
    }
}