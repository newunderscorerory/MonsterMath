package com.example.monstermath.Controller

// Import necessary Android packages and classes
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.R

// Define the ChooseDifficulty activity
class ChooseDifficulty : AppCompatActivity() {

    // Override onCreate method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.difficulty)

        // Find UI elements by their IDs
        val difficultyButton = findViewById<Button>(R.id.btnStartGame)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        // Set up click listener for the difficulty button
        difficultyButton.setOnClickListener {
            // Get the ID of the selected radio button from the radio group
            val selectedDifficultyId = radioGroup.checkedRadioButtonId
            // Find the selected radio button by its ID and get its text (difficulty level)
            val selectedDifficulty = findViewById<RadioButton>(selectedDifficultyId).text.toString()
            // Create an intent to start the Game activity and pass the selected difficulty as an extra
            val intent = Intent(this, Game::class.java)
            intent.putExtra("DIFFICULTY", selectedDifficulty)
            startActivity(intent)
        }
    }
}
