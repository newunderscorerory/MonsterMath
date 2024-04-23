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

    private lateinit var timerTextView: TextView
    private lateinit var questionsTextView: TextView
    private lateinit var dbHelper: MonsterMathDBHelper
    private lateinit var optionsGridView: GridView
    private lateinit var scoreTextView: TextView
    private var score: Int = 0
    private lateinit var username: String
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        timerTextView = findViewById(R.id.Countdown)
        questionsTextView = findViewById(R.id.Question)
        dbHelper = MonsterMathDBHelper(this)
        scoreTextView = findViewById(R.id.scoreTextView)
        optionsGridView = findViewById(R.id.optionsGridView)
        progressBar = findViewById(R.id.progressBar)
        progressBar.max = 60
        username = globalUser
        dbHelper.insertDefaultQuestionsIfNeeded()


        val millisInFuture: Long = 60000
        val countDownInterval: Long = 1000
        val countDownTimer = object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                val minutes = secondsRemaining / 60
                val seconds = secondsRemaining % 60
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
                progressBar.progress = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                timerTextView.text = "00:00"
                updateHighScore()
                if (score >= 100) {
                    val intent = Intent(this@Game, Win::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this@Game, Lose::class.java)
                    startActivity(intent)
                }
                finish()
            }
        }
        countDownTimer.start()

        optionsGridView.setOnItemClickListener { _, _, position, _ ->
            val selectedOption = optionsGridView.adapter.getItem(position) as String

            val currentQuestion = questionsTextView.text.toString()
            val currentDifficulty = intent.getStringExtra("DIFFICULTY")

            if (currentDifficulty != null) {
                val questionList = dbHelper.getQuestionsByDifficulty(currentDifficulty)
                val correctAnswer = questionList.find { it.question == currentQuestion }?.correctAnswer.toString()

                if (selectedOption == correctAnswer) {
                    score += 10
                    updateScoreDisplay(score)
                }

                displayRandomQuestion(currentDifficulty)
            }
        }

        val intent = intent
        val selectedDifficulty = intent.getStringExtra("DIFFICULTY")

        if (selectedDifficulty != null) {
            displayRandomQuestion(selectedDifficulty)
        }


    }


    private fun displayRandomQuestion(difficulty: String) {
        val questionList = dbHelper.getQuestionsByDifficulty(difficulty)
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
        dbHelper.updateHighScore(username, score)

    }




}
