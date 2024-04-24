package com.example.monstermath.Controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.R

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start)

        val startButton: Button = findViewById(R.id.start)
        startButton.setOnClickListener {
            val intent = Intent(this, ChooseDifficulty::class.java)
            startActivity(intent)
        }

        val profileButton: Button = findViewById(R.id.playerprofile)
        profileButton.setOnClickListener {
            val intent = Intent(this, PlayerProfile::class.java)
            startActivity(intent)
        }

        val leaderButton: Button = findViewById(R.id.leaderboard)
        leaderButton.setOnClickListener {
            val intent = Intent(this, Leaderboard::class.java)
            startActivity(intent)
        }

        val logoutButton: Button = findViewById(R.id.LogOut)
        logoutButton.setOnClickListener {
            val intent = Intent(this, Start::class.java)
            startActivity(intent)
        }
    }
}
