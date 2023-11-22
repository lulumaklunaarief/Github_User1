package com.dicoding.githubuser.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubuser.ui.fragment.FollowFragment

class SectionPagerAdapter (activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {

    var appName: String = "String"

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.ARG_POSITION, position + 1)
            putString(FollowFragment.ARG_NAME, appName)
            putString(FollowFragment.ARG_USERNAME, username)
        }
        return fragment
    }

}