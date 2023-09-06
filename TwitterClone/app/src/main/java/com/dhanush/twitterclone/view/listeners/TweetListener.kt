package com.dhanush.twitterclone.view.listeners

import com.dhanush.twitterclone.model.Tweet

interface TweetListener {
    fun onLayoutClick(tweet: Tweet?)
    fun onLike(tweet: Tweet?)
    fun onRetweet(tweet: Tweet?)
}
