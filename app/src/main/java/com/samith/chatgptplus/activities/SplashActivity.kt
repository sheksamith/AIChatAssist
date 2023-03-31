package com.samith.chatgptplus.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.samith.chatgptplus.databinding.ActivitySplashBinding
import com.samith.chatgptplus.utils.Constants

/**
 * This activity displays a splash screen while the app initializes.
 *
 * It first checks the user's preference for dark mode using SharedPreferences and sets the appropriate AppCompatDelegate mode.
 *
 * It then inflates the layout using View Binding and sets it as the content view for the activity.
 *
 * Finally, after a 5-second delay, it starts the main activity and finishes this activity.
 *
 * Created by samithchow on 3/25/2023.
 * Website: samith.dev
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    //Variables
    private var splashBinding : ActivitySplashBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve user preference for dark mode using SharedPreferences
        val spf  = getSharedPreferences(Constants.SPF_NAME, MODE_PRIVATE)
        val darkMode : Boolean = spf.getBoolean(Constants.SPF_DATA, false)

        // Set the appropriate AppCompatDelegate mode based on user preference
        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        // Inflate the layout using View Binding and set it as the content view
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding!!.root)

        // Start the main activity after a 5-second delay
        Handler(Looper.getMainLooper()).postDelayed({
           startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 5000)
    }
}