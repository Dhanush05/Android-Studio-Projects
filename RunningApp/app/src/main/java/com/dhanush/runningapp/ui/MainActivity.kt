package com.dhanush.runningapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dhanush.runningapp.R
import com.dhanush.runningapp.databinding.ActivityMainBinding
import com.dhanush.runningapp.db.RunDAO
import com.dhanush.runningapp.other.constants.ACTION_SHOW_TRACKING_FRAGMENT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    var navHostFragment : NavHostFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        navHostFragment  = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHostFragment!!.navController)
        navigateToTrackingFragmentIfNeeded(intent)
        navHostFragment!!.findNavController()
            .addOnDestinationChangedListener{_,destination, _ ->
                when(destination.id){
                    R.id.settingsFragment,R.id.runFragment,R.id.statisticsFragment ->
                        binding.bottomNavigationView.visibility = View.VISIBLE
                    else -> binding.bottomNavigationView.visibility = View.GONE
                }
            }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentIfNeeded(intent)
    }
    private fun navigateToTrackingFragmentIfNeeded(intent: Intent?) {
        if(intent?.action == ACTION_SHOW_TRACKING_FRAGMENT){
            navHostFragment!!.findNavController().navigate(R.id.action_global_trackingFragment)
        }
    }
}



































































































