package com.example.monstermath.Controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.Model.MonsterMathDBHelper
import com.example.monstermath.Model.Reward
import com.example.monstermath.R

class PlayerProfile : AppCompatActivity() {

    private lateinit var dbHelper: MonsterMathDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.playerprofile)

        dbHelper = MonsterMathDBHelper(this)

        val username = globalUser

        val user = dbHelper.getCustomer(username)

        if (user != null) {
            val usernameTextView = findViewById<TextView>(R.id.usernameTextView)
            val emailTextView = findViewById<TextView>(R.id.emailTextView)
            val fullNameTextView = findViewById<TextView>(R.id.fullnameTextView)
            val highScoreTextView = findViewById<TextView>(R.id.highScoreTextView)

            usernameTextView.text = user.username
            emailTextView.text = "Email: ${user.email}"
            fullNameTextView.text = "Full Name: ${user.fullname}"
            highScoreTextView.text = "High Score: ${user.highScore}"

            val earnedRewards = dbHelper.getEarnedRewards(username)
            displayEarnedRewards(earnedRewards)
        } else {
            Toast.makeText(this, "Failed to retrieve user information", Toast.LENGTH_SHORT).show()
        }

        val returnButton: Button = findViewById(R.id.back)
        returnButton.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }
    }

    private fun displayEarnedRewards(earnedRewards: List<Reward>) {
        for (reward in earnedRewards) {
            val imageViewId = when (reward.id) {
                1 -> R.id.reward1ImageView
                2 -> R.id.reward2ImageView
                3 -> R.id.reward3ImageView
                else -> null
            }
            imageViewId?.let {
                val imageView = findViewById<ImageView>(it)
                imageView.setImageResource(reward.imageResourseID)
                imageView.visibility = View.VISIBLE
            }
        }
    }

}
