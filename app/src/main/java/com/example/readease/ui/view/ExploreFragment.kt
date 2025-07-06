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
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentExploreBinding.bind(view)

        val book = arguments?.getParcelable<Books>("book")
        book?.let {
            binding.tvTitle.text = it.title
            binding.tvAuthor.text = it.subject
            binding.tvOverview.text = it.category

            val secureUrl = it.thumbnail.replace("http://", "https://")
            Glide.with(requireContext())
                .load(secureUrl)
                .placeholder(R.drawable.image)
                .into(binding.ivCover)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}