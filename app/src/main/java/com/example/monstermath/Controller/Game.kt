package com.example.monstermath.Controller
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.Model.MathQuestions
import com.example.monstermath.Model.MathQuestionsDBHelper
import com.example.monstermath.R
class Game : AppCompatActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var questionsTextView: TextView
    private lateinit var dbHelper: MathQuestionsDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        timerTextView = findViewById(R.id.Countdown)
        questionsTextView = findViewById(R.id.Question)
        dbHelper = MathQuestionsDBHelper(this)
        dbHelper.insertDefaultQuestions()

        // Start the countdown timer
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
            }
        }
        countDownTimer.start()

        // Fetch and display a random question
        displayRandomQuestion()
    }

    private fun displayRandomQuestion() {
        val questionList = dbHelper.getAllQuestions()
        if (questionList.isNotEmpty()) {
            val randomIndex = (0 until questionList.size).random()
            val randomQuestion = questionList[randomIndex]
            questionsTextView.text = randomQuestion.question
        } else {
            questionsTextView.text = "No questions available"
        }
    }

}


