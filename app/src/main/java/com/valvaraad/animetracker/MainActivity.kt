package com.valvaraad.animetracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        val remember = UserManager.getCurrentUser(this)

        if (remember != null) {
            val intent = Intent(this, AnimelistActivity::class.java)
            startActivity(intent)
        }

        val userLogin: EditText = findViewById(R.id.user_login)
        val userEmail: EditText = findViewById(R.id.user_email)
        val userPass: EditText = findViewById(R.id.user_pass)
        val button: Button = findViewById(R.id.button_signup)
        val linkToAuth: TextView = findViewById(R.id.link_to_auth)

        linkToAuth.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if (login == "" || email == "" || pass == "") {
                Toast.makeText(this, "Заполните все поля.", Toast.LENGTH_LONG).show()
            } else {
                val user = User(login, email, pass)

                val db = DbHelper(this, null)
                if (db.addUser(user)) {
                    Toast.makeText(this, "Пользователь $login добавлен", Toast.LENGTH_LONG).show()
                    userLogin.text.clear()
                    userEmail.text.clear()
                    userPass.text.clear()
                } else {
                    Toast.makeText(this, "Логин или email занят", Toast.LENGTH_LONG).show()
                }
            }

        }

    }
}