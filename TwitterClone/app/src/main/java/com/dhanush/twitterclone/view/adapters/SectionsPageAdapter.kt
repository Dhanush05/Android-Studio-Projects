package com.dhanush.twitterclone.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dhanush.twitterclone.view.fragments.ActivityFragment
import com.dhanush.twitterclone.view.fragments.HomeFragment
import com.dhanush.twitterclone.view.fragments.SearchFragment

class SectionsPageAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int=3
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment()
            1-> SearchFragment()
            else -> ActivityFragment()
        }
    }
}