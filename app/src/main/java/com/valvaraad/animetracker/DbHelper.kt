package com.valvaraad.animetracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DbHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "animehub_db", factory, 2) {

    override fun onCreate(db: SQLiteDatabase?) {
        val userQuery = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, login TEXT, email TEXT, pass TEXT)"
        val animeQuery = "CREATE TABLE anime (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL," +
                " score REAL NOT NULL, status TEXT NOT NULL, progress INTEGER NOT NULL, total INTEGER NOT NULL, comment TEXT)"
        val mangaQuery = "CREATE TABLE manga (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL," +
                " score REAL NOT NULL, status TEXT NOT NULL, progress INTEGER NOT NULL, total INTEGER NOT NULL, comment TEXT)"
        val userAnimeQuery = "CREATE TABLE user_anime (user_id INTEGER NOT NULL, anime_id INTEGER NOT NULL, FOREIGN KEY(user_id) REFERENCES users(id)" +
                " ON DELETE CASCADE, FOREIGN KEY(anime_id) REFERENCES anime(id) ON DELETE CASCADE, PRIMARY KEY(user_id, anime_id))"
        val userMangaQuery = "CREATE TABLE user_manga (user_id INTEGER NOT NULL, manga_id INTEGER NOT NULL, FOREIGN KEY(user_id) REFERENCES users(id)" +
                " ON DELETE CASCADE, FOREIGN KEY(manga_id) REFERENCES manga(id) ON DELETE CASCADE, PRIMARY KEY(user_id, manga_id))"
        db!!.execSQL(userQuery)
        db!!.execSQL(animeQuery)
        db!!.execSQL(mangaQuery)
        db!!.execSQL(userAnimeQuery)
        db!!.execSQL(userMangaQuery)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        db!!.execSQL("DROP TABLE IF EXISTS anime")
        db!!.execSQL("DROP TABLE IF EXISTS manga")
        db!!.execSQL("DROP TABLE IF EXISTS user_anime")
        db!!.execSQL("DROP TABLE IF EXISTS user_manga")
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

    fun addAnime(anime: Anime): Int{
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", anime.title)
            put("score", anime.score)
            put("status", anime.status)
            put("progress", anime.progress)
            put("total", anime.total)
            put("comment", anime.comment)
        }
        return db.insert("anime", null, values).toInt()
    }

    fun addManga(manga: Manga): Int{
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", manga.title)
            put("score", manga.score)
            put("status", manga.status)
            put("progress", manga.progress)
            put("total", manga.total)
            put("comment", manga.comment)
        }
        return db.insert("manga", null, values).toInt()
    }

    fun removeAnime(animeId: Int) {
        val db = writableDatabase
        db.execSQL("DELETE FROM anime WHERE id = $animeId")
        db.execSQL("DELETE FROM user_anime WHERE anime_id = $animeId")
    }

    fun removeManga(mangaId: Int) {
        val db = writableDatabase
        db.execSQL("DELETE FROM manga WHERE id = $mangaId")
        db.execSQL("DELETE FROM user_manga WHERE manga_id = $mangaId")
    }

    fun editAnime(anime: Anime) {
        val db = writableDatabase
        db.execSQL("UPDATE anime SET status = '${anime.status}', score = ${anime.score}, title = '${anime.title}', progress = ${anime.progress}, total = ${anime.total}, comment = '${anime.comment}' WHERE id = ${anime.id}")
    }

    fun editManga(manga: Manga) {
        val db = writableDatabase
        db.execSQL("UPDATE manga SET status = '${manga.status}', score = ${manga.score}, title = '${manga.title}', progress = ${manga.progress}, total = ${manga.total}, comment = '${manga.comment}' WHERE id = ${manga.id}")
    }

    fun addAnimeToUser(userId: Int, animeId: Int): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("user_id", userId)
            put("anime_id", animeId)
        }
        return db.insert("user_anime", null, values)
    }

    fun addMangaToUser(userId: Int, mangaId: Int): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("user_id", userId)
            put("manga_id", mangaId)
        }
        return db.insert("user_manga", null, values)
    }

    fun getAllUsersAnime(userId: Int) : MutableList<Anime> {
        val db = readableDatabase
        val list = mutableListOf<Anime>()
        val query = "SELECT * FROM user_anime JOIN anime ON anime_id = id WHERE user_id = $userId "
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val anime = Anime(cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("score")),
                    cursor.getString(cursor.getColumnIndexOrThrow("status")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("progress")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("total")),
                    cursor.getString(cursor.getColumnIndexOrThrow("comment")))
                list.add(anime)
            } while (cursor.moveToNext())
        }
        cursor.close()

        return list
    }

    fun getAllUsersManga(userId: Int) : MutableList<Manga> {
        val db = readableDatabase
        val list = mutableListOf<Manga>()
        val query = "SELECT * FROM user_manga JOIN manga ON manga_id = id WHERE user_id = $userId"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val manga = Manga(cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("score")),
                    cursor.getString(cursor.getColumnIndexOrThrow("status")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("progress")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("total")),
                    cursor.getString(cursor.getColumnIndexOrThrow("comment")))      //cursor.getLong(cursor.getColumnIndexOrThrow("manga_id"))
                list.add(manga)

            } while (cursor.moveToNext())
        }
        cursor.close()

        return list
    }

    fun getUser(login: String, pass: String) : Boolean {
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login' AND pass = '$pass'", null)
        return result.moveToFirst()
    }

    fun getAllUsers() : ArrayList<User> {
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM users", null)
        val users = ArrayList<User>()
        if (result.moveToFirst()) {
            for (i in 1..result.count) {
                users.add(User(result.getString(1), result.getString(2)))
            }
        }
        result.close()
        return users
    }

    fun getUser(id: Int): User {
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT * FROM users WHERE id = '$id'", null)
        if (result.moveToFirst())
            return User(
                result.getString(result.getColumnIndexOrThrow("login")),
                result.getString(result.getColumnIndexOrThrow("email"))
            )
        else
            return User()
    }

    fun getUserId(login: String): Int {
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT id FROM users WHERE login = '$login'", null)
        if (result.moveToFirst())
            return result.getInt(result.getColumnIndexOrThrow("id"))
        else
            return -1
    }

}