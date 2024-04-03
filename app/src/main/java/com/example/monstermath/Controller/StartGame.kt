package com.example.monstermath.Controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.R

class StartGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start)

        val startButton: Button = findViewById(R.id.start)
        startButton.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }

        val profileButton: Button = findViewById(R.id.playerprofile)
        profileButton.setOnClickListener {
            // Retrieve the username from SharedPreferences
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("USERNAME", "")

            // Pass the username to the PlayerProfile activity
            val intent = Intent(this, PlayerProfile::class.java)
            intent.putExtra("USERNAME", username)
            startActivity(intent)
        }
    }
}
