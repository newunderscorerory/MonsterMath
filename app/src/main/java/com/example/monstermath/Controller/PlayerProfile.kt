package com.example.monstermath.Controller

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.R
import android.widget.TextView

import com.example.monstermath.Model.CustomerDBHelper

class PlayerProfile : AppCompatActivity() {

    private lateinit var dbHelper: CustomerDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.playerprofile)


        dbHelper = CustomerDBHelper(this)

        // Get the username from intent extras
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("USERNAME", "")

        // Retrieve user information from the database
        val user = dbHelper.getAllCustomers().find { it.username == username }

        // Populate the UI with user information
        findViewById<TextView>(R.id.usernameTextView).text = "Username: ${user?.username}"
        findViewById<TextView>(R.id.emailTextView).text = "Email: ${user?.email}"
        findViewById<TextView>(R.id.fullnameTextView).text = "Fullname: ${user?.fullname}"
    }
}
