package com.dhanush.runningapp.ui.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dhanush.runningapp.R
import com.dhanush.runningapp.databinding.FragmentTrackingBinding
import com.dhanush.runningapp.other.constants.ACTION_START_OR_RESUME_SERVICE
import com.dhanush.runningapp.services.TrackingService
import com.dhanush.runningapp.ui.viewmodels.MainViewModel
import com.google.android.gms.maps.GoogleMap
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.Console

@AndroidEntryPoint
class TrackingFragment : Fragment() {
    private val viewModel : MainViewModel by viewModels()
    private var map: GoogleMap? = null // this is the map object
    private var binding : FragmentTrackingBinding? = null // this is the binding object

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("CheckFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrackingBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.mapView?.onCreate(savedInstanceState)
        binding?.mapView?.getMapAsync {
            map = it
        }
        binding?.btnToggleRun?.setOnClickListener {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
    }
    private fun sendCommandToService(action: String) {
        val intent = Intent(requireActivity(), TrackingService::class.java).also {
            it.action = action
            requireActivity().startService(it)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireActivity().startForegroundService(intent)
        } else {
            requireActivity().startService(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding?.mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding?.mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding?.mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding?.mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        //the code might work without this line too - if code give null pointer exception then remove this line
        binding?.mapView?.onDestroy() //check where it is getting destroyed - in onStop or onDestroy
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding?.mapView?.onSaveInstanceState(outState)
    }



}