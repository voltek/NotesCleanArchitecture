package com.voltek.cleanarchitectureproject.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.voltek.cleanarchitectureproject.databinding.FragmentListBinding
import com.voltek.cleanarchitectureproject.framework.viewmodels.ListViewModel

class ListFragment : Fragment(), ListAction {

    private var notesListAdapter: NotesListAdapter = NotesListAdapter(arrayListOf(), this)
    private lateinit var listViewModel: ListViewModel

    private var _binding: FragmentListBinding? = null

    private val binding: FragmentListBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewModel = defaultViewModelProviderFactory.create(ListViewModel::class.java)

        with(binding) {
            rvNotesList.layoutManager = LinearLayoutManager(context)
            rvNotesList.adapter = notesListAdapter


            fabAddNote.setOnClickListener {
                goToNoteDetails()
            }
        }

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        listViewModel.getAllNotes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(id: Long) {
        goToNoteDetails(id)
    }

    private fun goToNoteDetails(id: Long = 0L) {
        with(ListFragmentDirections.actionLisNoteToNote(id)) {
            Navigation.findNavController(binding.root).navigate(this)
        }
    }

    private fun observeViewModel() {
        listViewModel.notes.observe(viewLifecycleOwner) { notesList ->
            binding.pbNoteList.visibility = View.GONE
            binding.rvNotesList.visibility = View.VISIBLE
            notesListAdapter.updateNotes(notesList.sortedBy { it.updateTime })
        }
    }
}