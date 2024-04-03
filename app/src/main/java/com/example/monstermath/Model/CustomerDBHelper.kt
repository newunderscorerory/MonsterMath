package com.example.monstermath.Model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CustomerDBHelper(context: Context) : SQLiteOpenHelper(context, "Customer", null, 2) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Customer(username TEXT primary key, password TEXT, email TEXT, fullname TEXT, highScore INTEGER DEFAULT 0)")
        db?.execSQL("create table Class(class_id INTEGER PRIMARY KEY, class_name TEXT)")
        db?.execSQL("create table UserClass(username TEXT, class_id INTEGER, FOREIGN KEY (username) REFERENCES Customer(username), FOREIGN KEY (class_id) REFERENCES Class(class_id), PRIMARY KEY (username, class_id))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("drop table if exists Customer")
        db?.execSQL("drop table if exists Class")
        db?.execSQL("drop table if exists UserClass")
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
        val highScoreIndex = cursor.getColumnIndex("highScore")
        if (highScoreIndex != -1) {
            highScore = cursor.getInt(highScoreIndex)
        } else {
            throw IllegalStateException("Column 'highScore' not found in cursor")
        }

        cursor.close()
        db.close()
        return highScore
    }

    fun updateHighScore(username: String, newHighScore: Int) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("highScore", newHighScore)
        db.update("Customer", cv, "username = ?", arrayOf(username))
        db.close()
    }

    fun checkPass(username: String, password: String): Boolean {
        val db = this.writableDatabase
        val query = "select * from Customer where username= '$username' and password= '$password'"
        val cursor = db.rawQuery(query, null)
        val result = cursor.count > 0
        cursor.close()
        db.close()
        return result
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
        val query = "SELECT c.* FROM Customer c INNER JOIN UserClass uc ON c.username = uc.username WHERE uc.class_id = ?"
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
}
