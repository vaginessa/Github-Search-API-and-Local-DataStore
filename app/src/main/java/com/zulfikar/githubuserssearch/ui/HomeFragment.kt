package com.zulfikar.githubuserssearch.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zulfikar.githubuserssearch.R
import com.zulfikar.githubuserssearch.data.Result
import com.zulfikar.githubuserssearch.data.local.entity.UserEntity
import com.zulfikar.githubuserssearch.databinding.FragmentHomeBinding
import com.zulfikar.githubuserssearch.ui.adapter.UserAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: UserViewModel by viewModels {
            factory
        }

        val userAdapter = UserAdapter { user ->
            val mover = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
            mover.username = user.username
            mover.url = user.avatarUrl
            mover.bookmarked = user.isBookmarked
            findNavController().navigate(mover)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            toolbar.inflateMenu(R.menu.home_menu)
            toolbar.setupWithNavController(findNavController())
            toolbar.setTitle(R.string.app_name)

            if (binding.searchView.text.toString().isEmpty()) {
                load(viewModel, userAdapter, "z")
            }

            searchView.editText.setOnEditorActionListener { textView, actionId, event ->
                searchBar.text = searchView.text
                val searchText: String = searchView.text.toString()
                load(viewModel, userAdapter, searchText)
                searchView.hide()
                false
            }

            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu1 -> {
                        val mover = HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()
                        findNavController().navigate(mover)
                        searchBar.text = ""
                        true
                    }
                    R.id.menu2 -> {
                        val mover = HomeFragmentDirections.actionHomeFragmentToSwitchFragment()
                        findNavController().navigate(mover)
                        searchBar.text = ""
                        true
                    }
                    else -> {
                        super.onOptionsItemSelected(it)
                        false
                    }
                }
            }

            binding?.rvUser?.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = userAdapter
            }
        }
    }

    private fun load(viewModel: UserViewModel, userAdapter: UserAdapter, username: String) {
        viewModel.getUser(username).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val newsData = result.data

                        val sortedData = newsData.sortedWith(
                            compareByDescending<UserEntity> { it.isBookmarked == false }
                                .thenBy { it.username }
                        )

                        userAdapter.submitList(sortedData)
                        binding.rvUser.scrollToPosition(0)
                    }

                    is Result.Error -> {
                        binding?.progressBar?.visibility = View.GONE
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