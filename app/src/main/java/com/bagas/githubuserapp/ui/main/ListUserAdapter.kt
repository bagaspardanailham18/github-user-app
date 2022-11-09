package com.bagas.githubuserapp.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bagas.githubuserapp.data.remote.responses.ListUserResponseItem
import com.bagas.githubuserapp.databinding.ItemRowUsersBinding
import com.bagas.githubuserapp.ui.detail.DetailUserActivity
import com.bumptech.glide.Glide

class ListUserAdapter: ListAdapter<ListUserResponseItem, ListUserAdapter.ListUserVH>(DIFF_CALBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserVH {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListUserVH(binding)
    }

    override fun onBindViewHolder(holder: ListUserVH, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ListUserVH(private val binding: ItemRowUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(listUser: ListUserResponseItem?) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(listUser!!.avatarUrl)
                    .into(tvItemAvatar)

                tvItemName.text = listUser.login

                itemView.setOnClickListener {
                    Intent(itemView.context, DetailUserActivity::class.java).also { intent ->
                        intent.putExtra(DetailUserActivity.EXTRA_USERNAME, listUser.login)
                        itemView.context.startActivity(intent)
                    }
                }
            }
        }
    }

    companion object {
        val DIFF_CALBACK: DiffUtil.ItemCallback<ListUserResponseItem> =
            object : DiffUtil.ItemCallback<ListUserResponseItem>() {
                override fun areItemsTheSame(
                    oldItem: ListUserResponseItem,
                    newItem: ListUserResponseItem
                ): Boolean {
                    return oldItem.login == newItem.login
                }

                override fun areContentsTheSame(
                    oldItem: ListUserResponseItem,
                    newItem: ListUserResponseItem
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}