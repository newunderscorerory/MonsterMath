package com.example.monstermath.Controller

// Import necessary Android packages and classes
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import com.example.monstermath.Model.MonsterMathDBHelper
import com.example.monstermath.R
import com.example.monstermath.Utils.PasswordHashing.hashPassword

// Define the Register activity
class Register : AppCompatActivity() {

    // Declare properties
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var confirm: EditText
    private lateinit var email: EditText
    private lateinit var fullname: EditText
    private lateinit var db: MonsterMathDBHelper
    private lateinit var register: Button

    // Override onCreate method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register) // Set the layout for the activity

        // Initialize UI elements and database helper
        username = findViewById(R.id.Username)
        password = findViewById(R.id.Password)
        confirm = findViewById(R.id.ConfirmPassword)
        email = findViewById(R.id.Email)
        fullname = findViewById(R.id.Name)
        db = MonsterMathDBHelper(this)
        register = findViewById(R.id.registerButton)

        // Set up click listener for the register button
        register.setOnClickListener {
            // Get input values from EditText fields
            val userText = username.text.toString().toLowerCase()
            val passText = password.text.toString()
            val conText = confirm.text.toString()
            val emailText = email.text.toString()
            val fullnameText = fullname.text.toString()

            // Check if any field is empty
            if (TextUtils.isEmpty(userText) || TextUtils.isEmpty(passText) || TextUtils.isEmpty(emailText) || TextUtils.isEmpty(fullnameText)) {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
            } else {
                // Check if password is at least 6 characters long
                if (passText.length < 6) {
                    Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                } else {
                    // Check if password matches the confirmation
                    if (passText == conText) {
                        // Hash the password
                        val hashedPassword = hashPassword(passText)
                        // Insert user data into the database
                        val saveData = db.insertData(userText, hashedPassword, emailText, fullnameText)

                        if (saveData) {
                            // Show a toast message indicating successful registration
                            Toast.makeText(this, "Sign Up complete", Toast.LENGTH_SHORT).show()
                            // Navigate to the Start activity
                            val intent = Intent(this, Start::class.java)
                            startActivity(intent)
                        } else {
                            // Show a toast message indicating that the user already exists
                            Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Show a toast message indicating that passwords don't match
                        Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Find the return button by its ID
        val returnToHomeButton: Button = findViewById(R.id.ReturnToHome)

        // Set up click listener for the return button
        returnToHomeButton.setOnClickListener {
            // Navigate to the Start activity
            val intent = Intent(this, Start::class.java)
            startActivity(intent)
        }
    }
}
