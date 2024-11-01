package com.valvaraad.animetracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class AnimeAdapter(var animes: List<Anime>, var context: Context,) : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    class AnimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.animeTitle)
        val score: TextView = view.findViewById(R.id.animeScore)
        val status: TextView = view.findViewById(R.id.animeStatus)
        val progress: TextView = view.findViewById(R.id.animeProgress)
        val button: Button = view.findViewById(R.id.edit_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.anime_in_list, parent, false)
        return AnimeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return animes.count()
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {

        holder.title.text = animes[position].title

        val score_str = "Score: " + animes[position].score.toString()
        holder.score.text = score_str

        holder.status.text = animes[position].status

        val progress_str = "Progress: " + animes[position].progress.toString() + '/' + animes[position].total.toString()
        holder.progress.text = progress_str

        holder.button.setOnClickListener {

        }


    }
}