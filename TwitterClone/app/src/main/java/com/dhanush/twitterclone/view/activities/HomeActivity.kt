package com.dhanush.twitterclone.view.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.dhanush.twitterclone.R
import com.dhanush.twitterclone.databinding.ActivityHomeBinding
import com.dhanush.twitterclone.model.DATA_USERS
import com.dhanush.twitterclone.model.User
import com.dhanush.twitterclone.model.loadUrl
import com.dhanush.twitterclone.view.adapters.SectionsPageAdapter
import com.dhanush.twitterclone.view.fragments.ActivityFragment
import com.dhanush.twitterclone.view.fragments.HomeFragment
import com.dhanush.twitterclone.view.fragments.SearchFragment
import com.dhanush.twitterclone.view.fragments.TwitterFragment
import com.dhanush.twitterclone.viewmodel.SharedViewModel
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDB = FirebaseFirestore.getInstance()
    private var userID = firebaseAuth.currentUser?.uid
    private lateinit var binding: ActivityHomeBinding
    var user: User? = null
    private lateinit var sharedViewModel: SharedViewModel
    private var currentFragment: TwitterFragment = HomeFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        sharedViewModel.setCurrentFragment(HomeFragment())

        val adapter = SectionsPageAdapter(this)
        binding.viewPagerContainer.adapter = adapter
        binding.viewPagerContainer.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
           override fun onPageSelected(position: Int) {
               binding.tabs.selectTab(binding.tabs.getTabAt(position))
           }
       })
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.viewPagerContainer.currentItem = tab.position
                }
                when(tab?.position){
                    0->{
                        binding.title.visibility = View.VISIBLE
                        binding.title.text = "Home"
                        binding.searchBar.visibility = View.GONE
                        sharedViewModel.setCurrentFragment(HomeFragment())
                        currentFragment = HomeFragment()
                    }
                    1->{
                        binding.title.visibility = View.GONE
                        binding.searchBar.visibility = View.VISIBLE
                        sharedViewModel.setCurrentFragment(SearchFragment())
                        currentFragment = SearchFragment()
                    }
                    2->{
                        binding.title.visibility = View.VISIBLE
                        binding.title.text = "My Activity"
                        binding.searchBar.visibility = View.GONE
                        sharedViewModel.setCurrentFragment(ActivityFragment())
                        currentFragment = ActivityFragment()
                    }

                }

            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.logo.setOnClickListener { view->
            startActivity(ProfileActivity.newIntent(this))
        }
        binding.fab.setOnClickListener{
            startActivity(TweetActivity.newIntent(this,userID,user?.username))
        }
        binding.search.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE|| actionId ==EditorInfo.IME_ACTION_SEARCH){
                Toast.makeText(this, "Action bar working",Toast.LENGTH_SHORT).show()
                sharedViewModel.hashtagString.value = v?.text.toString()
            }
            true
        }

    }
//    override fun onUserUpdated() {
//        populateLogo()
//    }
    private fun populateLogo(){
        binding.homeProgressLayout.visibility = View.VISIBLE
        firebaseDB.collection(DATA_USERS).document(userID!!).get()
            .addOnSuccessListener {
                binding.homeProgressLayout.visibility = View.GONE
                user = it.toObject(User::class.java)
//                val imageURL = user?.imageUrl
                user?.imageUrl.let {
                    binding.logo.loadUrl(user?.imageUrl, R.drawable.logo)
                }
                //let fragments know that user has been updated.
                updateFragmentUser()
            }
            .addOnFailureListener { e->
                e.stackTrace
            }

    }
    fun updateFragmentUser(){
        sharedViewModel.currrentUser.value = user
//        SearchFragment().setUser(user!!)
//        HomeFragment().setUser(user!!)
//        ActivityFragment().setUser(user!!)
//        currentFragment.updateList()
    }

    override fun onResume() {
        super.onResume()
        userID = FirebaseAuth.getInstance().currentUser?.uid

        if(userID==null){
            startActivity(LoginActivity.newIntent(this))
        }
        else{
            populateLogo()
        }

    }



    companion object{
        fun newIntent(context: Context)= Intent(context, HomeActivity::class.java)
    }




}