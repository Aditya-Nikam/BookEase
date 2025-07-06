package com.example.readease.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.readease.data.model.Books
import com.example.readease.databinding.ItemCollectionBinding

class BookCollectionAdapter(private val books: List<Books>) :
    RecyclerView.Adapter<BookCollectionAdapter.BookViewHolder>() {

    inner class BookViewHolder(val binding: ItemCollectionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.binding.textTitle.text = book.title
        holder.binding.textAuthor.text = book.author
        holder.binding.textDescription.text = book.description

        val secureUrl = book.thumbnail.replace("http://", "https://")
        Glide.with(holder.binding.root.context)
            .load(secureUrl)
            .placeholder(com.example.readease.R.drawable.image)
            .error(com.example.readease.R.drawable.image)
            .into(holder.binding.imageBook)
    }

    override fun getItemCount(): Int = books.size
}