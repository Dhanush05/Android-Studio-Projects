package com.dhanush.runningapp.ui.fragments

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dhanush.runningapp.R
import com.dhanush.runningapp.databinding.FragmentRunBinding
import com.dhanush.runningapp.databinding.FragmentSetupBinding
import com.dhanush.runningapp.other.TrackingUtility
import com.dhanush.runningapp.ui.viewmodels.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class RunFragment : Fragment(){
    lateinit var binding: FragmentRunBinding
    private val viewModel : MainViewModel by viewModels()
       override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRunBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener{
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
        checkPermissions()
    }

    private fun checkPermissions() {
        if(TrackingUtility.hasLocationPermissions(requireContext())){
            return
        }
        else{
            requestPermissions()
        }
    } private fun requestPermissions(){
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    private val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        permissions->
        when{
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
                    && permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true ->{
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                            //************not needed now but may be needed in future********************
                           // requestBackgroundLocationPermission()
                        }
                        else{
                            //continue as usual
                        }
                    }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) ||
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) ->{
                        //permission got denied. Show ratinale and request agian
                        showRationaleDialog()
            }
            else->{
                //denied permissions permanently
                Snackbar.make(requireView(),"Go to app settings for adding required permissions",Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun showRationaleDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Location Permissions required")
            .setMessage("This app requires location permissions to work properly")
            .setPositiveButton("Ok"){_,_ ->
                requestPermissions()
            }
            .setNegativeButton("Cancel"){dialogInterface,_ ->
                dialogInterface.cancel()
            }
            .show()
    }

    private fun requestBackgroundLocationPermission() {
        backgroundLocationPermissionRequest.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    }

    private val backgroundLocationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestPermission()){granted->
        if(granted){
            //continue as usual
        }
        else{
            Snackbar.make(requireView(),"Please grant background permissions",Snackbar.LENGTH_SHORT).show()
        }
    }

}