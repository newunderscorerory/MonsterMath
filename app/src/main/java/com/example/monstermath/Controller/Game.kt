package com.example.monstermath.Controller

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.Model.MonsterMathDBHelper
import com.example.monstermath.Model.Reward
import com.example.monstermath.R

class Game : AppCompatActivity() {

    // UI elements
    private lateinit var timerTextView: TextView
    private lateinit var questionsTextView: TextView
    private lateinit var optionsGridView: GridView
    private lateinit var scoreTextView: TextView
    private lateinit var progressBar: ProgressBar

    // Game data
    private var score: Int = 0
    private lateinit var username: String
    private lateinit var dbHelper: MonsterMathDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        // Initialize UI elements
        timerTextView = findViewById(R.id.Countdown)
        questionsTextView = findViewById(R.id.Question)
        scoreTextView = findViewById(R.id.scoreTextView)
        optionsGridView = findViewById(R.id.optionsGridView)
        progressBar = findViewById(R.id.progressBar)
        progressBar.max = 60 // Set maximum value for the progress bar

        // Initialize database helper and get username from global variable
        dbHelper = MonsterMathDBHelper(this)
        username = globalUser

        // Insert default questions into the database if needed
        dbHelper.insertDefaultQuestionsIfNeeded()

        // Create a countdown timer for the game
        val countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update timer text and progress bar
                val secondsRemaining = millisUntilFinished / 1000
                val minutes = secondsRemaining / 60
                val seconds = secondsRemaining % 60
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
                progressBar.progress = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                // Actions to perform when the game timer finishes
                timerTextView.text = "00:00"
                updateHighScore() // Update high score in the database

                // Start appropriate activity based on the final score
                val intent = if (score >= 100) {
                    Intent(this@Game, Win::class.java)
                } else {
                    Intent(this@Game, Lose::class.java)
                }
                startActivity(intent)
                finish() // Finish the current activity
            }
        }
        countDownTimer.start() // Start the countdown timer

        // Set click listener for options grid view
        optionsGridView.setOnItemClickListener { _, _, position, _ ->
            val selectedOption = optionsGridView.adapter.getItem(position) as String
            val currentQuestion = questionsTextView.text.toString()
            val currentDifficulty = intent.getStringExtra("DIFFICULTY")

            // Get questions based on the selected difficulty
            val questionList = dbHelper.getQuestionsByDifficulty(currentDifficulty!!)

            // Find correct answer for the current question
            val correctAnswer = questionList.find { it.question == currentQuestion }?.correctAnswer.toString()

            // Update score if the selected option is correct
            if (selectedOption == correctAnswer) {
                score += 10
                updateScoreDisplay(score) // Update score display
            }

            // Display a new random question
            displayRandomQuestion(currentDifficulty)
        }

        // Get selected difficulty from intent and display a random question
        val selectedDifficulty = intent.getStringExtra("DIFFICULTY")
        if (selectedDifficulty != null) {
            displayRandomQuestion(selectedDifficulty)
        }
    }

    // Display a random question based on the selected difficulty
    private fun displayRandomQuestion(difficulty: String) {
        val questionList = dbHelper.getQuestionsByDifficulty(difficulty)
        if (questionList.isNotEmpty()) {
            val randomIndex = (0 until questionList.size).random()
            val randomQuestion = questionList[randomIndex]
            questionsTextView.text = randomQuestion.question

            // Convert question options to string list
            val optionsAsString = randomQuestion.options.map { it.toString() }

            // Set options adapter for grid view
            val optionsAdapter =
                ArrayAdapter<String>(this, R.layout.answers_layout, optionsAsString)
            optionsGridView.adapter = optionsAdapter
        } else {
            questionsTextView.text = "No questions available"
        }
    }

    // Update score display on the UI
    private fun updateScoreDisplay(score: Int) {
        scoreTextView.text = "Score: $score"
    }

    // Update the high score in the database
    private fun updateHighScore() {
        dbHelper.updateHighScore(username, score)
    }
}

