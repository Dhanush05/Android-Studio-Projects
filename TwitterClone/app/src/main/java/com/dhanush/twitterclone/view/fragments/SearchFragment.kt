package com.dhanush.twitterclone.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhanush.twitterclone.R
import com.dhanush.twitterclone.databinding.FragmentSearchBinding
import com.dhanush.twitterclone.model.DATA_TWEETS
import com.dhanush.twitterclone.model.DATA_TWEET_HASHTAGS
import com.dhanush.twitterclone.model.DATA_USERS
import com.dhanush.twitterclone.model.DATA_USERS_HASHTAGS
import com.dhanush.twitterclone.model.Tweet
import com.dhanush.twitterclone.model.User
import com.dhanush.twitterclone.view.adapters.TweetListAdapter
import com.dhanush.twitterclone.view.listeners.TweetListener
import com.dhanush.twitterclone.viewmodel.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : TwitterFragment() {
    private lateinit var viewModel : SharedViewModel
    private var currentHashtag = ""
    private lateinit var binding: FragmentSearchBinding
    private lateinit var followHashtag:ImageView
    private lateinit var tweetList: RecyclerView
    private var hashtagFollowed = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        Toast.makeText(activity,"View Created",Toast.LENGTH_SHORT).show()
        tweetList = binding.tweetList
        followHashtag = binding.followHashtag
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tweetsAdapter  = TweetListAdapter(userId!!, arrayListOf())
        tweetsAdapter?.setListener(listener)
        binding.tweetList?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tweetsAdapter
            addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
        }

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            updateList()
        }

        viewModel.hashtagString.observe(viewLifecycleOwner) { term ->
            currentHashtag = term
            Toast.makeText(activity,"change in viewmodel found", Toast.LENGTH_SHORT).show()
            newHashTag()
        }
        viewModel.currrentUser.observe(viewLifecycleOwner){
            currentUser = it
        }
        binding.followHashtag.setOnClickListener {
            val followed = currentUser?.followHashTags
            binding.followHashtag.isClickable = false
            hashtagFollowed = currentUser?.followHashTags?.contains(currentHashtag) == true
            if(hashtagFollowed){
                followed?.remove(currentHashtag)
            }
            else{
                followed?.add(currentHashtag)
            }
            firebaseDB.collection(DATA_USERS).document(userId).update(DATA_USERS_HASHTAGS,followed)
                .addOnSuccessListener {
//                    callback?.onUserUpdated()
                    binding.followHashtag.isClickable = true
                }
                .addOnFailureListener {
                    it.printStackTrace()
                    binding.followHashtag.isClickable = true
                }
            updateFollowDrawable()
            updateList()

        }

    }

    private fun newHashTag() {
//        Toast.makeText(activity,"hashtag is:"+currentHashtag,Toast.LENGTH_SHORT).show()
        followHashtag.visibility =View.VISIBLE
        updateList()

    }

    override fun updateList() {
        currentHashtag = viewModel.hashtagString.value.toString()
        Toast.makeText(activity,"Cuurent: "+currentHashtag,Toast.LENGTH_SHORT).show()
        tweetList.visibility = View.GONE
        firebaseDB.collection(DATA_TWEETS).whereArrayContains(DATA_TWEET_HASHTAGS,currentHashtag).get()
            .addOnSuccessListener {
//                Toast.makeText(activity,"success",Toast.LENGTH_SHORT).show()
                tweetList.visibility = View.VISIBLE
                val tweets = arrayListOf<Tweet>()
                for(doc in it.documents){
                    val t = doc.toObject(Tweet::class.java)
                    t?.let {
                        tweets.add(it)
                    }
                }
                val sortedTweets = tweets.sortedWith(compareByDescending { it.timestamp })
                tweetsAdapter?.updateTweets(sortedTweets)
            }
            .addOnFailureListener {
                Toast.makeText(activity,"failing",Toast.LENGTH_SHORT).show()
                it.printStackTrace()
            }
        updateFollowDrawable()
    }

    private fun updateFollowDrawable() {

        hashtagFollowed = currentUser?.followHashTags?.contains(currentHashtag) == true
        context?.let {
            if(hashtagFollowed){
                followHashtag.setImageDrawable(ContextCompat.getDrawable(it, R.drawable.follow))
            }
            else{
                followHashtag.setImageDrawable(ContextCompat.getDrawable(it, R.drawable.follow_inactive))
            }
        }
    }


}