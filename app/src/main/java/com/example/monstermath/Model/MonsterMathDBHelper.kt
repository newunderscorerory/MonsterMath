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
            "CREATE TABLE Class (" +
                    "class_id INTEGER PRIMARY KEY," +
                    "class_name TEXT)"
        )
        db?.execSQL(
            "CREATE TABLE UserClass (" +
                    "username TEXT," +
                    "class_id INTEGER," +
                    "FOREIGN KEY (username) REFERENCES Customer(username)," +
                    "FOREIGN KEY (class_id) REFERENCES Class(class_id)," +
                    "PRIMARY KEY (username, class_id))"
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

    fun getHighScore(username: String): Int {
        val db = this.readableDatabase
        var highScore = 0
        val cursor = db.rawQuery("SELECT highScore FROM Customer WHERE username=?", arrayOf(username))

        if (cursor != null && cursor.moveToFirst()) {
            val highScoreIndex = cursor.getColumnIndex("highScore")
            if (highScoreIndex != -1) {
                highScore = cursor.getInt(highScoreIndex)
            } else {
                throw IllegalStateException("Column 'highScore' not found in cursor")
            }
        }

        cursor?.close()
        db.close()
        return highScore
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
        val query = "SELECT * FROM Customer WHERE username= '$username' AND password= '$password'"
        val cursor = db.rawQuery(query, null)
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

    fun addClass(className: String): Long {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("class_name", className)
        val result = db.insert("Class", null, cv)
        db.close()
        return result
    }

    fun addUserToClass(username: String, classId: Long): Long {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("username", username)
        cv.put("class_id", classId)
        val result = db.insert("UserClass", null, cv)
        db.close()
        return result
    }

    fun getUsersInClass(classId: Long): List<Customer> {
        val customerList = mutableListOf<Customer>()
        val query =
            "SELECT c.* FROM Customer c INNER JOIN UserClass uc ON c.username = uc.username WHERE uc.class_id = ?"
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery(query, arrayOf(classId.toString()))

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

        addQuestionWithOptions("Addition and Subtraction", "What is 5 + 3?", 8, listOf(7, 9, 8, 6))
        addQuestionWithOptions("Addition and Subtraction", "What is 5 + 3?", 8, listOf(7, 9, 8, 6))
        addQuestionWithOptions("Addition and Subtraction", "What is 10 - 4?", 6, listOf(8, 5, 7, 6))
        addQuestionWithOptions("Addition and Subtraction", "What is 10 + 2?", 12, listOf(12, 9, 7, 4))
        addQuestionWithOptions("Addition and Subtraction", "What is 7 + 6?", 13, listOf(15, 13, 10, 12))
        addQuestionWithOptions("Addition and Subtraction", "What is 15 - 9?", 6, listOf(8, 6, 4, 10))
        addQuestionWithOptions("Addition and Subtraction", "What is 20 + 3?", 23, listOf(23, 19, 25, 21))
        addQuestionWithOptions("Addition and Subtraction", "What is 18 - 7?", 11, listOf(11, 10, 13, 8))
        addQuestionWithOptions("Addition and Subtraction", "What is 25 + 9?", 34, listOf(30, 34, 32, 28))
        addQuestionWithOptions("Addition and Subtraction", "What is 17 + 9?", 26, listOf(23, 28, 26, 25))
        addQuestionWithOptions("Addition and Subtraction", "What is 42 - 18?", 24, listOf(24, 20, 26, 22))
        addQuestionWithOptions("Addition and Subtraction", "What is 55 + 27?", 82, listOf(82, 80, 85, 75))
        addQuestionWithOptions("Addition and Subtraction", "What is 63 - 29?", 34, listOf(32, 36, 34, 30))
        addQuestionWithOptions("Addition and Subtraction", "What is 98 + 16?", 114, listOf(110, 120, 105, 114))
        addQuestionWithOptions("Addition and Subtraction", "What is 116 - 42?", 74, listOf(74, 78, 72, 68))
        addQuestionWithOptions("Addition and Subtraction", "What is 135 + 28?", 163, listOf(155, 163, 160, 168))
        addQuestionWithOptions("Addition and Subtraction", "What is 142 - 77?", 65, listOf(68, 65, 62, 58))
        addQuestionWithOptions("Addition and Subtraction", "What is 178 + 35?", 213, listOf(213, 205, 220, 200))
        addQuestionWithOptions("Addition and Subtraction", "What is 197 - 92?", 105, listOf(108, 105, 102, 98))

        addQuestionWithOptions("Multiplication and Division", "What is 4 * 5?", 20, listOf(20, 18, 22, 15))
        addQuestionWithOptions("Multiplication and Division", "What is 12 / 3?", 4, listOf(4, 5, 3, 2))
        addQuestionWithOptions("Multiplication and Division", "What is 2 * 9?", 18, listOf(15, 18, 12, 21))
        addQuestionWithOptions("Multiplication and Division", "What is 3 * 7?", 21, listOf(21, 24, 18, 15))
        addQuestionWithOptions("Multiplication and Division", "What is 4 * 8?", 32, listOf(32, 28, 36, 24))
        addQuestionWithOptions("Multiplication and Division", "What is 6 * 6?", 36, listOf(39, 36, 33, 30))
        addQuestionWithOptions("Multiplication and Division", "What is 7 * 5?", 35, listOf(38, 35, 32, 28))
        addQuestionWithOptions("Multiplication and Division", "What is 8 * 4?", 32, listOf(24, 36, 32, 28))
        addQuestionWithOptions("Multiplication and Division", "What is 9 * 3?", 27, listOf(24, 27, 30, 21))
        addQuestionWithOptions("Multiplication and Division", "What is 10 * 2?", 20, listOf(17, 20, 14, 23))
        addQuestionWithOptions("Multiplication and Division", "What is 11 * 3?", 33, listOf(30, 36, 33, 27))
        addQuestionWithOptions("Multiplication and Division", "What is 12 * 12?", 144, listOf(156, 144, 120, 132))

        addQuestionWithOptions("All", "What is 5 + 3?", 8, listOf(9, 8, 7, 6))
        addQuestionWithOptions("All", "What is 10 - 4?", 6, listOf(5, 7, 8, 6))
        addQuestionWithOptions("All", "What is 10 + 2?", 12, listOf(12, 4, 9, 7))
        addQuestionWithOptions("All", "What is 7 + 6?", 13, listOf(10, 12, 15, 13))
        addQuestionWithOptions("All", "What is 15 - 9?", 6, listOf(6, 4, 10, 8))
        addQuestionWithOptions("All", "What is 20 + 3?", 23, listOf(25, 21, 19, 23))
        addQuestionWithOptions("All", "What is 18 - 7?", 11, listOf(10, 11, 8, 13))
        addQuestionWithOptions("All", "What is 25 + 9?", 34, listOf(28, 32, 34, 30))
        addQuestionWithOptions("All", "What is 30 * 4?", 120, listOf(120, 100, 110, 130))
        addQuestionWithOptions("All", "What is 36 / 6?", 6, listOf(4, 7, 6, 5))
        addQuestionWithOptions("All", "What is 9 + 4?", 13, listOf(12, 15, 13, 10))
        addQuestionWithOptions("All", "What is 14 - 7?", 7, listOf(7, 9, 5, 11))
        addQuestionWithOptions("All", "What is 20 + 3?", 23, listOf(21, 25, 19, 23))
        addQuestionWithOptions("All", "What is 18 - 7?", 11, listOf(10, 8, 13, 11))
        addQuestionWithOptions("All", "What is 25 + 9?", 34, listOf(30, 34, 28, 32))

        db.close()

    }

    fun getQuestionsByDifficulty(difficulty: String): List<MathQuestions> {
        val questionList = mutableListOf<MathQuestions>()
        val query = when (difficulty) {
            "Easy" -> "SELECT * FROM MathQuestions WHERE category = 'Addition and Subtraction'"
            "Medium" -> "SELECT * FROM MathQuestions WHERE category = 'Multiplication and Division'"
            "Hard" -> "SELECT * FROM MathQuestions WHERE category = 'All'"
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
