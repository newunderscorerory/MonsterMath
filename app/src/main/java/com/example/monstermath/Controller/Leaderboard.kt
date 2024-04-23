package com.example.monstermath.Controller

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.Model.MonsterMathDBHelper
import com.example.monstermath.R

class Leaderboard : AppCompatActivity() {

    private lateinit var dbHelper: MonsterMathDBHelper
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.leaderboard)

        dbHelper = MonsterMathDBHelper(this)
        listView = findViewById(R.id.listViewLeaderboard)


        val highScores = dbHelper.getHighScores()
        val adapter = ArrayAdapter(this, R.layout.list_item, R.id.textView, highScores)
        listView.adapter = adapter

        val returnButton: Button = findViewById(R.id.LeaderReturn)
        returnButton.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }

    }
}

