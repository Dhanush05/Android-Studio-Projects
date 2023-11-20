package com.dhanush.runningapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dhanush.runningapp.R
import com.dhanush.runningapp.db.RunDAO
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var runDao: RunDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("RunDao", "RunDao: ${runDao.hashCode()}")
    }
}