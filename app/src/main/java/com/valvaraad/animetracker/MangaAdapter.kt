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

class MangaAdapter(var mangas: MutableList<Manga>, var context: Context, val onEditClick: (Manga) -> Unit) :
    RecyclerView.Adapter<MangaAdapter.MangaViewHolder>() {

    class MangaViewHolder(view: View, private val onDoubleTap: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.mangaTitle)
        val score: TextView = view.findViewById(R.id.mangaScore)
        val status: TextView = view.findViewById(R.id.mangaStatus)
        val progress: TextView = view.findViewById(R.id.mangaProgress)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.manga_in_list, parent, false)
        return MangaViewHolder(view) { position ->
            onEditClick(mangas[position])
        }
    }

    override fun getItemCount(): Int {
        return mangas.count()
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        holder.title.text = mangas[position].title

        val scoreStr = "Score: ${mangas[position].score}"
        holder.score.text = scoreStr

        holder.status.text = mangas[position].status

        val progressStr = "Progress: ${mangas[position].progress}/${mangas[position].total}"
        holder.progress.text = progressStr

        holder.button.setOnClickListener {
            onEditClick(mangas[position])
        }
    }

    // Method to update the dataset
    fun updateDataset(newMangas: List<Manga>) {
        mangas.clear() // Clear the old list
        mangas.addAll(newMangas) // Add all items from the new list
        notifyDataSetChanged() // Notify RecyclerView that the dataset has changed
    }
}