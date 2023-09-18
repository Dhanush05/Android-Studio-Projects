package com.dhanush.twitterclone.view.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dhanush.twitterclone.R
import com.dhanush.twitterclone.databinding.ActivityTweetBinding
import com.dhanush.twitterclone.databinding.ItemTweetBinding
import com.dhanush.twitterclone.model.Tweet
import com.dhanush.twitterclone.model.getDate
import com.dhanush.twitterclone.model.loadUrl
import com.dhanush.twitterclone.view.listeners.TweetListener

class TweetListAdapter(val userId: String, val tweets: ArrayList<Tweet>): RecyclerView.Adapter<TweetListAdapter.TweetViewHolder>() {

    private var listener: TweetListener? = null

    fun setListener(listener: TweetListener?){
        this.listener = listener
    }
    fun updateTweets(newTweets: List<Tweet>){
        tweets.clear()
        tweets.addAll(newTweets)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TweetViewHolder (

        LayoutInflater.from(parent.context).inflate(R.layout.item_tweet,parent,false))
    override fun getItemCount() = tweets.size
    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        holder.bind(userId,tweets[position],listener)
    }
    class TweetViewHolder(v: View): RecyclerView.ViewHolder(v){
        private var binding = ItemTweetBinding.bind(v)
        fun bind(userId: String, tweet: Tweet, listener: TweetListener?){
            binding.tweetusername.text = tweet.username
            binding.tweetText.text = tweet.text
            if(tweet.imageUrl.isNullOrEmpty()){
                binding.tweetImage.visibility = View.GONE
            }else{
                binding.tweetImage.visibility = View.VISIBLE
                binding.tweetImage.loadUrl(tweet.imageUrl)
            }
            binding.tweetDate.text = getDate(tweet.timestamp)
            binding.tweetLikeCount.text = tweet.likes?.size.toString()
            binding.retweetCount.text = tweet.userIds?.size?.minus(1).toString()
            binding.tweetLayout.setOnClickListener {
                listener?.onLayoutClick(tweet)
            }
            binding.tweetLike.setOnClickListener {
                listener?.onLike(tweet)
            }
            binding.retweet.setOnClickListener {
                listener?.onRetweet(tweet)
            }
            if(tweet.likes?.contains(userId) == true){
                binding.tweetLike.setImageDrawable(ContextCompat.getDrawable(binding.tweetLike.context, R.drawable.like))
            }
            else{
                binding.tweetLike.setImageDrawable(ContextCompat.getDrawable(binding.tweetLike.context, R.drawable.like_inactive))
            }
            if(tweet.userIds?.get(0) == userId){
                binding.retweet.setImageDrawable(ContextCompat.getDrawable(binding.retweet.context, R.drawable.original))
                binding.retweet.isClickable = false
            }else if(tweet.userIds?.contains(userId) == true){
                binding.retweet.setImageDrawable(ContextCompat.getDrawable(binding.retweet.context, R.drawable.retweet))
            }
            else{
                binding.retweet.setImageDrawable(ContextCompat.getDrawable(binding.retweet.context, R.drawable.retweet_inactive))
            }
        }
    }
}