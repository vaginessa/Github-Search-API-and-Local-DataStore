package com.zulfikar.githubuserssearch.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zulfikar.githubuserssearch.ui.FollowFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val dataToSend: String ) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.CONDITION, position)
            putString(FollowFragment.USERNAME, dataToSend)
        }
        return fragment
    }
}