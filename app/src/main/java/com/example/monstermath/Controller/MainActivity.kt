package com.example.monstermath.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.monstermath.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val registerButton: Button = findViewById(R.id.register)
        registerButton.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        val loginButton: Button = findViewById(R.id.login)
        loginButton.setOnClickListener {
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
        }


        val adminButton: Button = findViewById(R.id.teachers)
        adminButton.setOnClickListener {
            val intent = Intent(this, AdminLogIn::class.java)
            startActivity(intent)
        }
    }
}