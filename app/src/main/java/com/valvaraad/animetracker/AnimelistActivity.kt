package com.valvaraad.animetracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AnimelistActivity : AppCompatActivity() {

    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var db: DbHelper
    private var currentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animelist)

        db = DbHelper(this, null)
        currentUser = UserManager.getCurrentUser(this)?.let { db.getUser(it) }

        val animeList: RecyclerView = findViewById(R.id.animeList)
        val addButton: Button = findViewById(R.id.add_button)
        val linkToManga: Button = findViewById(R.id.link_to_manga)

        val animes = UserManager.getCurrentUser(this)?.let { db.getAllUsersAnime(it) }

        if (animes != null) {
            animeAdapter = AnimeAdapter(animes.toMutableList(), this) {
                anime ->
                val intent = Intent(this, AddAnimeActivity::class.java)
                intent.putExtra("animeId", anime.id)
                intent.putExtra("status", anime.status)
                intent.putExtra("score", anime.score)
                intent.putExtra("title", anime.title)
                intent.putExtra("progress", anime.progress)
                intent.putExtra("total", anime.total)
                intent.putExtra("comment", anime.comment)
                startActivity(intent)
            }
        }


        animeList.layoutManager = LinearLayoutManager(this)
        animeList.adapter = animeAdapter

        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val anime = animeAdapter.animes[position]
                    animeAdapter.animes.removeAt(position) // Remove the anime from the list
                    animeAdapter.notifyItemRemoved(position)

                    // Optionally, remove from database as well
                    db.removeAnime(anime.id) // Assuming you have a delete method in your DbHelper
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(animeList)

        addButton.setOnClickListener {
            val intent = Intent(this, AddAnimeActivity::class.java)
            startActivity(intent)
        }

        linkToManga.setOnClickListener {
            val intent = Intent(this, MangalistActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshAnimeList()
    }

    private fun refreshAnimeList() {
        val updatedAnimes = UserManager.getCurrentUser(this)?.let { db.getAllUsersAnime(it) }
        if (updatedAnimes != null) {
            animeAdapter.updateDataset(updatedAnimes.toList())
        }
    }
}
