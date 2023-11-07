package com.dhanush.twitterclone.view.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.dhanush.twitterclone.R
import com.dhanush.twitterclone.databinding.ActivityProfileBinding
import com.dhanush.twitterclone.model.DATA_IMAGES
import com.dhanush.twitterclone.model.DATA_USERS
import com.dhanush.twitterclone.model.DATA_USERS_EMAIL
import com.dhanush.twitterclone.model.DATA_USER_IMAGE_URL
import com.dhanush.twitterclone.model.DATA_USER_USERNAME
import com.dhanush.twitterclone.model.User
import com.dhanush.twitterclone.model.loadUrl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDB = FirebaseFirestore.getInstance()
    private val userID = firebaseAuth.currentUser?.uid
    private val firebaseStorage = FirebaseStorage.getInstance().reference
    private var imageURL: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        if(userID==null){
            finish()
        }
        binding.profileProgressLayout.setOnTouchListener { v, event ->  true}
        populateInfo()

        binding.applyButton.setOnClickListener {
            onApply()
        }
        binding.signoutButton.setOnClickListener {
            firebaseAuth.signOut()
            finish()
        }
        binding.photoIV.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Choose Image source:")
                .setItems(arrayOf("Camera","Files")){_,which->
                    when(which){
                        0->{
                            val intent = Intent(Intent.ACTION_CAMERA_BUTTON)
                            startActivityLoadFromCamera.launch(null)
                        }
                        1->{
                            val intent = Intent(Intent.ACTION_PICK)
                            intent.type = "image/*"
                            startActivityPickImageFromFile.launch(intent)
                        }
                    }
                }.show()
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = "image/*"
//            startForActivityResult.launch(intent)

        }
    }
    private val startActivityLoadFromCamera = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){result->
        if (result is Bitmap){
            binding.photoIV.setImageBitmap(result)
        }
    }
    private val startActivityPickImageFromFile = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if(result.resultCode == RESULT_OK ){
            val data: Intent? = result.data
            val imageUri: Uri? = data?.data
            storeImage(imageUri)
        }
    }

    private fun storeImage(imageUri: Uri?) {
        imageUri?.let {
            Toast.makeText(this, "Uploading...",Toast.LENGTH_SHORT).show()
            binding.profileProgressLayout.visibility = View.VISIBLE
            val filePath = firebaseStorage.child(DATA_IMAGES).child(userID!!)
            filePath.putFile(imageUri)
                .addOnSuccessListener {
                    filePath.downloadUrl
                        .addOnSuccessListener {
                            val url = it.toString()
                            firebaseDB.collection(DATA_USERS).document(userID!!).update(
                                DATA_USER_IMAGE_URL, url)
                                .addOnSuccessListener {
                                    imageURL = url
                                    binding.photoIV.loadUrl(imageURL,R.drawable.logo)
                                }
                            binding.profileProgressLayout.visibility = View.GONE
                    }
                        .addOnFailureListener {
                            onUploadFail()
                        }
                }
                .addOnFailureListener{
                    onUploadFail()
                }
        }
    }
    private fun onUploadFail(){
        Toast.makeText(this,"Image upload failed. Please try agian later", Toast.LENGTH_SHORT).show()
        binding.profileProgressLayout.visibility = View.GONE
    }


    private fun onApply() {
        binding.profileProgressLayout.visibility = View.VISIBLE //chech if its needed here or not
        val username = binding.usernameET.text.toString()
        val email = binding.emailET.text.toString()
        val map = HashMap<String, Any>()
        map[DATA_USER_USERNAME] = username
        map[DATA_USERS_EMAIL] = email
        firebaseDB.collection(DATA_USERS).document(userID!!).update(map)
            .addOnSuccessListener {
                Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e->
                e.printStackTrace()
                binding.profileProgressLayout.visibility = View.GONE
                Toast.makeText(this,"Update failed. Please try again", Toast.LENGTH_SHORT).show()
            }

    }

    private fun populateInfo() {
        binding.profileProgressLayout.visibility = View.VISIBLE
        firebaseDB.collection(DATA_USERS).document(userID!!).get()
            .addOnSuccessListener { snapshpot->
                val user = snapshpot.toObject(User::class.java)
                binding.usernameET.setText(user?.username,TextView.BufferType.EDITABLE)
                binding.emailET.setText(user?.email,TextView.BufferType.EDITABLE)
                binding.profileProgressLayout.visibility = View.GONE
                imageURL = user?.imageUrl
                imageURL?.let {
                    binding.photoIV.loadUrl(user?.imageUrl, R. drawable.logo)
                }
            }
            .addOnFailureListener { e->
                e.printStackTrace()
            }
    }

    companion object{
        fun newIntent(context: Context)= Intent(context, ProfileActivity::class.java)
    }
}