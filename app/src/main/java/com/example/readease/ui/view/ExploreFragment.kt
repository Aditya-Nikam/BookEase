package com.example.readease.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.readease.R
import com.example.readease.data.db.AppDatabase
import com.example.readease.data.model.Books
import com.example.readease.databinding.FragmentExploreBinding
import com.example.readease.repository.BooksRepository
import com.example.readease.ui.viewmodel.BooksViewModel
import com.example.readease.ui.viewmodel.BooksViewModelFactory

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BooksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Setup ViewModel
        val dao = AppDatabase.getDatabase(requireContext()).booksDao()
        val repository = BooksRepository(dao)
        val factory = BooksViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[BooksViewModel::class.java]

        val bookFromArgs = arguments?.getParcelable<Books>("book")
        if (bookFromArgs != null) {
            bindBook(bookFromArgs)
        } else {
            // Load first book from DB if no book passed
            viewModel.books.observe(viewLifecycleOwner) { books ->
                if (books.isNotEmpty()) {
                    bindBook(books[0])
                }
            }
            viewModel.loadBooks()
        }
    }

    private fun bindBook(book: Books) {
        binding.tvTitle.text = book.title
        binding.tvAuthor.text = book.author
        binding.tvSummary.text = book.subject
        binding.tvOverview.text = book.description

        val secureUrl = book.thumbnail.replace("http://", "https://")
        Glide.with(requireContext())
            .load(secureUrl)
            .placeholder(com.example.readease.R.drawable.image)
            .error(com.example.readease.R.drawable.image)
            .into(binding.ivCover)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
