package com.dhanush.twitterclone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel: ViewModel() {
    val hashtagString = MutableLiveData<String>()
}
