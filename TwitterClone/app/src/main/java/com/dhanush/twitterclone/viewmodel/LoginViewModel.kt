package com.dhanush.twitterclone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(): ViewModel() {
    internal val firebaseAuth = FirebaseAuth.getInstance()

    private val _loginResult = MutableLiveData<Boolean>()
    var statusError = MutableLiveData<String?>()
    var authFailure = MutableLiveData<Exception>()
    val loginResult :LiveData<Boolean> get() = _loginResult
    private val _authenticated  = MutableLiveData<Boolean>()
    val authenticated: LiveData<Boolean> get() = _authenticated
    val firebaseAuthListener: FirebaseAuth.AuthStateListener=
        FirebaseAuth.AuthStateListener {
            val user = it.currentUser?.uid
            _authenticated.value = user!=null
        }
    fun loginUser(email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                _loginResult.value = it.isSuccessful
                it.exception?.localizedMessage?.let {
                    statusError.value = it
                }
            }
            .addOnFailureListener {
                authFailure.value = it
            }

    }
//    fun getcurrentuser():FirebaseUser?{
//        return authRepository.geCurrentUser()
//    }
}