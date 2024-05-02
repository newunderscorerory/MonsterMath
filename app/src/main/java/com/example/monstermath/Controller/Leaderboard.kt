package com.example.monstermath.Controller

// Import necessary Android packages and classes
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.Model.MonsterMathDBHelper
import com.example.monstermath.R

//Define the Leaderboard activity
class Leaderboard : AppCompatActivity() {

    // Declare properties
    private lateinit var dbHelper: MonsterMathDBHelper
    private lateinit var listView: ListView

    // Override onCreate method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.leaderboard)

        // Initialize the database helper
        dbHelper = MonsterMathDBHelper(this)

        // Find the ListView in the layout
        listView = findViewById(R.id.listViewLeaderboard)

        // Retrieve high scores from the database
        val highScores = dbHelper.getHighScores()

        // Create an ArrayAdapter to populate the ListView with high scores
        val adapter = ArrayAdapter(this, R.layout.list_item, R.id.textView, highScores)
        listView.adapter = adapter

        // Set up click listener for the return button
        val returnButton: Button = findViewById(R.id.LeaderReturn)
        returnButton.setOnClickListener {
            // Return to the MainMenu activity
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }
    }
}

