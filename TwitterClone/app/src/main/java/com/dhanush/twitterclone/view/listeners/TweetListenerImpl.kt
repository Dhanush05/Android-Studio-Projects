package com.dhanush.twitterclone.view.listeners

import androidx.recyclerview.widget.RecyclerView
import com.dhanush.twitterclone.model.Tweet
import com.dhanush.twitterclone.model.User
import com.dhanush.twitterclone.viewmodel.SharedViewModel

class TweetListenerImpl(val tweetList: RecyclerView, var user: User?, val sharedViewModel: SharedViewModel ): TweetListener {

    override fun onLayoutClick(tweet: Tweet?) {

    }

    override fun onLike(tweet: Tweet?) {

    }

    override fun onRetweet(tweet: Tweet?) {

    }
}