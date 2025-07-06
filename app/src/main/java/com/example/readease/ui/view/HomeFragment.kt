package com.example.readease.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readease.R
import com.example.readease.data.db.AppDatabase
import com.example.readease.data.model.Books
import com.example.readease.databinding.FragmentHomeBinding
import com.example.readease.repository.BooksRepository
import com.example.readease.ui.adapter.BookAdapter
import com.example.readease.ui.viewmodel.BooksViewModel
import com.example.readease.ui.viewmodel.BooksViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BooksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Setup ViewModel manually using Factory
        val dao = AppDatabase.getDatabase(requireContext()).booksDao()
        val repository = BooksRepository(dao)
        val factory = BooksViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[BooksViewModel::class.java]

        setupRecyclerViews()
        observeBooks()

        // Only fetch from API if DB is empty
        viewModel.fetchBooksIfEmpty()
    }

    private fun observeBooks() {
        viewModel.books.observe(viewLifecycleOwner) { books ->
            bindCategory(binding.recyclerRecommended, books.filter { it.subject.equals("Fiction", true) })
            bindCategory(binding.recyclerscience, books.filter { it.subject.equals("Science", true) })
            bindCategory(binding.recyclerhistory, books.filter { it.subject.equals("History", true) })
            bindCategory(binding.recyclerbiography, books.filter { it.subject.equals("Biography", true) })
            bindCategory(binding.recyclerart, books.filter { it.subject.equals("Art", true) })
            bindCategory(binding.recyclerphilosophy, books.filter { it.subject.equals("Philosophy", true) })
            bindCategory(binding.recyclerselfhelp, books.filter { it.subject.equals("Self-Help", true) })
        }

        viewModel.loadBooks() // Trigger initial load
    }

    private fun setupRecyclerViews() {
        listOf(
            binding.recyclerRecommended,
            binding.recyclerscience,
            binding.recyclerhistory,
            binding.recyclerbiography,
            binding.recyclerart,
            binding.recyclerphilosophy,
            binding.recyclerselfhelp
        ).forEach {
            it.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun bindCategory(recyclerView: RecyclerView, books: List<Books>) {
        recyclerView.adapter = BookAdapter(books) { selectedBook ->
            val bundle = Bundle().apply {
                putParcelable("book", selectedBook)
            }

            // Navigate to ExploreFragment
            findNavController().navigate(R.id.exploreFragment, bundle)

           // Update bottom nav selection manually
            (activity as? MainActivity)?.setSelectedBottomNavItem(R.id.exploreFragment)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
