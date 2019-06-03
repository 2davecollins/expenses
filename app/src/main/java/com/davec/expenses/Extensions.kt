package com.davec.expenses

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun Context.showToast(message: String, duration: Int =Toast.LENGTH_LONG){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()

}

fun Context.logMessage(tag:String,m:String ){
    Log.d(tag,m)
}

