package com.davec.expenses

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Context.showToast(message: String, duration: Int =Toast.LENGTH_LONG){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()

}

fun Context.logMessage(tag:String,m:String ){
    Log.d(tag,m)
}


private fun Context.checkPersmission(): Boolean {
    return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
        android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
}

private fun Activity.requestPermission(){
    ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE, CAMERA), Constants.PERMISSION_REQUEST_CODE)
}

