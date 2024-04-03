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


        addQuestionWithOptions("Addition and Subtraction", "What is 5 + 3?", 8, listOf(7, 8, 6, 9))
        addQuestionWithOptions("Addition and Subtraction", "What is 10 - 4?", 6, listOf(7, 5, 8, 6))
        addQuestionWithOptions("Addition and Subtraction", "What is 10 + 2?", 12, listOf(12, 7, 4, 9))
        addQuestionWithOptions("Addition and Subtraction", "What is 7 + 6?", 13, listOf(10, 15, 12, 13))
        addQuestionWithOptions("Addition and Subtraction", "What is 15 - 9?", 6, listOf(10, 8, 4, 6))
        addQuestionWithOptions("Addition and Subtraction", "What is 20 + 3?", 23, listOf(21, 19, 25, 23))
        addQuestionWithOptions("Addition and Subtraction", "What is 18 - 7?", 11, listOf(10, 11, 8, 13))
        addQuestionWithOptions("Addition and Subtraction", "What is 25 + 9?", 34, listOf(28, 32, 34, 30))
        addQuestionWithOptions("Addition and Subtraction", "What is 17 + 9?", 26, listOf(28, 23, 26, 25))
        addQuestionWithOptions("Addition and Subtraction", "What is 42 - 18?", 24, listOf(22, 24, 20, 26))
        addQuestionWithOptions("Addition and Subtraction", "What is 55 + 27?", 82, listOf(85, 80, 75, 82))
        addQuestionWithOptions("Addition and Subtraction", "What is 63 - 29?", 34, listOf(32, 36, 34, 30))
        addQuestionWithOptions("Addition and Subtraction", "What is 98 + 16?", 114, listOf(105, 114, 120, 110))
        addQuestionWithOptions("Addition and Subtraction", "What is 116 - 42?", 74, listOf(78, 68, 74, 72))
        addQuestionWithOptions("Addition and Subtraction", "What is 135 + 28?", 163, listOf(160, 155, 168, 163))
        addQuestionWithOptions("Addition and Subtraction", "What is 142 - 77?", 65, listOf(65, 68, 58, 62))
        addQuestionWithOptions("Addition and Subtraction", "What is 178 + 35?", 213, listOf(205, 200, 220, 213))
        addQuestionWithOptions("Addition and Subtraction", "What is 197 - 92?", 105, listOf(105, 98, 102, 108))


        addQuestionWithOptions("Multiplication and Division", "What is 4 * 5?", 20, listOf(22, 15, 18, 20))
        addQuestionWithOptions("Multiplication and Division", "What is 12 / 3?", 4, listOf(5, 3, 2, 4))
        addQuestionWithOptions("Multiplication and Division", "What is 2 * 9?", 18, listOf(21, 15, 18, 12))
        addQuestionWithOptions("Multiplication and Division", "What is 3 * 7?", 21, listOf(24, 18, 15, 21))
        addQuestionWithOptions("Multiplication and Division", "What is 4 * 8?", 32, listOf(36, 28, 24, 32))
        addQuestionWithOptions("Multiplication and Division", "What is 6 * 6?", 36, listOf(33, 30, 39, 36))
        addQuestionWithOptions("Multiplication and Division", "What is 7 * 5?", 35, listOf(32, 35, 38, 28))
        addQuestionWithOptions("Multiplication and Division", "What is 8 * 4?", 32, listOf(28, 24, 36, 32))
        addQuestionWithOptions("Multiplication and Division", "What is 9 * 3?", 27, listOf(30, 24, 21, 27))
        addQuestionWithOptions("Multiplication and Division", "What is 10 * 2?", 20, listOf(23, 17, 14, 20))
        addQuestionWithOptions("Multiplication and Division", "What is 11 * 3?", 33, listOf(36, 27, 30, 33))
        addQuestionWithOptions("Multiplication and Division", "What is 12 * 12?", 144, listOf(132, 120, 144, 156))


        addQuestionWithOptions("All", "What is 5 + 3?", 8, listOf(7, 8, 6, 9))
        addQuestionWithOptions("All", "What is 10 - 4?", 6, listOf(7, 5, 8, 6))
        addQuestionWithOptions("All", "What is 10 + 2?", 12, listOf(12, 7, 4, 9))
        addQuestionWithOptions("All", "What is 7 + 6?", 13, listOf(10, 15, 12, 13))
        addQuestionWithOptions("All", "What is 15 - 9?", 6, listOf(10, 8, 4, 6))
        addQuestionWithOptions("All", "What is 20 + 3?", 23, listOf(21, 19, 25, 23))
        addQuestionWithOptions("All", "What is 18 - 7?", 11, listOf(10, 11, 8, 13))
        addQuestionWithOptions("All", "What is 25 + 9?", 34, listOf(28, 32, 34, 30))
        addQuestionWithOptions("All", "What is 30 * 4?", 120, listOf(110, 120, 100, 130))
        addQuestionWithOptions("All", "What is 36 / 6?", 6, listOf(7, 4, 6, 5))
        addQuestionWithOptions("All", "What is 9 + 4?", 13, listOf(10, 12, 13, 15))
        addQuestionWithOptions("All", "What is 14 - 7?", 7, listOf(11, 5, 7, 9))
        addQuestionWithOptions("All", "What is 20 + 3?", 23, listOf(19, 21, 23, 25))
        addQuestionWithOptions("All", "What is 18 - 7?", 11, listOf(8, 10, 11, 13))
        addQuestionWithOptions("All", "What is 25 + 9?", 34, listOf(28, 30, 32, 34))
        addQuestionWithOptions("All", "What is 30 * 4?", 120, listOf(100, 110, 120, 130))
        addQuestionWithOptions("All", "What is 36 / 6?", 6, listOf(4, 5, 6, 7))
        addQuestionWithOptions("All", "What is 9 + 4?", 13, listOf(10, 12, 13, 15))
        addQuestionWithOptions("All", "What is 14 - 7?", 7, listOf(5, 7, 9, 11))
        addQuestionWithOptions("All", "What is 8 * 3?", 24, listOf(18, 20, 24, 27))
        addQuestionWithOptions("All", "What is 17 + 8?", 25, listOf(20, 22, 25, 28))
        addQuestionWithOptions("All", "What is 25 - 12?", 13, listOf(9, 11, 13, 15))
        addQuestionWithOptions("All", "What is 6 * 7?", 42, listOf(36, 40, 42, 48))
        addQuestionWithOptions("All", "What is 49 / 7?", 7, listOf(5, 6, 7, 8))
        addQuestionWithOptions("All", "What is 11 + 9?", 20, listOf(15, 18, 20, 22))
        addQuestionWithOptions("All", "What is 20 - 16?", 4, listOf(2, 4, 6, 8))

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

