package com.zulfikar.githubuserssearch.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zulfikar.githubuserssearch.R
import com.zulfikar.githubuserssearch.databinding.FragmentFavoriteBinding
import com.zulfikar.githubuserssearch.ui.adapter.UserAdapter

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: UserViewModel by viewModels {
            factory
        }

        val userAdapter = UserAdapter { user ->
            val mover = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment()
            mover.username = user.username
            mover.url = user.avatarUrl
            mover.bookmarked = user.isBookmarked
            findNavController().navigate(mover)
        }

        with(binding) {
            toolbar.inflateMenu(R.menu.detail_menu)
            toolbar.setupWithNavController(findNavController())
            toolbar.setTitle(R.string.favorite_title)

            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu1 -> {
                        val mover = FavoriteFragmentDirections.actionFavoriteFragmentToSwitchFragment()
                        findNavController().navigate(mover)
                        true
                    }

                    else -> {
                        super.onOptionsItemSelected(it)
                        false
                    }
                }
            }
            viewModel.getBookmarkedUser().observe(viewLifecycleOwner) { bookmarkedNews ->
                binding?.progressBar4?.visibility = View.GONE
                userAdapter.submitList(bookmarkedNews)
            }
            recyclerView2.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = userAdapter
            }
        }
    }
}