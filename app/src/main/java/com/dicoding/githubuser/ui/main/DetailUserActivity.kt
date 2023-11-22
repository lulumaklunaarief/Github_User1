package com.dicoding.githubuser.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.ActivityDetailUserBinding
import com.dicoding.githubuser.ui.VM.DetailUserViewModel
import com.dicoding.githubuser.ui.adapter.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel by viewModels<DetailUserViewModel>()

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation= 0f
        val username = intent.getStringExtra(EXTRA_USERNAME)

        val sectionPagerAdapter = SectionPagerAdapter(this, username ?: " ")
        binding.viewPager.adapter = sectionPagerAdapter

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        viewPager.adapter = sectionPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.tab_1)
                1 -> tab.text = resources.getString(R.string.tab_2)
            }
        }.attach()

        detailUserViewModel.showLoading.observe(this) {
            showLoading(it)
        }
        detailUserViewModel.detailUser.observe(this) {

            if (it != null) {
                binding.apply {

                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = "${it.followers.toString()} Follower"
                    binding.tvFollowing.text = "${it.following.toString()} Following"
                    Glide.with(this@DetailUserActivity).load(it.avatarUrl).into(binding.userPhoto)
                }
            }
        }

        detailUserViewModel.setUserDetail(username ?: "username")
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}