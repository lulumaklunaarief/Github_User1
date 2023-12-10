package com.dicoding.githubuser.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.roomdatabase.Favorite
import com.dicoding.githubuser.databinding.ActivityDetailUserBinding
import com.dicoding.githubuser.ui.adapter.SectionPagerAdapter
import com.dicoding.githubuser.ui.viewmodel.DetailUserViewModel
import com.dicoding.githubuser.ui.viewmodel.FavoriteUserViewModel
import com.dicoding.githubuser.ui.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel by viewModels<DetailUserViewModel>()
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel
    private var isUserFavorited = false

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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.elevation = 0f

        favoriteUserViewModel = ViewModelProvider(
            this,
            ViewModelFactory(application)
        )[FavoriteUserViewModel::class.java]
        val currentUser = intent.getParcelableExtra<Favorite>(EXTRA_USERNAME)
        val username = currentUser?.username
        if (username != null) {
            detailUserViewModel.getFavoriteUser(username)
        }



        detailUserViewModel.showLoading.observe(this) {
            showLoading(it)
        }

        if (currentUser != null) {
            favoriteUserViewModel.isFavoriteUser(currentUser.username).observe(this) {
                isUserFavorited = currentUser.username == it
                binding.apply {
                    btnFavorites.setOnClickListener {
                        if (isUserFavorited) {
                            favoriteUserViewModel.delete(currentUser)
                            Toast.makeText(
                                this@DetailUserActivity,
                                "$username telah dihapus dari Favorit",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            favoriteUserViewModel.insert(currentUser)
                            Toast.makeText(
                                this@DetailUserActivity,
                                "$username telah ditambah dari Favorit",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                    if (isUserFavorited) {
                        btnFavorites.setImageResource(R.drawable.baseline_favorite_24)

                    } else {
                        btnFavorites.setImageResource(R.drawable.baseline_favorite_border_24)
                    }

                }
            }
        }



    val user = intent.getStringExtra(EXTRA_USERNAME)

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

        detailUserViewModel.setUserDetail(user ?: "username")
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}