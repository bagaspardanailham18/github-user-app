package com.bagas.githubuserapp.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bagas.githubuserapp.data.remote.responses.SearchedUserItem
import com.bagas.githubuserapp.databinding.ItemRowUsersBinding
import com.bagas.githubuserapp.ui.detail.DetailUserActivity
import com.bumptech.glide.Glide

class ListSearchedUserAdapter: ListAdapter<SearchedUserItem, ListSearchedUserAdapter.ListSearchedUserVH>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSearchedUserVH {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListSearchedUserVH(binding)
    }

    override fun onBindViewHolder(holder: ListSearchedUserVH, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ListSearchedUserVH(private val binding: ItemRowUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(listSearchedUser: SearchedUserItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(listSearchedUser.avatarUrl)
                    .into(tvItemAvatar)

                tvItemName.text = listSearchedUser.login

                itemView.setOnClickListener {
                    Intent(itemView.context, DetailUserActivity::class.java).also { intent ->
                        intent.putExtra(DetailUserActivity.EXTRA_USERNAME, listSearchedUser.login)
                        itemView.context.startActivity(intent)
                    }
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<SearchedUserItem> =
            object : DiffUtil.ItemCallback<SearchedUserItem>() {
                override fun areItemsTheSame(
                    oldItem: SearchedUserItem,
                    newItem: SearchedUserItem
                ): Boolean {
                    return oldItem.login == newItem.login
                }

                override fun areContentsTheSame(
                    oldItem: SearchedUserItem,
                    newItem: SearchedUserItem
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }

}