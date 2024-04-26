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
import com.example.monstermath.Utils.PasswordHashing

class EditUser : AppCompatActivity() {

    private lateinit var dbHelper: MonsterMathDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edituser)
        dbHelper = MonsterMathDBHelper(this)
        val username = intent.getStringExtra("username")
        if (username != null) {
            val user = dbHelper.getCustomer(username)
            findViewById<EditText>(R.id.UsernameEdit).setText(user.username)
            findViewById<EditText>(R.id.EmailEdit).setText(user.email)
            findViewById<EditText>(R.id.NameEdit).setText(user.fullname)
            findViewById<Button>(R.id.editButton).setOnClickListener {
                val newPassword = findViewById<EditText>(R.id.PasswordEdit).text.toString()
                val confirmPassword = findViewById<EditText>(R.id.ConfirmPasswordEdit).text.toString()
                if (newPassword.isEmpty() || newPassword.length <= 6) {
                    Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (newPassword != confirmPassword) {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val hashedPassword = PasswordHashing.hashPassword(newPassword)
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
                dbHelper.updateCustomer(updatedUser)
                Toast.makeText(this, "User information updated", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {

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



