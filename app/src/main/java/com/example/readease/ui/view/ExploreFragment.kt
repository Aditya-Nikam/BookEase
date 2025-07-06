package com.example.readease.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.readease.R
import com.example.readease.data.model.Books
import com.example.readease.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private lateinit var book: Books

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getParcelable<Books>("book")?.let { selectedBook ->
            book = selectedBook

            binding.tvTitle.text = book.title
            binding.tvAuthor.text = book.author
            binding.tvSummary.text = book.subject
            binding.tvOverview.text = book.description

            Glide.with(requireContext())
                .load(book.thumbnail.replace("http://", "https://"))
                .placeholder(com.example.readease.R.drawable.image)
                .error(com.example.readease.R.drawable.image)
                .into(binding.ivCover)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}