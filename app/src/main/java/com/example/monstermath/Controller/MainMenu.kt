package com.example.monstermath.Controller

// Import necessary Android packages and classes
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.R

// Define the MainMenu activity
class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start)

        // Find the start button by its ID
        val startButton: Button = findViewById(R.id.start)
        // Set up click listener for the start button
        startButton.setOnClickListener {
            // Create an intent to navigate to the ChooseDifficulty activity
            val intent = Intent(this, ChooseDifficulty::class.java)
            startActivity(intent)
        }

        // Find the profile button by its ID
        val profileButton: Button = findViewById(R.id.playerprofile)
        // Set up click listener for the profile button
        profileButton.setOnClickListener {
            // Create an intent to navigate to the PlayerProfile activity
            val intent = Intent(this, PlayerProfile::class.java)
            startActivity(intent)
        }

        // Find the leaderboard button by its ID
        val leaderButton: Button = findViewById(R.id.leaderboard)
        // Set up click listener for the leaderboard button
        leaderButton.setOnClickListener {
            // Create an intent to navigate to the Leaderboard activity
            val intent = Intent(this, Leaderboard::class.java)
            startActivity(intent)
        }

        // Find the logout button by its ID
        val logoutButton: Button = findViewById(R.id.LogOut)
        // Set up click listener for the logout button
        logoutButton.setOnClickListener {
            // Create an intent to navigate back to the Start activity
            val intent = Intent(this, Start::class.java)
            startActivity(intent)
        }
    }
}
