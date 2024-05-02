package com.example.monstermath.Controller

// Import necessary Android packages and classes
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.Model.Customer
import com.example.monstermath.Model.MonsterMathDBHelper
import com.example.monstermath.R
import com.example.monstermath.Utils.PasswordHashing

// Define the EditUser activity
class EditUser : AppCompatActivity() {

    private lateinit var dbHelper: MonsterMathDBHelper

    // Override onCreate method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edituser)

        // Initialize the database helper
        dbHelper = MonsterMathDBHelper(this)

        // Get the username passed from the intent
        val username = intent.getStringExtra("username")
        if (username != null) {
            // Retrieve user data from the database based on the username
            val user = dbHelper.getCustomer(username)

            // Populate EditText fields with the user's data
            findViewById<EditText>(R.id.UsernameEdit).setText(user.username)
            findViewById<EditText>(R.id.EmailEdit).setText(user.email)
            findViewById<EditText>(R.id.NameEdit).setText(user.fullname)

            // Set up click listener for the edit button
            findViewById<Button>(R.id.editButton).setOnClickListener {
                // Get new password and confirm password from EditText fields
                val newPassword = findViewById<EditText>(R.id.PasswordEdit).text.toString()
                val confirmPassword = findViewById<EditText>(R.id.ConfirmPasswordEdit).text.toString()

                // Check if the new password is valid
                if (newPassword.isEmpty() || newPassword.length <= 6) {
                    Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Check if the new password matches the confirm password
                if (newPassword != confirmPassword) {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Hash the new password
                val hashedPassword = PasswordHashing.hashPassword(newPassword)

                // Create an updated user object with the new password
                val updatedUser = Customer(
                    username,
                    hashedPassword,
                    findViewById<EditText>(R.id.EmailEdit).text.toString(),
                    findViewById<EditText>(R.id.NameEdit).text.toString(),
                    user.highScore,
                    user.reward1,
                    user.reward2,
                    user.reward3
                )

                // Update user information in the database
                dbHelper.updateCustomer(updatedUser)

                // Show a toast message indicating successful user information update
                Toast.makeText(this, "User information updated", Toast.LENGTH_SHORT).show()

                // Finish the activity
                finish()
            }
        } else {
            // Show a toast message if the username is not found
            Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT).show()
            // Finish the activity
            finish()
        }

        // Set up click listener for the return button
        val returnButton: Button = findViewById(R.id.retrunToAdmin)
        returnButton.setOnClickListener {
            // Navigate back to the Admin activity
            val intent = Intent(this, Admin::class.java)
            startActivity(intent)
            finish()
        }
    }
}

