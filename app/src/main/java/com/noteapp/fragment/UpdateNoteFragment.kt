package com.noteapp.fragment

import android.app.AlertDialog
import android.graphics.ColorSpace
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.noteapp.MainActivity
import com.noteapp.R
import com.noteapp.databinding.FragmentUpdateNoteBinding
import com.noteapp.model.Note
import com.noteapp.toast
import com.noteapp.viewmodel.NoteViewModel

class UpdateNoteFragment : Fragment() {
    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private val args: UpdateNoteFragmentArgs by navArgs()
    private lateinit var currentNote: Note
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

            _binding = FragmentUpdateNoteBinding.inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel

        currentNote = args.note!!

        binding.etNoteTitleUpdate.setText(currentNote.noteTitle)
        binding.etNoteBodyUpdate.setText(currentNote.noteBody)

        binding.faUpdate.setOnClickListener {

            val title = binding.etNoteTitleUpdate.text.toString().trim()
            val body = binding.etNoteBodyUpdate.text.toString().trim()

            if(title.isNotEmpty()) {
                val note = Note(currentNote.id, title, body)
                noteViewModel.updateNote(note)

                view.findNavController().navigate(
                    R.id.action_updateNoteFragment_to_homeFragment
                )
            } else {
                activity?.toast("Pleas enter title name!")
            }
        }
    }

    private fun deleteNote(){
        AlertDialog.Builder(activity,R.style.DialogTheme).apply {
            setTitle("삭제")
            setMessage("삭제하시겠습니까?")
            setPositiveButton("삭제") {_,_ ->
                noteViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(
                    R.id.action_updateNoteFragment_to_homeFragment
                )
            }
            setNegativeButton("취소", null)

        }.create().show()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.update_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete_menu -> {
                deleteNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}