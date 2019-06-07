package com.davec.expenses

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText


fun Context.showToast(message: String, duration: Int=Toast.LENGTH_LONG) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

}

fun Context.logMessage(tag: String, m: String) {
    Log.d(tag, m)
}


fun Context.checkPersmission(): Boolean {
    return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
        this,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED)
}

fun Activity.requestPermission() {
    ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE, CAMERA), Constants.PERMISSION_REQUEST_CODE)
}

fun RotateBitmap(bitMap: Bitmap, angle: Float): Bitmap {
    var matrix= Matrix()
    matrix.postRotate(angle)

    return Bitmap.createBitmap(
        bitMap, 0, 0, bitMap.width, bitMap.height, matrix, true
    )

}

fun imageFromBitmap(bitmap: Bitmap): FirebaseVisionImage? {

    return FirebaseVisionImage.fromBitmap(bitmap)
}

fun recogniseText(image: FirebaseVisionImage) : String{
    val detector = FirebaseVision.getInstance()
        .onDeviceTextRecognizer

    var res : String = "Awaiting"



    val result = detector.processImage(image)
        .addOnSuccessListener {
            Log.d("Firebase Text ","Text Recognition Success")

            res = "Firebase recognition Success"

        }
        .addOnFailureListener {
            Log.d("Firebase Text Failure","Text Recognition Failed")
            res = "Firebase recognition Failed"
        }

    return res




}



fun processTextBlocks(result: FirebaseVisionText){

}

