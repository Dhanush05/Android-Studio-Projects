package com.dhanush.twitterclone.view.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dhanush.twitterclone.databinding.ActivitySignupBinding
import com.dhanush.twitterclone.model.DATA_USERS
import com.dhanush.twitterclone.model.User
import com.dhanush.twitterclone.viewmodel.LoginViewModel
import com.dhanush.twitterclone.viewmodel.SignupViewModel
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {
    private lateinit var viewModel: SignupViewModel
    private lateinit var binding : ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)

        binding.buttonSignup.setOnClickListener {
            onSignUp()
        }
        binding.loginTextView.setOnClickListener {
            goToLogin()
        }
        viewModel.authenticated.observe(this) {
            if (it) {
                startActivity(HomeActivity.newIntent(this))
                finish()
            }
        }
        setTextChangeListener(binding.usernameET,binding.usernameTIL)
        setTextChangeListener(binding.emailET,binding.emailTIL)
        setTextChangeListener(binding.passwordET,binding.passwordTIL)
        binding.signupProgressLayout.setOnTouchListener { v, event -> true }
    }
    private fun setTextChangeListener(etname:EditText, til: TextInputLayout){
        etname.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                til.isErrorEnabled = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun goToLogin() {
        startActivity(LoginActivity.newIntent(this))
        finish()
    }

    private fun onSignUp() {
        var proceed = true
        if(binding.usernameET.text.isNullOrEmpty()){
            binding.usernameTIL.error = "Username is required"
            binding.usernameTIL.isErrorEnabled = true
            proceed = false
        }
        if(binding.emailET.text.isNullOrEmpty()){
            binding.emailTIL.error = "e-mail is required"
            binding.emailTIL.isErrorEnabled = true
            proceed = false
        }
        if(binding.passwordET.text.isNullOrEmpty()){
            binding.passwordTIL.error = "Password is required"
            binding.passwordTIL.isErrorEnabled = true
            proceed = false
        }
        if(proceed){
            binding.signupProgressLayout.visibility = View.VISIBLE
            val emailEntered = binding.emailET.text.toString()
            val passwordEntered = binding.passwordET.text.toString()
            val usernameEntered = binding.usernameET.text.toString()
            viewModel.signUpuser(emailEntered,usernameEntered,passwordEntered)
            viewModel.authComplete.observe(this){
                if(it){
                    if(viewModel.signUpStatus.value == false){
                        Toast.makeText(this, "Signup Error ${viewModel.signUpError.value?.exception?.localizedMessage}",
                            Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    viewModel.statusError.value?.printStackTrace()
                    binding.signupProgressLayout.visibility = View.GONE
                }
            }
        }

    }
    override fun onStart() {
        super.onStart()
        viewModel.firebaseAuth.addAuthStateListener(viewModel.firebaseAuthListener)
    }

    override fun onStop() {
        super.onStop()
        viewModel.firebaseAuth.removeAuthStateListener(viewModel.firebaseAuthListener)
    }

    companion object{
        fun newIntent(context: Context)= Intent(context, SignupActivity::class.java)
    }
}