package com.valvaraad.animetracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AnimelistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_animelist)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        val animeList: RecyclerView = findViewById(R.id.animeList)
        val addButton: Button = findViewById(R.id.add_button)
        val linkToManga: Button = findViewById(R.id.link_to_manga)

        val animes = arrayListOf<Anime>()

        animes.add(Anime(1, "Naruto", 9.51, "Completed", 220, 220))
        animes.add(Anime(2, "Demon Slayer", 8.23, "Watching", 3, 26))

        if (animes.isNotEmpty()) {
            animeList.layoutManager = LinearLayoutManager(this)
            animeList.adapter = AnimeAdapter(animes, this)
        }

        addButton.setOnClickListener {
            val intent = Intent(this, AddAnimeActivity::class.java)
            startActivity(intent)
        }

        linkToManga.setOnClickListener {
            val intent = Intent(this, MangalistActivity::class.java)
            startActivity(intent)
        }


    }
}