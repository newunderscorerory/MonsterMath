package com.example.monstermath.Model
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class MathQuestionsDBHelper(context: Context) : SQLiteOpenHelper(context, "MathQuestions", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE MathQuestions (" +
                    "id INTEGER PRIMARY KEY," +
                    "category TEXT," +
                    "question TEXT," +
                    "correctAnswer INTEGER," +
                    "option1 INTEGER," +
                    "option2 INTEGER," +
                    "option3 INTEGER," +
                    "option4 INTEGER)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS MathQuestions")
        onCreate(db)
    }

    internal fun insertDefaultQuestions() {
        val db = writableDatabase
        addQuestionWithOptions("Addition and Subtraction", "What is 5 + 3?", 8, listOf(6, 7, 8, 9))
        addQuestionWithOptions("Addition and Subtraction", "What is 10 - 4?", 6, listOf(5, 6, 7, 8))

        addQuestionWithOptions(
            "Multiplication and Division",
            "What is 4 * 5?",
            20,
            listOf(15, 18, 20, 22)
        )
        addQuestionWithOptions(
            "Multiplication and Division",
            "What is 12 / 3?",
            4,
            listOf(2, 3, 4, 5)
        )

        addQuestionWithOptions("All", "What is 2 + 3 * 4?", 14, listOf(9, 14, 18, 20))
        addQuestionWithOptions("All", "What is (6 - 2) * 5?", 20, listOf(16, 18, 20, 24))
        db.close()
    }

    fun addQuestionWithOptions(
        category: String,
        question: String,
        answer: Int,
        options: List<Int>
    ): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues().apply {
            put("category", category)
            put("question", question)
            put("correctAnswer", answer)
            put("option1", options[0])
            put("option2", options[1])
            put("option3", options[2])
            put("option4", options[3])
        }
        val result = db.insert("MathQuestions", null, cv)
        db.close()
        return result != -1L
    }

    fun getAllQuestions(): List<MathQuestions> {
        val questionList = mutableListOf<MathQuestions>()
        val query = "SELECT * FROM MathQuestions"
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery(query, null)

        cursor?.use {
            val idIndex = it.getColumnIndex("id")
            val categoryIndex = it.getColumnIndex("category")
            val questionIndex = it.getColumnIndex("question")
            val correctAnswerIndex = it.getColumnIndex("correctAnswer")
            val option1Index = it.getColumnIndex("option1")
            val option2Index = it.getColumnIndex("option2")
            val option3Index = it.getColumnIndex("option3")
            val option4Index = it.getColumnIndex("option4")

            while (it.moveToNext()) {
                val id = it.getInt(idIndex)
                val category = it.getString(categoryIndex)
                val question = it.getString(questionIndex)
                val correctAnswer = it.getInt(correctAnswerIndex)
                val options = listOf(
                    it.getInt(option1Index),
                    it.getInt(option2Index),
                    it.getInt(option3Index),
                    it.getInt(option4Index)
                )
                questionList.add(MathQuestions(id, category, question, correctAnswer, options))
            }
        }

        cursor?.close()
        db.close()
        return questionList
    }
}

