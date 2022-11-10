package com.bagas.githubuserapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagas.githubuserapp.data.remote.Result
import com.bagas.githubuserapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var adapter = ListUserAdapter()
    private var searchAdapter = ListSearchedUserAdapter()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsers.setHasFixedSize(true)
        showRvUser()

        searchbarAction()
    }

    private fun showRvUser() {
        lifecycleScope.launch {
            mainViewModel.getAllUsers().observe(this@MainActivity) { usersResult ->
                when (usersResult) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val listUserData = usersResult.data
                        adapter.submitList(listUserData)
                        binding.rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
                        binding.rvUsers.adapter = adapter
                    }
                    is Result.Error -> {
                        Toast.makeText(this@MainActivity, usersResult.error, Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun searchbarAction() {
        binding.searchBar.setOnQueryTextListener(object: OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query!!.isNotEmpty()) {
                    lifecycleScope.launch {
                        mainViewModel.getSearchedUsers(query.toString()).observe(this@MainActivity) { searchResult ->
                            when (searchResult) {
                                is Result.Loading -> {
                                    binding.progressBar.visibility = View.VISIBLE
                                }
                                is Result.Success -> {
                                    binding.progressBar.visibility = View.GONE
                                    binding.rvSearchedUsers.visibility = View.VISIBLE
                                    binding.rvUsers.visibility = View.GONE

                                    val data = searchResult.data.items
                                    searchAdapter.submitList(data)
                                    binding.rvSearchedUsers.layoutManager = LinearLayoutManager(this@MainActivity)
                                    binding.rvSearchedUsers.adapter = searchAdapter
                                    binding.rvSearchedUsers.setHasFixedSize(true)
                                }
                                is Result.Error -> {
                                    Toast.makeText(this@MainActivity, searchResult.error, Toast.LENGTH_SHORT).show()
                                    binding.progressBar.visibility = View.GONE
                                    binding.rvUsers.visibility = View.VISIBLE
                                    binding.rvSearchedUsers.visibility = View.GONE
                                }
                            }
                        }
                    }
                } else {
                    binding.rvSearchedUsers.visibility = View.GONE
                    binding.rvUsers.visibility = View.VISIBLE
                }
                return true
            }

        })
    }
}