package com.zulfikar.githubuserssearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zulfikar.githubuserssearch.data.remote.response.FollowItem
import com.zulfikar.githubuserssearch.databinding.ItemRowGitUsersBinding

class FollowAdapter: ListAdapter<FollowItem, FollowAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemRowGitUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyViewHolder(private val binding: ItemRowGitUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FollowItem) {
            binding.tvItemUsername.text = item.login
            Glide.with(binding.root.context).load(item.avatarUrl).into(binding.imgItemPhoto)
        }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowItem>() {
            override fun areItemsTheSame(
                oldItem: FollowItem, newItem: FollowItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FollowItem, newItem: FollowItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}