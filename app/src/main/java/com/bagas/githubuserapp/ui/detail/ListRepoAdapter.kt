package com.bagas.githubuserapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bagas.githubuserapp.data.remote.responses.ListRepositoriesForAUserResponseItem
import com.bagas.githubuserapp.databinding.ItemRowRepositoryBinding

class ListRepoAdapter: ListAdapter<ListRepositoriesForAUserResponseItem, ListRepoAdapter.ListRepoVH>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRepoVH {
        val binding = ItemRowRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListRepoVH(binding)
    }

    override fun onBindViewHolder(holder: ListRepoVH, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ListRepoVH(private val binding: ItemRowRepositoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(listRepo: ListRepositoriesForAUserResponseItem) {
            with(binding) {
                tvItemRepoName.text = listRepo.fullName
                tvItemRepoDescription.text = listRepo.description
                tvItemRepoStars.text = listRepo.stargazersCount.toString()
                tvItemRepoUpdated.text = listRepo.updatedAt
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ListRepositoriesForAUserResponseItem> =
            object : DiffUtil.ItemCallback<ListRepositoriesForAUserResponseItem>() {
                override fun areItemsTheSame(
                    oldItem: ListRepositoriesForAUserResponseItem,
                    newItem: ListRepositoriesForAUserResponseItem
                ): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areContentsTheSame(
                    oldItem: ListRepositoriesForAUserResponseItem,
                    newItem: ListRepositoriesForAUserResponseItem
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}