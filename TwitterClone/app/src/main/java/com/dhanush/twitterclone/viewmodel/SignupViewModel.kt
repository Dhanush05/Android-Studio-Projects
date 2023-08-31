package com.dhanush.twitterclone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhanush.twitterclone.model.DATA_USERS
import com.dhanush.twitterclone.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupViewModel:ViewModel() {
    internal val firebaseAuth = FirebaseAuth.getInstance()
    private val _authenticated  = MutableLiveData<Boolean>()
    val authComplete = MutableLiveData<Boolean>()
    val signUpStatus = MutableLiveData<Boolean>()
    val statusError = MutableLiveData<Exception>()
    val signUpError = MutableLiveData<Task<AuthResult>>()
    private val firebaseDB = FirebaseFirestore.getInstance()

    val authenticated: LiveData<Boolean> get() = _authenticated
    val firebaseAuthListener: FirebaseAuth.AuthStateListener=
        FirebaseAuth.AuthStateListener {
            val user = it.currentUser?.uid
            _authenticated.value = user!=null
        }
    fun signUpuser(email:String,username: String, password: String){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                authComplete.value = true
                signUpStatus.value = it.isSuccessful
                signUpError.value = it
                if(it.isSuccessful){
                    val user= User(email,username,"", arrayListOf(), arrayListOf())
                    firebaseDB.collection(DATA_USERS).document(firebaseAuth.uid!!).set(user)
                }
            }
            .addOnFailureListener {
                authComplete.value = false
                statusError.value = it
            }
    }

}