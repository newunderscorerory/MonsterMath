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
// Define the AdminAddUser activity
class AdminAddUser : AppCompatActivity() {

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
        setContentView(R.layout.register)

        // Initialize UI elements
        username = findViewById(R.id.Username)
        password = findViewById(R.id.Password)
        confirm = findViewById(R.id.ConfirmPassword)
        email = findViewById(R.id.Email)
        fullname = findViewById(R.id.Name)
        db = MonsterMathDBHelper(this)
        register = findViewById(R.id.registerButton)

        // Set up click listener for register button
        register.setOnClickListener {
            // Get text input from EditText fields
            val userText = username.text.toString().toLowerCase()
            val passText = password.text.toString()
            val conText = confirm.text.toString()
            val emailText = email.text.toString()
            val fullnameText = fullname.text.toString()

            // Check if any field is empty
            if (TextUtils.isEmpty(userText) || TextUtils.isEmpty(passText) || TextUtils.isEmpty(emailText) || TextUtils.isEmpty(fullnameText)) {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
            } else {
                // Check if password matches confirm password
                if (passText == conText) {
                    // Hash the password
                    val hashedPassword = hashPassword(passText)
                    // Insert user data into the database
                    val saveData = db.insertData(userText, hashedPassword, emailText, fullnameText)
                    // Check if data was saved successfully
                    if (saveData) {
                        Toast.makeText(this, "Sign Up complete", Toast.LENGTH_SHORT).show()
                        // Redirect to Admin activity
                        val intent = Intent(this, Admin::class.java)
                        startActivity(intent)
                    } else {
                        // Show toast if user already exists
                        Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Show toast if passwords don't match
                    Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Set up click listener for return to home button
        val returnToHomeButton: Button = findViewById(R.id.ReturnToHome)
        returnToHomeButton.setOnClickListener {
            // Redirect to Admin activity
            val intent = Intent(this, Admin::class.java)
            startActivity(intent)
        }
    }
}

