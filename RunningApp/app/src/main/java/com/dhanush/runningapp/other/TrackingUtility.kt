package com.dhanush.runningapp.other

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat
import pub.devrel.easypermissions.EasyPermissions

object TrackingUtility {
    fun hasLocationPermissions(context: Context): Boolean {
        val fineLocationGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        val coarseLocationGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        val backgroundLocationGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED

        } else {
            true
        }
        return fineLocationGranted && coarseLocationGranted && backgroundLocationGranted
    }
}


//    return if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
//            EasyPermissions.hasPermissions(
//                context,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            )
//        }
//        else {
//            EasyPermissions.hasPermissions(
//                context,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_BACKGROUND_LOCATION
//            )
//        }