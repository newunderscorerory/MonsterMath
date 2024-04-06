package com.example.monstermath.Controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.R
import android.widget.TextView
import android.widget.Toast
import com.example.monstermath.Model.MonsterMathDBHelper

class PlayerProfile : AppCompatActivity() {

    private lateinit var dbHelper: MonsterMathDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.playerprofile)

        dbHelper = MonsterMathDBHelper(this)

        // Retrieve username from globalUser variable
        val username = globalUser

        // Retrieve user information from the database using the username
        val user = dbHelper.getCustomer(username)



        if (user != null) {
            val usernameTextView = findViewById<TextView>(R.id.usernameTextView)
            val emailTextView = findViewById<TextView>(R.id.emailTextView)
            val fullNameTextView = findViewById<TextView>(R.id.fullnameTextView)

            usernameTextView.text = user.username
            emailTextView.text = user.email  // Corrected this line to display the email
            fullNameTextView.text = user.fullname

        } else {
            // Handle case where user information is not found
            Toast.makeText(this, "Failed to retrieve user information", Toast.LENGTH_SHORT).show()
        }

        val returnButton: Button = findViewById(R.id.back)
        returnButton.setOnClickListener {
            val intent = Intent(this, StartGame::class.java)
            startActivity(intent)
        }

    }
}


