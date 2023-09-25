package com.zulfikar.githubuserssearch.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zulfikar.githubuserssearch.data.Result
import com.zulfikar.githubuserssearch.databinding.FragmentFollowBinding
import com.zulfikar.githubuserssearch.ui.adapter.UserAdapter
import com.zulfikar.githubuserssearch.ui.insert.DetailFragmentDirections

class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pos = arguments?.getInt(CONDITION)
        val username = arguments?.getString(USERNAME)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: UserViewModel by viewModels {
            factory
        }
        val userAdapter = UserAdapter {user ->
            val mover = DetailFragmentDirections.actionDetailFragmentSelf()
            mover.username = user.username
            mover.url = user.avatarUrl
            mover.bookmarked = user.isBookmarked
            findNavController().navigate(mover)
        }

        if (!username.isNullOrEmpty()) {
            when (pos) {
                0 -> {
                    setFollower(viewModel, userAdapter, username)
                }

                1 -> {
                    setFollowing(viewModel, userAdapter, username)
                }
            }
        }

        binding.rvFollow.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = userAdapter
        }
    }

    companion object {
        const val CONDITION = "CONDITION"
        const val USERNAME = "USERNAME"
    }

    private fun setFollowing(viewModel: UserViewModel, userAdapter: UserAdapter, username: String) {
        viewModel.getFollowing(username).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar2.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar2.visibility = View.GONE
                        val newsData = result.data
                        userAdapter.submitList(newsData)
                    }

                    is Result.Error -> {
                        binding.progressBar2.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setFollower(viewModel: UserViewModel, userAdapter: UserAdapter, username: String) {
        viewModel.getFollower(username).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar2.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar2.visibility = View.GONE
                        val newsData = result.data
                        userAdapter.submitList(newsData)
                    }

                    is Result.Error -> {
                        binding.progressBar2.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}