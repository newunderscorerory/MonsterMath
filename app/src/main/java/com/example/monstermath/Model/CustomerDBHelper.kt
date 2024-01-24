package com.example.monstermath.Model
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CustomerDBHelper(context: Context) : SQLiteOpenHelper(context, "Customer", null, 2) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Customer(username TEXT primary key, password TEXT, email TEXT, fullname TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("drop table if exists Customer")
        onCreate(db);
    }

    fun insertData(
        username: String,
        password: String,
        email: String,
        fullname: String
    ): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("username", username)
        cv.put("password", password)
        cv.put("email", email)
        cv.put("fullname", fullname)
        val result = db.insert("Customer", null, cv)
        db.close()
        return result != -1L
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

            while (it.moveToNext()) {
                val username = it.getString(usernameIndex)
                val password = it.getString(passwordIndex)
                val email = it.getString(emailIndex)
                val fullname = it.getString(fullnameIndex)
                customerList.add(Customer(username, password, email, fullname))
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