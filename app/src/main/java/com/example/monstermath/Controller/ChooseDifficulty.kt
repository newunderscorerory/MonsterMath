package com.example.monstermath.Controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.R

class ChooseDifficulty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.difficulty)

        val difficultyButton = findViewById<Button>(R.id.btnStartGame)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        difficultyButton.setOnClickListener {
            val selectedDifficultyId = radioGroup.checkedRadioButtonId
            val selectedDifficulty = findViewById<RadioButton>(selectedDifficultyId).text.toString()

            val intent = Intent(this, Game::class.java)
            intent.putExtra("DIFFICULTY", selectedDifficulty)
            startActivity(intent)
        }
    }
}
