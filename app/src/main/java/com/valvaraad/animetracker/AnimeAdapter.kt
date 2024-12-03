package com.valvaraad.animetracker

import android.content.Context
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class AnimeAdapter(var animes: MutableList<Anime>, var context: Context, val onEditClick: (Anime) -> Unit) :
    RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    class AnimeViewHolder(view: View, private val onDoubleTap: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.animeTitle)
        val score: TextView = view.findViewById(R.id.animeScore)
        val status: TextView = view.findViewById(R.id.animeStatus)
        val progress: TextView = view.findViewById(R.id.animeProgress)
        val button: Button = view.findViewById(R.id.edit_button)

        init {
            val gestureDetector = GestureDetector(view.context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(event: MotionEvent): Boolean {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                            onDoubleTap(position)
                    }
                    return true
                }
            })

            view.setOnTouchListener { _, event ->
                gestureDetector.onTouchEvent(event)
                if (event.action == MotionEvent.ACTION_UP) {
                    view.performClick() // Ensure accessibility compliance
                }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.anime_in_list, parent, false)
        return AnimeViewHolder(view) {position ->
            onEditClick(animes[position])
        }
    }

    override fun getItemCount(): Int {
        return animes.count()
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.title.text = animes[position].title

        val scoreStr = "Score: ${animes[position].score}"
        holder.score.text = scoreStr

        holder.status.text = animes[position].status

        val progressStr = "Progress: ${animes[position].progress}/${animes[position].total}"
        holder.progress.text = progressStr

        holder.button.setOnClickListener {
            onEditClick(animes[position])
        }
    }

    // Method to update the dataset
    fun updateDataset(newAnimes: List<Anime>) {
        animes.clear() // Clear the old list
        animes.addAll(newAnimes) // Add all items from the new list
        notifyDataSetChanged() // Notify RecyclerView that the dataset has changed
    }
}