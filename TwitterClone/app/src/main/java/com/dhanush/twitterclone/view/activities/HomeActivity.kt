package com.dhanush.twitterclone.view.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.dhanush.twitterclone.R
import com.dhanush.twitterclone.databinding.ActivityHomeBinding
import com.dhanush.twitterclone.model.DATA_USERS
import com.dhanush.twitterclone.model.User
import com.dhanush.twitterclone.model.loadUrl
import com.dhanush.twitterclone.view.adapters.SectionsPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDB = FirebaseFirestore.getInstance()
    private var sectionsPageAdapter : SectionsPageAdapter? = null
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2
    private var userID = firebaseAuth.currentUser?.uid
    private lateinit var binding: ActivityHomeBinding
    var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }

    private fun populateLogo(){
        firebaseDB.collection(DATA_USERS).document(userID!!).get()
            .addOnSuccessListener {
                user = it.toObject(User::class.java)
//                val imageURL = user?.imageUrl
                user?.imageUrl.let {
                    binding.logo.loadUrl(user?.imageUrl, R.drawable.logo)
                }
            }
            .addOnFailureListener { e->
                e.stackTrace
            }

    }

    override fun onResume() {
        super.onResume()
        userID = FirebaseAuth.getInstance().currentUser?.uid

        if(userID==null){
            startActivity(LoginActivity.newIntent(this))
        }
        populateLogo()
    }
    fun onLogout(v:View){
        firebaseAuth.signOut()
        startActivity(LoginActivity.newIntent(this))
    }

    companion object{
        fun newIntent(context: Context)= Intent(context, HomeActivity::class.java)
    }
}