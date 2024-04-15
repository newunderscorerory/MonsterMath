package com.example.monstermath.Controller

import android.content.Context
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

var globalUser: String = ""

class LogIn : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var db: MonsterMathDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        usernameEditText = findViewById(R.id.usernameLogIn)
        passwordEditText = findViewById(R.id.passwordLogIn)
        loginButton = findViewById(R.id.go)
        registerButton = findViewById(R.id.takeToRegister)
        db = MonsterMathDBHelper(this)

        db.insertDefualtRewards()

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().toLowerCase()
            val password = passwordEditText.text.toString()

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            } else {
                val hashedPassword = hashPassword(password)
                val isAuthenticated = db.checkPass(username, hashedPassword)
                if (isAuthenticated) {
                    globalUser = username
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                    // Navigate to the menu activity
                    val intent = Intent(this, StartGame::class.java)
                    intent.putExtra("username", username)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }
        }


        registerButton.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            finish()
        }
    }
}
