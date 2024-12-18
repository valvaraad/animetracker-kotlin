package com.valvaraad.animetracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddAnimeActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_add_anime)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/


        val titleInput: EditText = findViewById(R.id.title_input)
        val scoreInput: EditText = findViewById(R.id.score_input)
        val statusSpinner: Spinner = findViewById(R.id.status_spinner)
        val progressInput: EditText = findViewById(R.id.progress_input)
        val totalInput: EditText = findViewById(R.id.total_input)
        val commentInput: EditText = findViewById(R.id.comment_input)
        val saveButton: Button = findViewById(R.id.save_button)


        ArrayAdapter.createFromResource(
            this,
            R.array.anime_status_options,
            R.layout.spinner_item_white
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item_white)
            statusSpinner.adapter = adapter
        }

        if (intent.getIntExtra("animeId", -1) != -1) {
            val pageTitle: TextView = findViewById(R.id.page_title)
            pageTitle.setText("Редактировать аниме")

            saveButton.setText("Сохранить")


            val title = intent.getStringExtra("title")
            val status = intent.getStringExtra("status")
            val score = intent.getDoubleExtra("score", 0.0)
            val id = intent.getIntExtra("animeId", 0)
            val progress = intent.getIntExtra("progress", 0)
            val total = intent.getIntExtra("total", 0)
            val comment = intent.getStringExtra("comment")

            titleInput.setText(title)
            scoreInput.setText(score.toString())
            progressInput.setText(progress.toString())
            totalInput.setText(total.toString())
            commentInput.setText(comment)

            // Set the spinner value
            val adapter = statusSpinner.adapter
            for (i in 0 until adapter.count) {
                if (adapter.getItem(i).toString() == status) {
                    statusSpinner.setSelection(i)
                    break
                }
            }
        }

        saveButton.setOnClickListener {
            val editAnimeId = intent.getIntExtra("animeId", -1)

            val title = titleInput.text.toString()
            val score = scoreInput.text.toString().toDoubleOrNull() ?: 0.0
            val status = statusSpinner.selectedItem.toString()
            val progress = progressInput.text.toString().toIntOrNull() ?: 0
            val total = totalInput.text.toString().toIntOrNull() ?: 0
            val comment = commentInput.text.toString()

            val anime = Anime(editAnimeId, title, score, status, progress, total, comment)
            val db = DbHelper(this, null)

            if (editAnimeId == -1) {
                val animeId = db.addAnime(anime)
                val currentUserId = UserManager.getCurrentUser(this)

                if (currentUserId != null) {
                    db.addAnimeToUser(currentUserId, animeId)
                }

            } else {
                db.editAnime(anime)
            }

            val resultIntent = Intent()
            resultIntent.putExtra("new_anime", anime)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}