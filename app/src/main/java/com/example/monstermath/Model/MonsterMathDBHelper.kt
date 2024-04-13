package com.example.monstermath.Model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private val DatabaseName = "MonsterMath.db"
private val ver = 1

class MonsterMathDBHelper(context: Context) : SQLiteOpenHelper(context, DatabaseName, null, ver) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE Customer (" +
                    "username TEXT PRIMARY KEY," +
                    "password TEXT," +
                    "email TEXT," +
                    "fullname TEXT," +
                    "highScore INTEGER DEFAULT 0)"
        )



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
        db?.execSQL("DROP TABLE IF EXISTS Customer")
        db?.execSQL("DROP TABLE IF EXISTS Class")
        db?.execSQL("DROP TABLE IF EXISTS UserClass")
        db?.execSQL("DROP TABLE IF EXISTS MathQuestions")
        onCreate(db)
    }

    fun insertData(
        username: String,
        password: String,
        email: String,
        fullname: String,
        highScore: Int = 0 // Default value for highScore
    ): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("username", username)
        cv.put("password", password)
        cv.put("email", email)
        cv.put("fullname", fullname)
        cv.put("highScore", highScore)
        val result = db.insert("Customer", null, cv)
        db.close()
        return result != -1L
    }


    fun getHighScores(): List<String> {
        val highScoreList = mutableListOf<String>()
        val query = "SELECT username, highScore FROM Customer ORDER BY highScore DESC"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        cursor.use {
            while (it.moveToNext()) {
                val username = it.getString(it.getColumnIndex("username"))
                val highScore = it.getInt(it.getColumnIndex("highScore"))
                val scoreEntry = "$username: $highScore"
                highScoreList.add(scoreEntry)
            }
        }

        cursor.close()
        db.close()
        return highScoreList
    }



    fun updateHighScore(username: String, newHighScore: Int) {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put("highScore", newHighScore)
        }
        val whereClause = "username = ? AND highScore < ?"
        val whereArgs = arrayOf(username, newHighScore.toString())
        db.update("Customer", cv, whereClause, whereArgs)
        db.close()
    }


    fun checkPass(username: String, password: String): Boolean {
        val db = this.writableDatabase
        val query = "SELECT * FROM Customer WHERE LOWER(username) = LOWER(?) AND password = ?"
        val cursor = db.rawQuery(query, arrayOf(username.toLowerCase(), password))
        val result = cursor.count > 0
        cursor.close()
        db.close()
        return result
    }


    fun getCustomer(username: String): Customer {
        val db = this.readableDatabase
        val columns = arrayOf("username", "password", "email", "fullname", "highScore")
        val selection = "username = ?"
        val selectionArgs = arrayOf(username)
        val cursor = db.query("Customer", columns, selection, selectionArgs, null, null, null)
        var customer = Customer("", "", "", "", 0)

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val retrievedUsername = cursor.getString(cursor.getColumnIndex("username"))
                val password = cursor.getString(cursor.getColumnIndex("password"))
                val email = cursor.getString(cursor.getColumnIndex("email"))
                val fullname = cursor.getString(cursor.getColumnIndex("fullname"))
                val highScore = cursor.getInt(cursor.getColumnIndex("highScore"))

                // Create a Customer object with the retrieved data
                customer = Customer(retrievedUsername, password, email, fullname, highScore)
            }
            cursor.close()
        }
        db.close()
        return customer
    }



    fun getAllCustomers(): List<Customer> {
        val customerList = mutableListOf<Customer>()
        val query = "SELECT * FROM Customer"
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery(query, null)

        cursor?.use {
            val usernameIndex = it.getColumnIndex("username")
            val passwordIndex = it.getColumnIndex("password")
            val emailIndex = it.getColumnIndex("email")
            val fullnameIndex = it.getColumnIndex("fullname")
            val highScoreIndex = it.getColumnIndex("highScore")

            while (it.moveToNext()) {
                val username = it.getString(usernameIndex)
                val password = it.getString(passwordIndex)
                val email = it.getString(emailIndex)
                val fullname = it.getString(fullnameIndex)
                val highScore = it.getInt(highScoreIndex)
                customerList.add(Customer(username, password, email, fullname, highScore))
            }
        }

        cursor?.close()
        db.close()
        return customerList
    }


    fun removeUser(username: String): Boolean {
        val db = this.writableDatabase
        val whereClause = "username = ?"
        val whereArgs = arrayOf(username)
        val result = db.delete("Customer", whereClause, whereArgs)
        db.close()
        return result > 0
    }

    fun updateCustomer(customer: Customer): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("password", customer.password)
            put("email", customer.email)
            put("fullname", customer.fullname)
            // Add other columns as needed
        }

        // Updating row
        return db.update(
            "Customer",
            values,
            "username = ?",
            arrayOf(customer.username)
        )
    }



    internal fun insertDefaultQuestions() {
        val db = writableDatabase


        // For addition questions
        addQuestionWithOptions("Addition", "What is 5 + 3?", 8, listOf(7, 9, 8, 6))
        addQuestionWithOptions("Addition", "What is 10 + 2?", 12, listOf(12, 9, 7, 4))
        addQuestionWithOptions("Addition", "What is 5 + 3?", 8, listOf(7, 9, 8, 6))
        addQuestionWithOptions("Addition", "What is 10 + 2?", 12, listOf(12, 9, 7, 4))
        addQuestionWithOptions("Addition", "What is 8 + 5?", 13, listOf(12, 13, 14, 15))
        addQuestionWithOptions("Addition", "What is 17 + 9?", 26, listOf(25, 26, 27, 28))
        addQuestionWithOptions("Addition", "What is 32 + 18?", 50, listOf(48, 49, 50, 51))
        addQuestionWithOptions("Addition", "What is 45 + 27?", 72, listOf(70, 71, 72, 73))
        addQuestionWithOptions("Addition", "What is 56 + 34?", 90, listOf(88, 89, 90, 91))
        addQuestionWithOptions("Addition", "What is 73 + 29?", 102, listOf(100, 101, 102, 103))
        addQuestionWithOptions("Addition", "What is 88 + 45?", 93, listOf(93, 94, 95, 96))
        addQuestionWithOptions("Addition", "What is 45 + 50?", 95, listOf(95, 96, 97, 98))
        addQuestionWithOptions("Addition", "What is 32 + 70?", 97, listOf(96, 97, 98, 99))
        addQuestionWithOptions("Addition", "What is 55 + 45?", 100, listOf(99, 100, 101, 102))
        // Add more addition questions...

        // For subtraction questions
        addQuestionWithOptions("Subtraction", "What is 10 - 4?", 6, listOf(8, 5, 7, 6))
        addQuestionWithOptions("Subtraction", "What is 15 - 9?", 6, listOf(8, 6, 4, 10))
        addQuestionWithOptions("Subtraction", "What is 15 - 8?", 7, listOf(6, 7, 8, 9))
        addQuestionWithOptions("Subtraction", "What is 27 - 14?", 13, listOf(12, 13, 14, 15))
        addQuestionWithOptions("Subtraction", "What is 39 - 21?", 18, listOf(16, 17, 18, 19))
        addQuestionWithOptions("Subtraction", "What is 52 - 37?", 15, listOf(14, 15, 16, 17))
        addQuestionWithOptions("Subtraction", "What is 68 - 49?", 19, listOf(18, 19, 20, 21))
        addQuestionWithOptions("Subtraction", "What is 85 - 62?", 23, listOf(21, 22, 23, 24))
        addQuestionWithOptions("Subtraction", "What is 97 - 78?", 19, listOf(18, 19, 20, 21))
        addQuestionWithOptions("Subtraction", "What is 109 - 92?", 17, listOf(15, 16, 17, 18))
        // Add more subtraction questions...

        // For multiplication questions
        addQuestionWithOptions("Multiplication", "What is 4 * 5?", 20, listOf(20, 18, 22, 15))
        addQuestionWithOptions("Multiplication", "What is 2 * 9?", 18, listOf(15, 18, 12, 21))
        addQuestionWithOptions("Multiplication", "What is 3 * 4?", 12, listOf(10, 12, 14, 16))
        addQuestionWithOptions("Multiplication", "What is 5 * 6?", 30, listOf(25, 30, 35, 40))
        addQuestionWithOptions("Multiplication", "What is 2 * 8?", 16, listOf(12, 14, 16, 18))
        addQuestionWithOptions("Multiplication", "What is 7 * 3?", 21, listOf(18, 21, 24, 27))
        addQuestionWithOptions("Multiplication", "What is 4 * 7?", 28, listOf(24, 28, 32, 36))
        addQuestionWithOptions("Multiplication", "What is 6 * 5?", 30, listOf(25, 30, 35, 40))
        addQuestionWithOptions("Multiplication", "What is 9 * 2?", 18, listOf(15, 18, 21, 24))
        addQuestionWithOptions("Multiplication", "What is 8 * 4?", 32, listOf(28, 32, 36, 40))
        addQuestionWithOptions("Multiplication", "What is 3 * 9?", 27, listOf(21, 24, 27, 30))
        addQuestionWithOptions("Multiplication", "What is 5 * 4?", 20, listOf(15, 20, 25, 30))
        addQuestionWithOptions("Multiplication", "What is 6 * 3?", 18, listOf(12, 15, 18, 21))
        addQuestionWithOptions("Multiplication", "What is 7 * 5?", 35, listOf(30, 35, 40, 45))
        addQuestionWithOptions("Multiplication", "What is 8 * 2?", 16, listOf(12, 14, 16, 18))
        addQuestionWithOptions("Multiplication", "What is 4 * 9?", 36, listOf(30, 36, 42, 48))
        addQuestionWithOptions("Multiplication", "What is 9 * 3?", 27, listOf(24, 27, 30, 33))
        addQuestionWithOptions("Multiplication", "What is 6 * 4?", 24, listOf(20, 24, 28, 32))
        addQuestionWithOptions("Multiplication", "What is 7 * 2?", 14, listOf(10, 12, 14, 16))
        addQuestionWithOptions("Multiplication", "What is 5 * 8?", 40, listOf(35, 40, 45, 50))
        addQuestionWithOptions("Multiplication", "What is 8 * 5?", 40, listOf(35, 40, 45, 50))



        addQuestionWithOptions("Division", "What is 12 / 3?", 4, listOf(4, 5, 3, 2))
        addQuestionWithOptions("Division", "What is 36 / 6?", 6, listOf(4, 7, 6, 5))
        addQuestionWithOptions("Division", "What is 8 / 2?", 4, listOf(4, 3, 2, 5))
        addQuestionWithOptions("Division", "What is 10 / 5?", 2, listOf(2, 3, 4, 6))
        addQuestionWithOptions("Division", "What is 18 / 3?", 6, listOf(6, 5, 4, 3))
        addQuestionWithOptions("Division", "What is 20 / 4?", 5, listOf(5, 6, 4, 3))
        addQuestionWithOptions("Division", "What is 15 / 3?", 5, listOf(4, 5, 6, 7))
        addQuestionWithOptions("Division", "What is 9 / 3?", 3, listOf(2, 3, 4, 5))
        addQuestionWithOptions("Division", "What is 16 / 4?", 4, listOf(4, 3, 5, 6))
        addQuestionWithOptions("Division", "What is 24 / 6?", 4, listOf(4, 5, 6, 7))
        addQuestionWithOptions("Division", "What is 14 / 2?", 7, listOf(7, 6, 5, 8))
        addQuestionWithOptions("Division", "What is 27 / 3?", 9, listOf(8, 9, 10, 7))
        addQuestionWithOptions("Division", "What is 30 / 5?", 6, listOf(6, 7, 5, 4))
        addQuestionWithOptions("Division", "What is 21 / 3?", 7, listOf(6, 7, 8, 9))
        addQuestionWithOptions("Division", "What is 25 / 5?", 5, listOf(4, 5, 6, 7))
        addQuestionWithOptions("Division", "What is 32 / 4?", 8, listOf(7, 8, 9, 10))
        addQuestionWithOptions("Division", "What is 28 / 4?", 7, listOf(6, 7, 8, 9))
        addQuestionWithOptions("Division", "What is 35 / 5?", 7, listOf(6, 7, 8, 9))
        addQuestionWithOptions("Division", "What is 42 / 6?", 7, listOf(6, 7, 8, 9))
        addQuestionWithOptions("Division", "What is 49 / 7?", 7, listOf(6, 7, 8, 9))
        addQuestionWithOptions("Division", "What is 50 / 10?", 5, listOf(4, 5, 6, 7))



        db.close()

    }

    fun getQuestionsByDifficulty(difficulty: String): List<MathQuestions> {
        val questionList = mutableListOf<MathQuestions>()
        val query = when (difficulty) {
            "Addition" -> "SELECT * FROM MathQuestions WHERE category = 'Addition'"
            "Subtraction" -> "SELECT * FROM MathQuestions WHERE category = 'Subtraction'"
            "Multiplication" -> "SELECT * FROM MathQuestions WHERE category = 'Multiplication'"
            "Division" -> "SELECT * FROM MathQuestions WHERE category = 'Division'"
            else -> "SELECT * FROM MathQuestions"
        }
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


    fun addQuestionWithOptions(
        category: String,
        question: String,
        answer: Int,
        options: List<Int>
    ): Boolean {
        val db = this.writableDatabase
        val shuffledOptions = options.shuffled() // Shuffle the options list
        val cv = ContentValues().apply {
            put("category", category)
            put("question", question)
            put("correctAnswer", answer)
            put("option1", shuffledOptions[0]) // Use shuffled options
            put("option2", shuffledOptions[1])
            put("option3", shuffledOptions[2])
            put("option4", shuffledOptions[3])
        }
        val result = db.insert("MathQuestions", null, cv)
        db.close()
        return result != -1L
    }


}
