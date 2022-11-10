package com.bagas.githubuserapp.ui.detail

import android.nfc.NfcAdapter.EXTRA_ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagas.githubuserapp.data.remote.Result
import com.bagas.githubuserapp.databinding.ActivityDetailUserBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    private val detailViewModel: DetailViewModel by viewModels()

    private val adapter = ListRepoAdapter()

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getStringExtra(EXTRA_USERNAME).toString().trim()

        setUserDetail(data)

    }

    private fun setUserDetail(username: String) {
        lifecycleScope.launch {
            detailViewModel.getDetailUser(username).observe(this@DetailUserActivity) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        val data = result.data
                        binding.apply {
                            progressBar.visibility = View.GONE

                            Glide.with(this@DetailUserActivity)
                                .load(data.avatarUrl.toString())
                                .into(tvAvatar)

                            if (data.name == null) {
                                tvName.text = "No name"
                            } else {
                                tvName.text = data.name
                            }

                            tvUsername.text = data.login

                            if (data.bio == null) {
                                tvBio.text = "no bio"
                            } else {
                                tvBio.text = data.bio
                            }

                            setUserReposRv(data.login)
                        }
                    }
                    is Result.Error -> {
                        Toast.makeText(this@DetailUserActivity, result.error, Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setUserReposRv(username: String?) {
        lifecycleScope.launch {
            detailViewModel.getUserRepos(username!!).observe(this@DetailUserActivity) { reposResult ->
                when (reposResult) {
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        adapter.submitList(reposResult.data.listRepositoriesForAUserResponse)
                        binding.rvRepos.layoutManager = LinearLayoutManager(this@DetailUserActivity)
                        binding.rvRepos.adapter = adapter
                        binding.rvRepos.setHasFixedSize(true)
                    }
                }
            }
        }
    }
}