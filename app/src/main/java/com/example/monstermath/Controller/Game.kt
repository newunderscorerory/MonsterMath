package com.example.monstermath.Controller

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.Model.CustomerDBHelper
import com.example.monstermath.Model.MathQuestionsDBHelper
import com.example.monstermath.R

class Game : AppCompatActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var questionsTextView: TextView
    private lateinit var dbHelper: MathQuestionsDBHelper
    private lateinit var optionsGridView: GridView
    private lateinit var scoreTextView: TextView
    private var score: Int = 0
    private lateinit var username: String
    private lateinit var customerDBHelper: CustomerDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        timerTextView = findViewById(R.id.Countdown)
        questionsTextView = findViewById(R.id.Question)
        dbHelper = MathQuestionsDBHelper(this)
        dbHelper.insertDefaultQuestions()
        scoreTextView = findViewById(R.id.scoreTextView)
        optionsGridView = findViewById(R.id.optionsGridView)

        // Retrieve the username from the intent or wherever it's stored
        username = "username" // Replace with actual username retrieval

        // Initialize CustomerDBHelper
        customerDBHelper = CustomerDBHelper(this)

        val millisInFuture: Long = 60000
        val countDownInterval: Long = 1000
        val countDownTimer = object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                val minutes = secondsRemaining / 60
                val seconds = secondsRemaining % 60
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                timerTextView.text = "00:00"
                updateHighScore()
                if (score >= 100) {
                    val intent = Intent(this@Game, Win::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this@Game, Loose::class.java)
                    startActivity(intent)
                }
                finish()
            }
        }
        countDownTimer.start()

        optionsGridView.setOnItemClickListener { _, _, position, _ ->
            val selectedOption = optionsGridView.adapter.getItem(position) as String
            val questionList = dbHelper.getAllQuestions()

            val correctAnswer =
                questionList.find { it.question == questionsTextView.text }?.correctAnswer.toString()

            if (selectedOption == correctAnswer) {
                score += 10
                updateScoreDisplay(score)
            }

            displayRandomQuestion()
        }

        displayRandomQuestion()
    }

    private fun displayRandomQuestion() {
        val questionList = dbHelper.getAllQuestions()
        if (questionList.isNotEmpty()) {
            val randomIndex = (0 until questionList.size).random()
            val randomQuestion = questionList[randomIndex]
            questionsTextView.text = randomQuestion.question

            val optionsAsString = randomQuestion.options.map { it.toString() }

            val optionsAdapter =
                ArrayAdapter<String>(this, R.layout.answers_layout, optionsAsString)
            optionsGridView.adapter = optionsAdapter
        } else {
            questionsTextView.text = "No questions available"
        }
    }

    private fun updateScoreDisplay(score: Int) {
        scoreTextView.text = "Score: $score"
    }

    private fun updateHighScore() {
        val currentHighScore = customerDBHelper.getHighScore(username)
        if (score > currentHighScore) {
            customerDBHelper.updateHighScore(username, score)
        }
    }
}
