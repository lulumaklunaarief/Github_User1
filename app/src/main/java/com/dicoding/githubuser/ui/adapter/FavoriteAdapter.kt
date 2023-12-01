package com.dicoding.githubuser.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.roomdatabase.Favorite
import com.dicoding.githubuser.databinding.ItemUserFavoriteBinding
import com.dicoding.githubuser.ui.adapter.FollowAdapter.Companion.DIFF_CALLBACK
import com.dicoding.githubuser.ui.main.DetailUserActivity

class FavoriteAdapter : ListAdapter<Favorite, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyViewHolder {
        val binding = ItemUserFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyViewHolder(val binding: ItemUserFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: Favorite) {

            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                    .also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, user.username)
                }
                itemView.context.startActivity(intent)
            }
            binding.tvNameFavorite.text = "${user.username}"
            Glide.with(binding.imgFavorite.context)
                .load(user.avatarUrl)
                .into(binding.imgFavorite)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }
        }
    }
}


