package com.example.monstermath.Controller

// Import necessary Android packages and classes
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.Model.MonsterMathDBHelper
import com.example.monstermath.R
import com.example.monstermath.Utils.PasswordHashing.hashPassword

// Define a global variable to store the logged-in user
var globalUser: String = ""

// Define the LogIn activity
class LogIn : AppCompatActivity() {

    // Declare properties
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var db: MonsterMathDBHelper

    // Override onCreate method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Initialize UI elements and database helper
        usernameEditText = findViewById(R.id.usernameLogIn)
        passwordEditText = findViewById(R.id.passwordLogIn)
        loginButton = findViewById(R.id.go)
        registerButton = findViewById(R.id.takeToRegister)
        db = MonsterMathDBHelper(this)

        // Ensure default rewards are inserted into the database if needed
        db.insertDefaultRewardsIfNeeded()

        // Set up click listener for the login button
        loginButton.setOnClickListener {
            // Get username and password from EditText fields
            val username = usernameEditText.text.toString().toLowerCase()
            val password = passwordEditText.text.toString()

            // Check if username or password is empty
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            } else {
                // Hash the password
                val hashedPassword = hashPassword(password)
                // Check if the username and password combination is authenticated
                val isAuthenticated = db.checkPass(username, hashedPassword)
                if (isAuthenticated) {
                    // Set the globalUser variable to the logged-in username
                    globalUser = username
                    // Show a toast message indicating successful login
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    // Start the MainMenu activity and pass the username as an extra
                    val intent = Intent(this, MainMenu::class.java)
                    intent.putExtra("username", username)
                    startActivity(intent)
                    // Finish the current activity
                    finish()
                } else {
                    // Show a toast message indicating invalid username or password
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Set up click listener for the register button
        registerButton.setOnClickListener {
            // Start the Register activity
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            // Finish the current activity
            finish()
        }
    }
}
