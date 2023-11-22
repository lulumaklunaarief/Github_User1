package com.dicoding.githubuser.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.databinding.FragmentFollowBinding
import com.dicoding.githubuser.ui.VM.FollowViewModel
import com.dicoding.githubuser.ui.adapter.UserAdapter


class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private val followViewModel by activityViewModels<FollowViewModel>()

    companion object {
        const val ARG_NAME = "app_name"
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = ""
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(ARG_POSITION)
        val username = arguments?.getString(ARG_USERNAME)

        val layoutManager = LinearLayoutManager(context)
        binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())




        followViewModel.showLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        if (position == 1) {
            followViewModel.loadListFollower(username.toString())
            followViewModel.listFollowers.observe(viewLifecycleOwner) { listFollowing ->
                setListFollowingData(listFollowing)
            }
        } else {
            followViewModel.loadListFollowing(username.toString())
            followViewModel.listFollowing.observe(viewLifecycleOwner) { listFollower ->
                setListFollowerData(listFollower)
            }
        }
    }
    private fun setListFollowingData(listFollowing: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(listFollowing)
        binding.rvFollow.adapter = adapter
    }
    private fun setListFollowerData(listFollower: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(listFollower)
        binding.rvFollow.adapter = adapter
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}