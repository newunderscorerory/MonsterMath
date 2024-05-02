package com.example.monstermath.Controller

// Import necessary Android packages and classes
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.R

// Define the Win activity
class Win : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.win) // Set the layout for the activity

        // Find the return button by its ID
        val returnButton: Button = findViewById(R.id.winReturn)

        // Set up click listener for the return button
        returnButton.setOnClickListener {
            // Create an intent to navigate back to the MainMenu activity
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent) // Start the MainMenu activity
        }
    }
}
