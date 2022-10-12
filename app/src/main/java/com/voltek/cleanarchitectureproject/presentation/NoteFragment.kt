package com.voltek.cleanarchitectureproject.presentation

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.voltek.cleanarchitectureproject.R
import com.voltek.cleanarchitectureproject.databinding.FragmentNoteBinding
import com.voltek.cleanarchitectureproject.framework.viewmodels.NoteViewModel
import com.voltek.core.data.Note
import com.voltek.core.utils.Constants.EMPTY_STRING

class NoteFragment : Fragment() {

    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note()
    private var _binding: FragmentNoteBinding? = null
    private var noteId = 0L

    private val binding: FragmentNoteBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = defaultViewModelProviderFactory.create(NoteViewModel::class.java)
        setMenu()

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }

        if (noteId != 0L) {
            viewModel.getNoteById(noteId)
        }

        observeViewModel()

        binding.fabSaveNote.setOnClickListener {
            if (isTitleAndContentEmpty()) {
                Navigation.findNavController(it).popBackStack()
                return@setOnClickListener
            }
            saveNote()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setMenu() {
        val menuHost: MenuHost = requireContext() as MenuHost

        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.note_menu, menu)

                if(noteId == 0L) {
                    menu.findItem(R.id.deleteNote).isVisible = false
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return if (menuItem.itemId == R.id.deleteNote) {
                    if (noteId != 0L) {
                        showDeleteDialog()
                    }
                    true
                } else {
                    false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun isTitleAndContentEmpty(): Boolean {
        return binding.etNoteTitle.text.toString() == EMPTY_STRING &&
                binding.etNoteContent.text.toString() == EMPTY_STRING
    }

    private fun saveNote() {
        val time = System.currentTimeMillis()

        with(currentNote) {
            title = binding.etNoteTitle.text.toString()
            content = binding.etNoteContent.text.toString()
            updateTime = time

            if (currentNote.id == 0L) {
                creationTime = time
            }
            viewModel.saveNote(this)
        }
    }

    private fun deleteNote() {
        viewModel.removeNote(currentNote)
        Navigation.findNavController(requireView()).popBackStack()
    }

    private fun observeViewModel() {
        viewModel.saved.observe(viewLifecycleOwner) {
            if (it) {
                Snackbar.make(binding.root, "Note saved", Snackbar.LENGTH_SHORT).show()
                hideKeyboard()
                Navigation.findNavController(binding.root).popBackStack()
            } else {
                Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.currentNote.observe(viewLifecycleOwner) {
            it?.let {
                currentNote = it
                binding.etNoteTitle.setText(it.title, TextView.BufferType.EDITABLE)
                binding.etNoteContent.setText(it.content, TextView.BufferType.EDITABLE)
            }
        }
    }

    private fun hideKeyboard() {
        with(requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager) {
            hideSoftInputFromWindow(binding.root.windowToken, 0)
        }
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Are you sure you want to delete this note?")
            .setPositiveButton("Yes") { _, _ -> deleteNote() }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}