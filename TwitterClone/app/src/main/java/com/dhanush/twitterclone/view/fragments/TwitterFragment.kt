package com.dhanush.twitterclone.view.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import com.dhanush.twitterclone.model.User
import com.dhanush.twitterclone.view.adapters.TweetListAdapter
import com.dhanush.twitterclone.view.listeners.TweetListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

abstract class TwitterFragment: Fragment() {
    protected var tweetsAdapter: TweetListAdapter? = null
    protected var currentUser: User? =null
    protected val firebaseDB = FirebaseFirestore.getInstance()
    protected val userId = FirebaseAuth.getInstance().currentUser?.uid
    protected val listener: TweetListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
//    fun setUser(user: User){
//        this.currentUser = user
//    }
    abstract fun updateList()
    override fun onResume() {
        super.onResume()
        updateList()
    }
}