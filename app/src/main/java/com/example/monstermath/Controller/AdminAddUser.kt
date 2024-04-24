package com.example.monstermath.Controller

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.monstermath.Model.MonsterMathDBHelper
import com.example.monstermath.R
import com.example.monstermath.Utils.PasswordHashing.hashPassword

class AdminAddUser : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var confirm: EditText
    private lateinit var email: EditText
    private lateinit var fullname: EditText
    private lateinit var db: MonsterMathDBHelper
    private lateinit var register: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        username = findViewById(R.id.Username)
        password = findViewById(R.id.Password)
        confirm = findViewById(R.id.ConfirmPassword)
        email = findViewById(R.id.Email)
        fullname = findViewById(R.id.Name)
        db = MonsterMathDBHelper(this)
        register = findViewById(R.id.registerButton)


        register.setOnClickListener {
            val userText = username.text.toString().toLowerCase()
            val passText = password.text.toString()
            val conText = confirm.text.toString()
            val emailText = email.text.toString()
            val fullnameText = fullname.text.toString()

            if (TextUtils.isEmpty(userText) || TextUtils.isEmpty(passText) || TextUtils.isEmpty(emailText) || TextUtils.isEmpty(fullnameText)) {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
            } else {
                if (passText == conText) {
                    val hashedPassword = hashPassword(passText)
                    val saveData = db.insertData(userText, hashedPassword, emailText, fullnameText)

                    if (saveData) {
                        Toast.makeText(this, "Sign Up complete", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Admin::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val returnToHomeButton: Button = findViewById(R.id.ReturnToHome)
        returnToHomeButton.setOnClickListener {
            val intent = Intent(this, Admin::class.java)
            startActivity(intent)
        }
    }
}

