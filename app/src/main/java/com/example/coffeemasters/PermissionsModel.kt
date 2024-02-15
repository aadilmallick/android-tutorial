package com.example.coffeemasters

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.security.Permission

class PermissionsModel(private var context: android.app.Activity) {
    private val permissionsArray = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
    )

    fun checkPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissions() {
        val permissionsNotGranted = mutableListOf<String>()
        permissionsArray.forEach { permission ->
            if (ActivityCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNotGranted.add(permission)
            }
            if (permissionsNotGranted.isNotEmpty()) {
                this.requestPermissions(permissionsNotGranted.toTypedArray())
            }
        }


    }

    fun requestPermissions(permissionsArray: Array<String>) {
        ActivityCompat.requestPermissions(
            context,
            permissionsArray,
            0
        )
    }

}


