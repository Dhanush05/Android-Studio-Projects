package com.dhanush.twitterclone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhanush.twitterclone.model.User
import com.dhanush.twitterclone.view.fragments.HomeFragment
import com.dhanush.twitterclone.view.fragments.TwitterFragment

class SharedViewModel: ViewModel() {
    val hashtagString = MutableLiveData<String>()
    val currrentUser = MutableLiveData<User>()
    val currentFragment = MutableLiveData<TwitterFragment>()
    fun setCurrentFragment(fragment: TwitterFragment){
        currentFragment.value = fragment
    }
}
