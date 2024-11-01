package com.valvaraad.animetracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DbHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "animehub_db", factory, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users (id INT PRIMARY KEY, login TEXT, email TEXT, pass TEXT)"
        db!!.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun addUser(user: User) : Boolean{

        val read_db = this.readableDatabase
        val cursor = read_db.rawQuery(
            "SELECT * FROM users WHERE email = ? OR login = ?",
            arrayOf(user.email, user.login)
        )

        // Check if any record already exists with the same email or login
        val userExists = cursor.count > 0
        cursor.close()
        read_db.close()

        if (userExists) {
            // User already exists, so do not proceed with insertion
            return false
        }


        val values = ContentValues()
        values.put("login", user.login)
        values.put("email", user.email)
        values.put("pass", user.pass)

        val write_db = this.writableDatabase
        write_db.insert("users", null, values)

        write_db.close()

        return true
    }

    fun getUser(login: String, pass: String) : Boolean {
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login' AND pass = '$pass'", null)
        return result.moveToFirst()
    }

}