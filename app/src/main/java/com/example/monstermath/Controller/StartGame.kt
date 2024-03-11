package com.example.monstermath.Controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.R

class StartGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start)

        val startButton: Button = findViewById(R.id.start)
        startButton.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }


    }
}
