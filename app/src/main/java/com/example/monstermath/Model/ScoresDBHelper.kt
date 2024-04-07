package com.example.monstermath.Model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class  ScoresDBHelper(context: Context) : SQLiteOpenHelper(context, "Scores", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Scores(username TEXT primary key, highestScore INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists Scores")
        onCreate(db)
    }

    fun insertInitialScore(username: String) {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put("username", username)
            put("highestScore", 0)
        }
        db.insert("Scores", null, cv)
        db.close()
    }

    fun getHighestScore(username: String): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT highestScore FROM Scores WHERE username = ?", arrayOf(username))
        var highestScore = 0
        cursor.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndex("highestScore")
                if (columnIndex != -1) {
                    highestScore = it.getInt(columnIndex)
                } else {
                    return 0
                }
            }
        }
        cursor.close()
        db.close()
        return highestScore
    }

    fun updateHighestScore(username: String, newScore: Int): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("highestScore", newScore)
        }
        val whereClause = "username = ?"
        val whereArgs = arrayOf(username)
        val result = db.update("Scores", values, whereClause, whereArgs)
        db.close()
        return result > 0
    }
}
