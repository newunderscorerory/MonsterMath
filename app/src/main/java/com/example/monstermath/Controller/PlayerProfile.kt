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

        // Retrieve the username extra from the Intent
        val username = intent.getStringExtra("USERNAME")

        // Retrieve user information from the database using the username
        val user = dbHelper.getAllCustomers().find { it.username == username }

        // Populate the UI with user information
        findViewById<TextView>(R.id.usernameTextView).text = "Username: ${user?.username}"
        findViewById<TextView>(R.id.emailTextView).text = "Email: ${user?.email}"
        findViewById<TextView>(R.id.fullnameTextView).text = "Fullname: ${user?.fullname}"
    }

}
