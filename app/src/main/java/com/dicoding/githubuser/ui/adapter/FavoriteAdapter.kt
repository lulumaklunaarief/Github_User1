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

class FavoriteAdapter(private val listUser: List<Favorite>) : RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {
    inner class MyViewHolder(private val binding: ItemUserFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Favorite) {
            with(binding) {
                Glide.with(root.context)
                    .load(user.avatarUrl).circleCrop().into(imgFavorite)
                tvNameFavorite.text = "${user.username}"

            }

            itemView.setOnClickListener{}
            val intent = Intent(itemView.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USERNAME, Favorite(username = user.username, avatarUrl = user.avatarUrl))
            itemView.context.startActivity(intent)
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FavoriteAdapter.MyViewHolder {
        val binding = ItemUserFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.MyViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

}


