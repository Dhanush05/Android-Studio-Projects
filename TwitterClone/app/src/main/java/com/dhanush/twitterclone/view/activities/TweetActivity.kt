
package com.dhanush.twitterclone.view.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.dhanush.twitterclone.R
import com.dhanush.twitterclone.databinding.ActivityTweetBinding
import com.dhanush.twitterclone.model.DATA_TWEETS
import com.dhanush.twitterclone.model.DATA_TWEET_IMAGES
import com.dhanush.twitterclone.model.DATA_TWEET_IMAGE_URL
import com.dhanush.twitterclone.model.DATA_USERS
import com.dhanush.twitterclone.model.Tweet
import com.dhanush.twitterclone.model.loadUrl
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class TweetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTweetBinding
    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance().reference
    private var imageUrl:String? = null
    private var userId:String? = null
    private var userName: String? = null
    private var tweetIdRetreived:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTweetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra(PARAM_USER_ID)&&intent.hasExtra(PARAM_USER_NAME)){
            userId = intent.getStringExtra(PARAM_USER_ID)
            userName = intent.getStringExtra(PARAM_USER_NAME)
        }
        else{
            Toast.makeText(this,"Error creating tweet. Make sure you have a username",Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.tweetImage.setOnClickListener {
            uploadImage()
        }
        binding.fabPhoto.setOnClickListener {
            uploadImage()
        }
        binding.fabSend.setOnClickListener {
            postTweet()
        }
        binding.tweetProgressLayout.setOnTouchListener { view, motionEvent -> true }

    }


    private fun postTweet() {
        binding.tweetProgressLayout.visibility = View.VISIBLE
        val text = binding.tweetText.text.toString()
        val hashtags = extractHashTags(text)
        val tweetId = firebaseDB.collection(DATA_TWEETS).document()
        tweetIdRetreived = tweetId.id
        val tweet = Tweet(tweetId.id, arrayListOf(userId!!),userName,text, imageUrl, System.currentTimeMillis(), hashtags,
            arrayListOf())
        tweetId.set(tweet)
            .addOnSuccessListener { finish() }
            .addOnFailureListener {
                it.printStackTrace()
                binding.tweetProgressLayout.visibility = View.GONE
                Toast.makeText(this,"Failed to post tweet",Toast.LENGTH_SHORT).show()
            }
    }

    private fun extractHashTags(input: String): ArrayList<String> {
        val words = input.split(" ")
        val hashtags = ArrayList<String>()
        for(word in words){
            if(word.startsWith("#")){
                hashtags.add(word.trim())
            }
        }
        return hashtags
    }

    private fun uploadImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startForActivityResult.launch(intent)
    }
    private val startForActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if(result.resultCode == RESULT_OK){
            val data: Intent? = result.data
            val imageUri: Uri? = data?.data
            storeImage(imageUri)
        }
    }

    private fun storeImage(imageUri: Uri?) {
        imageUri?.let{
            Toast.makeText(this,"Uploading...",Toast.LENGTH_SHORT).show()
            binding.tweetProgressLayout.visibility= View.VISIBLE
            val filepath = firebaseStorage.child(DATA_TWEET_IMAGES).child(userId!!)
            filepath.putFile(imageUri)
                .addOnSuccessListener {
                    filepath.downloadUrl
                        .addOnSuccessListener {
                            imageUrl = it.toString()
                            binding.tweetImage.loadUrl(imageUrl, R.drawable.logo)
                            binding.tweetImage.background = null
                            binding.tweetProgressLayout.visibility= View.GONE
                        }
                        .addOnFailureListener {
                            uploadFail()
                        }
                }
                .addOnFailureListener{
                    uploadFail()
                }
        }

    }

    private fun uploadFail() {
        Toast.makeText(this,"Image upload failed. Please try agian later", Toast.LENGTH_SHORT).show()
        binding.tweetProgressLayout.visibility= View.GONE
    }


    companion object{
        val PARAM_USER_ID = "UserId"
        val PARAM_USER_NAME = "UserName"
        fun newIntent(context: Context, userId:String?, userName:String?): Intent {
            val intent = Intent(context,TweetActivity::class.java )
            intent.putExtra(PARAM_USER_ID,userId)
            intent.putExtra(PARAM_USER_NAME,userName)
            return  intent
        }

    }
}