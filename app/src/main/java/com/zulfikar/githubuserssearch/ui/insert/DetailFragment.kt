package com.zulfikar.githubuserssearch.ui.insert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.zulfikar.githubuserssearch.R
import com.zulfikar.githubuserssearch.data.Result
import com.zulfikar.githubuserssearch.data.local.entity.DetailUserEntity
import com.zulfikar.githubuserssearch.data.local.entity.UserEntity
import com.zulfikar.githubuserssearch.databinding.FragmentDetailBinding
import com.zulfikar.githubuserssearch.ui.FollowFragment
import com.zulfikar.githubuserssearch.ui.UserViewModel
import com.zulfikar.githubuserssearch.ui.ViewModelFactory
import com.zulfikar.githubuserssearch.ui.adapter.SectionsPagerAdapter


class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: UserViewModel by viewModels {
            factory
        }

        val username = DetailFragmentArgs.fromBundle(
            arguments as Bundle
        ).username
        var bookmarked = DetailFragmentArgs.fromBundle(arguments as Bundle).bookmarked
        val avatarUrl = DetailFragmentArgs.fromBundle(arguments as Bundle).url

        getUserData(viewModel, username)

        if(bookmarked) {
            binding.floatingActionButton2.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.baseline_favorite_24, null))
        } else {
            binding.floatingActionButton2.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.baseline_favorite_border_24, null))
        }

        with(binding) {
            val sectionsPagerAdapter =
                SectionsPagerAdapter(requireActivity() as AppCompatActivity, username)
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

            toolbar.inflateMenu(R.menu.detail_menu)
            toolbar.setupWithNavController(findNavController())
            toolbar.setTitle(R.string.detail_title)

            floatingActionButton2.setOnClickListener {
                val news: UserEntity = UserEntity(
                    username,
                    avatarUrl,
                    bookmarked
                )
                if (bookmarked){
                    floatingActionButton2.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.baseline_favorite_border_24, null))
                    viewModel.deleteUser(news)
                    bookmarked = false
                } else {
                    floatingActionButton2.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.baseline_favorite_24, null))
                    viewModel.saveUser(news)
                    bookmarked = true
                }
            }

            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu1 -> {
                        val mover = DetailFragmentDirections.actionDetailFragmentToSwitchFragment()
                        findNavController().navigate(mover)
                        true
                    }

                    else -> {
                        super.onOptionsItemSelected(it)
                        false
                    }
                }
            }

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val fragment = FollowFragment()
                    fragment.arguments = Bundle().apply {
                        when (position) {
                            0 -> {
                                putInt(FollowFragment.CONDITION, position)
                                putString(FollowFragment.USERNAME, username)
                            }

                            1 -> {
                                putInt(FollowFragment.CONDITION, position)
                                putString(FollowFragment.USERNAME, username)
                            }
                        }
                    }
                }
            })
        }
    }

    private fun getUserData(viewModel: UserViewModel, username: String) {
        viewModel.getDetail(username).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar3?.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding?.progressBar3?.visibility = View.GONE
                        setDetail(result.data)
                    }

                    is Result.Error -> {
                        binding?.progressBar3?.visibility = View.GONE
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

    private fun setDetail(detail: DetailUserEntity) {
        if (detail != null) {
            Glide.with(requireContext()).load(detail.avatarUrl).into(binding.imageView4)
            binding.textView3.text = detail.name
            binding.textView4.text = detail.username
            val template = "${detail.followers} followers, ${detail.following} following"
            binding.textView5.text = template
        }

    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1, R.string.tab_text_2
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}