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

    private lateinit var dbHelper: MathQuestionsDBHelper
    private lateinit var questions: List<MathQuestions>
    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var timer: CountDownTimer
    private lateinit var questionEditText: TextView
    private lateinit var answerEditText: TextView
    private lateinit var infoTextView: TextView
    private lateinit var countdownTextView: TextView
    private var timeLeftMillis: Long = 60000
    private var timerRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        questionEditText = findViewById(R.id.Question)
        answerEditText = findViewById(R.id.Answer)
        infoTextView = findViewById(R.id.Info)
        countdownTextView = findViewById(R.id.Countdown)
        dbHelper = MathQuestionsDBHelper(this)
        questions = dbHelper.getAllQuestions()


        displayNextQuestion()
    }


    private fun initializeTimer() {
        timer = object : CountDownTimer(timeLeftMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftMillis = millisUntilFinished
                updateCountdownText()
            }

            override fun onFinish() {
                endGame()
            }
        }
    }

    private fun startTimer() {
        timer.start()
        timerRunning = true
    }

    private fun updateCountdownText() {
        val secondsLeft = timeLeftMillis / 1000
        countdownTextView.text = "Time left: $secondsLeft seconds"
    }

    private fun displayNextQuestion() {
        if (currentQuestionIndex < questions.size) {
            val currentQuestion = questions[currentQuestionIndex]
            questionEditText.text = currentQuestion.question

            if (!timerRunning) {
                startTimer()
            }

            answerEditText.setOnEditorActionListener { _, _, _ ->
                val userAnswer = answerEditText.text.toString().toIntOrNull()
                if (userAnswer != null && userAnswer == currentQuestion.answer) {
                    score++
                    infoTextView.text = "Correct! Type your answer here!"
                } else {
                    infoTextView.text = "Incorrect! Type your answer here!"
                }
                answerEditText.text = ""

                currentQuestionIndex++
                if (currentQuestionIndex < questions.size) {
                    displayNextQuestion()
                } else {
                    endGame()
                }
                true
            }
        } else {
            endGame()
        }
    }



    private fun endGame() {
        timer.cancel()
        countdownTextView.text = "Time's up!"
        Toast.makeText(this, "Game over! Your score is $score", Toast.LENGTH_LONG).show()
    }
}
