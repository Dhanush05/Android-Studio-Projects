package com.dhanush.twitterclone.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhanush.twitterclone.R
import com.dhanush.twitterclone.databinding.FragmentSearchBinding
import com.dhanush.twitterclone.model.DATA_TWEETS
import com.dhanush.twitterclone.model.DATA_TWEET_HASHTAGS
import com.dhanush.twitterclone.model.Tweet
import com.dhanush.twitterclone.model.User
import com.dhanush.twitterclone.view.adapters.TweetListAdapter
import com.dhanush.twitterclone.view.listeners.TweetListener
import com.dhanush.twitterclone.viewmodel.SearchViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : TwitterFragment() {
    private lateinit var viewModel : SearchViewModel

    private var currentHashtag = ""
    private lateinit var binding: FragmentSearchBinding
    private var tweetsAdapter: TweetListAdapter? = null
    private var currentUser: User? =null
    private val firebaseDB = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val listener: TweetListener? = null
    private lateinit var followHashtag:ImageView
    private lateinit var tweetList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        Toast.makeText(activity,"View Created",Toast.LENGTH_SHORT).show()
        tweetList = binding.tweetList
        followHashtag = binding.followHashtag
        viewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]

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
//        viewModel.setHashTag("term")
//        currentHashtag = viewModel.hashtagString.value.toString()
//        newHashTag(currentHashtag)
        viewModel.hashtagString.observe(viewLifecycleOwner) { term ->
            currentHashtag = term
            Toast.makeText(activity,"change in viewmodel found", Toast.LENGTH_SHORT).show()
            newHashTag()
        }

    }

    fun newHashTag() {
//        Toast.makeText(activity,"hashtag is:"+currentHashtag,Toast.LENGTH_SHORT).show()
        followHashtag.visibility =View.VISIBLE
        updateList()

    }

    private fun updateList() {
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
    }


//    companion object{
//        private const val ARG_TEXT = "text"
//        fun newInstance(text: String): SearchFragment{
//            val fragment = SearchFragment()
//            val args =  Bundle()
//            args.putString(ARG_TEXT,text)
//            fragment.arguments = args
//            return fragment
//        }
//    }

}