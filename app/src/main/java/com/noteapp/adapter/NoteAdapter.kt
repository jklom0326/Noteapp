package com.noteapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.noteapp.databinding.NoteLayoutAdapterBinding
import com.noteapp.fragment.HomeFragmentDirections
import com.noteapp.model.Note

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var binding: NoteLayoutAdapterBinding? = null

    class NoteViewHolder(itemBinding: NoteLayoutAdapterBinding):
            RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback =
        object : DiffUtil.ItemCallback<Note>(){
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

        }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        binding = NoteLayoutAdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return  NoteViewHolder(binding!!)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.itemView.apply {

            binding?.tvNoteTitle?.text = currentNote.noteTitle
            binding?.tvNoteBody?.text = currentNote.noteBody

        }.setOnClickListener { mView ->
            val direction = HomeFragmentDirections
                .actionHomeFragmentToUpdateNoteFragment()

            mView.findNavController().navigate(
                direction
            )
        }
    }
}