package com.example.readease.ui.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readease.data.db.AppDatabase
import com.example.readease.databinding.FragmentCollectionBinding
import com.example.readease.repository.BooksRepository
import com.example.readease.ui.adapter.BookCollectionAdapter
import com.example.readease.ui.viewmodel.BooksViewModel
import com.example.readease.ui.viewmodel.BooksViewModelFactory

class CollectionFragment : Fragment() {

    private var _binding: FragmentCollectionBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BooksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dao = AppDatabase.getDatabase(requireContext()).booksDao()
        val repository = BooksRepository(dao)
        val factory = BooksViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[BooksViewModel::class.java]

        binding.recyclerViewCollections.layoutManager = LinearLayoutManager(requireContext())

        viewModel.books.observe(viewLifecycleOwner) { books ->
            val collectedBooks = books.filter { it.collected }
            binding.recyclerViewCollections.adapter = BookCollectionAdapter(collectedBooks)
        }

        viewModel.loadBooks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
