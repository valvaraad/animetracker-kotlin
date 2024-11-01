package com.valvaraad.animetracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class MangaAdapter(var mangas: List<Manga>, var context: Context,) : RecyclerView.Adapter<MangaAdapter.MangaViewHolder>() {

    class MangaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.mangaTitle)
        val score: TextView = view.findViewById(R.id.mangaScore)
        val status: TextView = view.findViewById(R.id.mangaStatus)
        val progress: TextView = view.findViewById(R.id.mangaProgress)
        val button: Button = view.findViewById(R.id.edit_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.manga_in_list, parent, false)
        return MangaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mangas.count()
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {

        holder.title.text = mangas[position].title

        val score_str = "Score: " + mangas[position].score.toString()
        holder.score.text = score_str

        holder.status.text = mangas[position].status

        val progress_str = "Progress: " + mangas[position].progress.toString() + '/' + mangas[position].total.toString()
        holder.progress.text = progress_str

        holder.button.setOnClickListener {

        }


    }
}