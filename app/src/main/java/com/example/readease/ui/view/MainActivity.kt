package com.example.readease.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.readease.R
import com.example.readease.data.db.AppDatabase
import com.example.readease.databinding.ActivityMainBinding
import com.example.readease.repository.BooksRepository
import com.example.readease.ui.viewmodel.BooksViewModel
import com.example.readease.ui.viewmodel.BooksViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: BooksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewModel Setup
        val dao = AppDatabase.getDatabase(this).booksDao()
        val repository = BooksRepository(dao)
        val factory = BooksViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[BooksViewModel::class.java]

        viewModel.fetchBooksIfEmpty()

        // Setup BottomNav with Navigation Component
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}
