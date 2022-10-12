package com.voltek.cleanarchitectureproject.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.voltek.cleanarchitectureproject.databinding.ItemNoteBinding
import com.voltek.core.data.Note
import java.text.SimpleDateFormat
import java.util.*

class NotesListAdapter(var notesList: ArrayList<Note>, val actions: ListAction) :
    RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notesList[position])
    }

    override fun getItemCount(): Int = notesList.size

    fun updateNotes(newNotesList: List<Note>) {
        notesList.clear()
        notesList.addAll(newNotesList)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(private val itemNoteBinding: ItemNoteBinding) :
        RecyclerView.ViewHolder(itemNoteBinding.root) {

        fun bind(note: Note) {
            with(itemNoteBinding) {
                itemTitle.text = note.title
                itemContent.text = note.content
                itemDate.text = "Last updated: ${formatTime(note.updateTime)}"
                tvWordCounter.text = "${note.wordCount} words"

                root.setOnClickListener {
                    actions.onClick(note.id)
                }
            }
        }

        private fun formatTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("MMM dd, HH:mm:ss", Locale.getDefault())
            return format.format(date)
        }
    }
}