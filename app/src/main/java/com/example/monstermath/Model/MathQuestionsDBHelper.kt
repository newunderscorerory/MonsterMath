package com.example.monstermath.Model
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class MathQuestionsDBHelper(context: Context) : SQLiteOpenHelper(context, "MathQuestions", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE MathQuestions (" +
                "id INTEGER PRIMARY KEY," +
                "category TEXT," +
                "question TEXT," +
                "answer INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS MathQuestions")
        onCreate(db)
    }

    internal fun insertDefaultQuestions() {
        val db = writableDatabase
        addQuestion("Addition and Subtraction", "What is 5 + 3?", 8)
        addQuestion("Addition and Subtraction", "What is 10 - 4?", 6)

        addQuestion("Multiplication and Division", "What is 4 * 5?", 20)
        addQuestion("Multiplication and Division", "What is 12 / 3?", 4)

        addQuestion("All", "What is 2 + 3 * 4?", 14)
        addQuestion("All", "What is (6 - 2) * 5?", 20)
        db.close()
    }

    fun addQuestion(category: String, question: String, answer: Int): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues().apply {
            put("category", category)
            put("question", question)
            put("answer", answer)
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
            val answerIndex = it.getColumnIndex("answer")

            while (it.moveToNext()) {
                val id = it.getInt(idIndex)
                val category = it.getString(categoryIndex)
                val question = it.getString(questionIndex)
                val answer = it.getInt(answerIndex)
                questionList.add(MathQuestions(id, category, question, answer))
            }
        }

        cursor?.close()
        db.close()
        return questionList
    }


}
