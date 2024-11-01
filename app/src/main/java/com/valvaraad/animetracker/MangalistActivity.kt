package com.valvaraad.animetracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MangalistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_mangalist)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        val mangaList: RecyclerView = findViewById(R.id.mangaList)
        val addButton: Button = findViewById(R.id.add_button)
        val linkToAnime: Button = findViewById(R.id.link_to_anime)

        val mangas = arrayListOf<Manga>()

        mangas.add(Manga(1, "Naruto", 9.51, "Completed", 700, 700))
        mangas.add(Manga(2, "Demon Slayer", 8.23, "Reading", 20, 207))

        if (mangas.isNotEmpty()) {
            mangaList.layoutManager = LinearLayoutManager(this)
            mangaList.adapter = MangaAdapter(mangas, this)
        }

        addButton.setOnClickListener {
            val intent = Intent(this, AddAnimeActivity::class.java)
            startActivity(intent)
        }

        linkToAnime.setOnClickListener {
            val intent = Intent(this, AnimelistActivity::class.java)
            startActivity(intent)
        }


    }
}