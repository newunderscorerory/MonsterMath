package com.example.monstermath.Controller
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.Model.MathQuestions
import com.example.monstermath.Model.MathQuestionsDBHelper
import com.example.monstermath.R
class QuizActivity : AppCompatActivity() {

    private lateinit var dbHelper: MathQuestionsDBHelper
    private lateinit var questions: List<MathQuestions>
    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var timer: CountDownTimer

    private lateinit var questionEditText: EditText
    private lateinit var answerEditText: EditText
    private lateinit var infoTextView: TextView
    private lateinit var countdownTextView: TextView
    private lateinit var imageView5: ImageView
    private lateinit var imageView6: ImageView
    private lateinit var imageView7: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        questionEditText = findViewById(R.id.Question)
        answerEditText = findViewById(R.id.Answer)
        infoTextView = findViewById(R.id.Info)
        countdownTextView = findViewById(R.id.Countdown)
        imageView5 = findViewById(R.id.imageView5)
        imageView6 = findViewById(R.id.imageView6)
        imageView7 = findViewById(R.id.imageView7)

        dbHelper = MathQuestionsDBHelper(this)
        questions = dbHelper.getAllQuestions()

        displayNextQuestion()

        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                countdownTextView.text = "Time left: $secondsLeft seconds"
            }

            override fun onFinish() {
                endGame()
            }
        }.start()
    }

    private fun displayNextQuestion() {
        if (currentQuestionIndex < questions.size) {
            val currentQuestion = questions[currentQuestionIndex]
            questionEditText.setText(currentQuestion.question)

            answerEditText.setOnEditorActionListener { _, _, _ ->
                val userAnswer = answerEditText.text.toString().toIntOrNull()
                if (userAnswer != null && userAnswer == currentQuestion.answer) {
                    score++
                    infoTextView.text = "Correct! Type your answer here!"
                } else {
                    infoTextView.text = "Incorrect! Type your answer here!"
                }
                answerEditText.text.clear()
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
        Toast.makeText(this, "Game over! Your score is $score", Toast.LENGTH_LONG).show()
        // Optionally, you can save the score to a database or perform other actions.
    }
}
