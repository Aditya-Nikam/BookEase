package com.example.readease.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.readease.R
import com.example.readease.data.model.Books
import com.example.readease.databinding.BookItemBinding
import com.example.readease.databinding.ItemCollectionBinding

class BookAdapter(
    private val books: List<Books>,
    private val onItemClick: (Books) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(val binding: BookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val book = books[adapterPosition]
                onItemClick(book)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.binding.textTitle.text = book.title
        holder.binding.textSubject.text = book.subject
        holder.binding.textCategory.text = book.category

        val secureUrl = book.thumbnail.replace("http://", "https://")
        Glide.with(holder.binding.root.context)
            .load(secureUrl)
            .placeholder(R.drawable.image)
            .error(R.drawable.image)
            .into(holder.binding.imageCover)
    }

    override fun getItemCount(): Int = books.size
}

