package com.example.monstermath.Controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.Model.Customer
import com.example.monstermath.Model.MonsterMathDBHelper
import com.example.monstermath.R

class EditUser : AppCompatActivity() {

    private lateinit var dbHelper: MonsterMathDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edituser)

        dbHelper = MonsterMathDBHelper(this)

        val username = intent.getStringExtra("username")

        // Check if username is not null before using it
        if (username != null) {
            // Retrieve user information from the database based on the username
            val user = dbHelper.getCustomer(username)

            // Populate EditText fields with user information
            findViewById<EditText>(R.id.UsernameEdit).setText(user.username)
            findViewById<EditText>(R.id.PasswordEdit).setText(user.password)
            findViewById<EditText>(R.id.EmailEdit).setText(user.email)
            findViewById<EditText>(R.id.NameEdit).setText(user.fullname)

            findViewById<Button>(R.id.editButton).setOnClickListener {
                // Update user information in the database
                val updatedUser = Customer(
                    username,
                    findViewById<EditText>(R.id.PasswordEdit).text.toString(),
                    findViewById<EditText>(R.id.EmailEdit).text.toString(),
                    findViewById<EditText>(R.id.NameEdit).text.toString(),

                    user.highScore
                )
                dbHelper.updateCustomer(updatedUser)
                Toast.makeText(this, "User information updated", Toast.LENGTH_SHORT).show()
                finish() // Finish the activity after updating
            }
        } else {
            // Handle the case where username is null (optional)
            Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT).show()
            finish()
        }


        val returnButton: Button = findViewById(R.id.retrunToAdmin)
        returnButton.setOnClickListener {
            val intent = Intent(this, Admin::class.java)
            startActivity(intent)
            finish()
        }


    }
}

