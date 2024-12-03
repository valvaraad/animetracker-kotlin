package com.valvaraad.animetracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MangalistActivity : AppCompatActivity() {

    private lateinit var mangaAdapter: MangaAdapter
    private lateinit var db: DbHelper
    private var currentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mangalist)

        db = DbHelper(this, null)
        currentUser = UserManager.getCurrentUser(this)?.let { db.getUser(it) }

        val mangaList: RecyclerView = findViewById(R.id.mangaList)
        val addButton: Button = findViewById(R.id.add_button)
        val linkToAnime: Button = findViewById(R.id.link_to_anime)

        val mangas = UserManager.getCurrentUser(this)?.let { db.getAllUsersManga(it) }

        if (mangas != null) {
            mangaAdapter = MangaAdapter(mangas.toMutableList(), this) {
                manga ->
                val intent = Intent(this, AddMangaActivity::class.java)
                intent.putExtra("mangaId", manga.id)
                intent.putExtra("status", manga.status)
                intent.putExtra("score", manga.score)
                intent.putExtra("title", manga.title)
                intent.putExtra("progress", manga.progress)
                intent.putExtra("total", manga.total)
                intent.putExtra("comment", manga.comment)
                startActivity(intent)
            }
        }
        mangaList.layoutManager = LinearLayoutManager(this)
        mangaList.adapter = mangaAdapter

        addButton.setOnClickListener {
            val intent = Intent(this, AddMangaActivity::class.java)
            startActivity(intent)
        }

        linkToAnime.setOnClickListener {
            val intent = Intent(this, AnimelistActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshMangaList()
    }

    private fun refreshMangaList() {
        val updatedMangas = UserManager.getCurrentUser(this)?.let { db.getAllUsersManga(it) }
        if (updatedMangas != null) {
            mangaAdapter.updateDataset(updatedMangas.toList())
        }
    }
}
