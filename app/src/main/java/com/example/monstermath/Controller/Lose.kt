package com.example.monstermath.Controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.R

class Lose : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lose)

        val returnButton: Button = findViewById(R.id.winReturn)
        returnButton.setOnClickListener {
            val intent = Intent(this, StartGame::class.java)
            startActivity(intent)
        }

    }
}