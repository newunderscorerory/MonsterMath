package com.example.monstermath.Controller

// Import necessary Android packages and classes
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.Model.MonsterMathDBHelper
import com.example.monstermath.Model.Reward
import com.example.monstermath.R

// Define the PlayerProfile activity
class PlayerProfile : AppCompatActivity() {

    // Declare properties
    private lateinit var dbHelper: MonsterMathDBHelper

    // Override onCreate method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.playerprofile) // Set the layout for the activity

        dbHelper = MonsterMathDBHelper(this) // Initialize the database helper

        val username = globalUser // Get the username from the global variable

        // Retrieve user data from the database based on the username
        val user = dbHelper.getCustomer(username)

        // If user data is successfully retrieved, populate the UI elements with user information
        if (user != null) {
            val usernameTextView = findViewById<TextView>(R.id.usernameTextView)
            val emailTextView = findViewById<TextView>(R.id.emailTextView)
            val fullNameTextView = findViewById<TextView>(R.id.fullnameTextView)
            val highScoreTextView = findViewById<TextView>(R.id.highScoreTextView)

            usernameTextView.text = user.username
            emailTextView.text = "Email: ${user.email}"
            fullNameTextView.text = "Full Name: ${user.fullname}"
            highScoreTextView.text = "High Score: ${user.highScore}"

            // Retrieve earned rewards for the user from the database
            val earnedRewards = dbHelper.getEarnedRewards(username)
            // Display earned rewards on the UI
            displayEarnedRewards(earnedRewards)
        } else {
            // Show a toast message if failed to retrieve user information
            Toast.makeText(this, "Failed to retrieve user information", Toast.LENGTH_SHORT).show()
        }

        // Find the return button by its ID
        val returnButton: Button = findViewById(R.id.back)

        // Set up click listener for the return button
        returnButton.setOnClickListener {
            // Create an intent to navigate back to the MainMenu activity
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent) // Start the MainMenu activity
        }
    }

    // Function to display earned rewards on the UI
    private fun displayEarnedRewards(earnedRewards: List<Reward>) {
        for (reward in earnedRewards) {
            // Determine the ImageView ID based on the reward ID
            val imageViewId = when (reward.id) {
                1 -> R.id.reward1ImageView
                2 -> R.id.reward2ImageView
                3 -> R.id.reward3ImageView
                else -> null
            }
            // If ImageView ID is not null, set the corresponding ImageView's image resource and make it visible
            imageViewId?.let {
                val imageView = findViewById<ImageView>(it)
                imageView.setImageResource(reward.imageResourseID)
                imageView.visibility = View.VISIBLE
            }
        }
    }
}