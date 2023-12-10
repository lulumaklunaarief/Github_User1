package com.dicoding.githubuser.data.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.githubuser.data.roomdatabase.Favorite

class FavoriteDiffCallBack(private val oldUserList: List<Favorite>,private val newUserList: List<Favorite>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldUserList.size

    override fun getNewListSize(): Int = newUserList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

}