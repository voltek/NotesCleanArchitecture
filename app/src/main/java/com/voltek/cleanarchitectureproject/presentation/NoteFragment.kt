package com.voltek.cleanarchitectureproject.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.voltek.cleanarchitectureproject.databinding.FragmentNoteBinding
import com.voltek.cleanarchitectureproject.framework.viewmodels.NoteViewModel
import com.voltek.core.data.Note
import com.voltek.core.utils.Constants.EMPTY_STRING

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding: FragmentNoteBinding get() = _binding!!

    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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
    }

    private fun hideKeyboard() {
        with(context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager) {
            hideSoftInputFromWindow(binding.root.windowToken, 0)
        }
    }
}