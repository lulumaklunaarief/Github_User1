package com.dicoding.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.response.ItemsItem

ab

class FollowAdapter() : ListAdapter<ItemsItem, FollowAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFolowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val follow = getItem(position)
        holder.bind(follow)
    }

    class MyViewHolder(val binding: ItemFolowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(follow	: ItemsItem){


            Glide.with(binding.root.context).load(follow.avatarUrl).into(binding.imgUserFollow)
            binding.tvUserFollow.text = follow.login
        }
    }

    companion object{
        val DIFF_CALLBACK =object: DiffUtil.ItemCallback<ItemsItem>(){
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}