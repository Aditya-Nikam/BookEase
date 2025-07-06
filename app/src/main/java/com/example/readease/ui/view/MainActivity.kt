package com.example.readease.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.readease.R
import com.example.readease.data.db.AppDatabase
import com.example.readease.databinding.ActivityMainBinding
import com.example.readease.repository.BooksRepository
import com.example.readease.ui.viewmodel.BooksViewModel
import com.example.readease.ui.viewmodel.BooksViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fetching Books through api Call
        // Setup Repository
        val dao = AppDatabase.getDatabase(this).booksDao()
        val repository = BooksRepository(dao)

        // Pass repository into ViewModel via factory
        val factory = BooksViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[BooksViewModel::class.java]

        viewModel.fetchBooksIfEmpty()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Default Fragment
        supportFragmentManager.beginTransaction()
            .replace(binding.mainFragmentContainer.id, HomeFragment())
            .commit()

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.mainFragmentContainer.id, HomeFragment())
                        .commit()
                    true
                }
                R.id.nav_explore -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.mainFragmentContainer.id, ExploreFragment())
                        .commit()
                    true
                }
                R.id.nav_collection -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.mainFragmentContainer.id, CollectionFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}
