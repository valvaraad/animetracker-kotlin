package com.valvaraad.animetracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
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
        val saveButton: Button = findViewById(R.id.save_button)

        ArrayAdapter.createFromResource(
            this,
            R.array.anime_status_options,
            R.layout.spinner_item_white
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item_white)
            statusSpinner.adapter = adapter
        }

        saveButton.setOnClickListener {
            val title = titleInput.text.toString()
            val score = scoreInput.text.toString().toDoubleOrNull() ?: 0.0
            val status = statusSpinner.selectedItem.toString()
            val progress = progressInput.text.toString().toIntOrNull() ?: 0
            val total = totalInput.text.toString().toIntOrNull() ?: 0

            val anime = Anime(0, title, score, status, progress, total)

            val resultIntent = Intent()
            resultIntent.putExtra("new_anime", anime)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}