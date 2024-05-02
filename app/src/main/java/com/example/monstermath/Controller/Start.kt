package com.example.monstermath.Controller

// Import necessary Android packages and classes
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.monstermath.R

// Define the Start activity
class Start : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Set the layout for the activity

        // Find the register button by its ID
        val registerButton: Button = findViewById(R.id.register)

        // Set up click listener for the register button
        registerButton.setOnClickListener {
            // Create an intent to navigate to the Register activity
            val intent = Intent(this, Register::class.java)
            startActivity(intent) // Start the Register activity
        }

        // Find the login button by its ID
        val loginButton: Button = findViewById(R.id.login)

        // Set up click listener for the login button
        loginButton.setOnClickListener {
            // Create an intent to navigate to the LogIn activity
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent) // Start the LogIn activity
        }

        // Find the admin button by its ID
        val adminButton: Button = findViewById(R.id.teachers)

        // Set up click listener for the admin button
        adminButton.setOnClickListener {
            // Create an intent to navigate to the AdminLogIn activity
            val intent = Intent(this, AdminLogIn::class.java)
            startActivity(intent) // Start the AdminLogIn activity
        }
    }
}
