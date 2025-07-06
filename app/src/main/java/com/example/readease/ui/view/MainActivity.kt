package com.example.readease.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import com.example.readease.R
import com.example.readease.data.db.AppDatabase
import com.example.readease.databinding.ActivityMainBinding
import com.example.readease.repository.BooksRepository
import com.example.readease.ui.viewmodel.BooksViewModel
import com.example.readease.ui.viewmodel.BooksViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: BooksViewModel
    private lateinit var navController: NavController

    private var skipExploreNavigation = false  // 👈 NEW FLAG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = AppDatabase.getDatabase(this).booksDao()
        val repository = BooksRepository(dao)
        val factory = BooksViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[BooksViewModel::class.java]

        viewModel.fetchBooksIfEmpty()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // ✅ Bottom Navigation Item Selection
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.exploreFragment -> {
                    if (!skipExploreNavigation) {
                        lifecycleScope.launch {
                            val firstBook = viewModel.books.value?.firstOrNull()
                            val bundle = Bundle().apply {
                                if (firstBook != null) putParcelable("book", firstBook)
                            }
                            navController.navigate(R.id.exploreFragment, bundle)
                        }
                    }
                    skipExploreNavigation = false // reset after use
                    true
                }

                R.id.collectionFragment -> {
                    navController.navigate(R.id.collectionFragment)
                    true
                }

                else -> false
            }
        }
    }

    fun setSelectedBottomNavItem(itemId: Int) {
        if (itemId == R.id.exploreFragment) {
            skipExploreNavigation = true
        }
        binding.bottomNavigationView.selectedItemId = itemId
    }
}
