package com.example.monstermath.Controller

// Import necessary Android packages and classes
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.R

// Define the AdminLogIn activity
class AdminLogIn : AppCompatActivity() {
    // Override onCreate method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_login)
        // Find UI elements by their IDs
        val usernameEditText = findViewById<EditText>(R.id.usernameLogIn)
        val passwordEditText = findViewById<EditText>(R.id.passwordLogIn)
        val loginButton = findViewById<Button>(R.id.go)

        // Set up click listener for the login button
        loginButton.setOnClickListener {
            // Get username and password from EditText fields
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Check if username and password are correct (in this case, hardcoded as "admin")
            if (username == "admin" && password == "admin") {
                // If correct, start the Admin activity and show a toast message indicating successful login
                val intent = Intent(this, Admin::class.java)
                startActivity(intent)
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            } else {
                // If incorrect, show a toast message indicating invalid username or password
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

