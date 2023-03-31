package com.samith.chatgptplus.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.samith.chatgptplus.databinding.ActivityMainBinding
/**
 * MainActivity is the entry point of the app that displays the welcome screen
 * and allows users to continue to the chat screen.
 *
 * Created by samithchow on 3/25/2023.
 * Website: samith.dev
 */
class MainActivity : AppCompatActivity() {

    // View binding for the main activity
    private var mainActivityBinding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up view binding
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding!!.root)


        // Continue button listener to start the chat activity
        mainActivityBinding!!.btnContinue.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java))
        }

    }

    override fun onDestroy() {
        // Clear view binding on destroy to avoid memory leaks
        mainActivityBinding = null
        super.onDestroy()
    }
}