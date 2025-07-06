package com.example.readease.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.readease.R
import com.example.readease.data.db.AppDatabase
import com.example.readease.data.model.Books
import com.example.readease.databinding.FragmentExploreBinding
import com.example.readease.repository.BooksRepository
import com.example.readease.ui.viewmodel.BooksViewModel
import com.example.readease.ui.viewmodel.BooksViewModelFactory
import kotlinx.coroutines.launch

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BooksViewModel
    private lateinit var currentBook: Books
    private var isCollected = false

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
            viewModel.books.observe(viewLifecycleOwner) { books ->
                if (books.isNotEmpty()) {
                    bindBook(books[0])
                }
            }
            viewModel.loadBooks()
        }

        binding.btnHeart.setOnClickListener {
            isCollected = !isCollected
            updateHeartIcon()

            // Update DB with new collected status
            val updatedBook = currentBook.copy(collected = isCollected)
            lifecycleScope.launch {
                dao.update(updatedBook)
            }
        }
    }

    private fun bindBook(book: Books) {
        currentBook = book
        isCollected = book.collected

        binding.tvTitle.text = book.title
        binding.tvAuthor.text = book.author
        binding.tvSummary.text = book.subject
        binding.tvOverview.text = book.description

        val secureUrl = book.thumbnail.replace("http://", "https://")
        Glide.with(requireContext())
            .load(secureUrl)
            .placeholder(R.drawable.image)
            .error(R.drawable.image)
            .into(binding.ivCover)

        updateHeartIcon()
    }

    private fun updateHeartIcon() {
        if (isCollected) {
            binding.btnHeart.setImageResource(R.drawable.ic_heart_filled)
            binding.btnHeart.setColorFilter(ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark))
        } else {
            binding.btnHeart.setImageResource(R.drawable.ic_heart_outline)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
