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
import com.dhanush.twitterclone.databinding.ActivityLoginBinding
import com.dhanush.twitterclone.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.buttonLogin.setOnClickListener {
            onLogin()
        }
        binding.signupTextView.setOnClickListener {
            goToSignUp(it)
        }
        viewModel.authenticated.observe(this) {
            if (it) {
                startActivity(HomeActivity.newIntent(this))
                finish()
            }
        }
        setTextChangeListener(binding.emailET,binding.emailTIL)
        setTextChangeListener(binding.passwordET,binding.passwordTIL)
        binding.loginProgressLayout.setOnTouchListener { view, motionEvent -> true }
    }
    private fun goToSignUp(view:View){
        startActivity(SignupActivity.newIntent(this))
        finish()
    }

    private fun setTextChangeListener(et: EditText, til: TextInputLayout){
        et.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                til.isErrorEnabled = false
            }
            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }
    private fun onLogin() {
        var proceed = true
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
            binding.loginProgressLayout.visibility = View.VISIBLE
            viewModel.loginUser(binding.emailET.text.toString(), binding.passwordET.text.toString())
            viewModel.loginResult.observe(this) {
                if(!it){
                    binding.loginProgressLayout.visibility = View.GONE
                    Toast.makeText(this@LoginActivity, "Login error ${viewModel.statusError}",Toast.LENGTH_LONG).show()
                }
            }
            viewModel.authFailure.observe(this){
                it.printStackTrace()
                binding.loginProgressLayout.visibility = View.GONE
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

    companion object {
        fun newIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}