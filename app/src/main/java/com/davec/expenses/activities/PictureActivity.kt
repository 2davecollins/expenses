package com.davec.expenses.activities

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.davec.expenses.Constants
import com.davec.expenses.RotateBitmap
import com.davec.expenses.imageFromBitmap
import com.davec.expenses.showToast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage


class PictureActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.davec.expenses.R.layout.activity_picture)

        val imageView=findViewById<ImageView>(com.davec.expenses.R.id.imageView)
        val resultText=findViewById<TextView>(com.davec.expenses.R.id.textView)

        val uriString=intent.getStringExtra("ImageSrc")
        val uri=Uri.parse(uriString)
        resultText.setText(uriString)

        Log.d("imgsrc", uriString)


        var bmImg=BitmapFactory.decodeFile(uriString)
        bmImg=RotateBitmap(bmImg, 90.00f)
        imageView.setImageBitmap(bmImg)

        val btnReturn=findViewById<Button>(com.davec.expenses.R.id.btnReturn)
        btnReturn.setOnClickListener {
            val intent=Intent(this@PictureActivity, NewActivity::class.java)
            startActivity(intent)
        }

        val btnRead=findViewById<Button>(com.davec.expenses.R.id.btnRead)
        btnRead.setOnClickListener {
            val fir: FirebaseVisionImage?=imageFromBitmap(bmImg)
            // imageView.setImageBitmap(bmImg)
            //var ans : String = recogniseText(fir!!)

            readText(fir!!)

            //readText()

            //resultText.setText(ans)
        }

        imageView.setOnLongClickListener {
            showToast("Pic touched")
            return@setOnLongClickListener true
        }
//        imageView.setOnClickListener {
//            val cropIntent=Intent("com.android.camera.action.CROP")
//            //indicate image type and Uri
//            cropIntent.setDataAndType(uri, "image/*")
//            //set crop properties
//            cropIntent.putExtra("crop", "true")
//            //indicate aspect of desired crop
//            cropIntent.putExtra("aspectX", 1)
//            cropIntent.putExtra("aspectY", 1)
//            //indicate output X and Y
//            cropIntent.putExtra("outputX", 256)
//            cropIntent.putExtra("outputY", 256)
//            //retrieve data on return
//            cropIntent.putExtra("return-data", true)
//            //start the activity - we handle returning in onActivityResult
//            startActivityForResult(cropIntent, Constants.PIC_CROP)
//        }

        fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
            super.onActivityResult(requestCode, resultCode, intentData)

            Log.d(
                MainActivity.TAG,
                requestCode.toString() + ": " + resultCode + "Activity RESULT  " + Activity.RESULT_OK
            )

            if (requestCode == Constants.PIC_CROP && resultCode == Activity.RESULT_OK) {//get the returned data
                val extras=intentData?.getExtras()
                //get the cropped bitmap
                bmImg=extras?.getParcelable("data")
                imageView.setImageBitmap(bmImg)

            }

        }


    }

    fun readText(image: FirebaseVisionImage) {
        val detector=FirebaseVision.getInstance()
            .onDeviceTextRecognizer

        var sb = ArrayList<String>()
        sb.add("dave")
        val resultText=findViewById<TextView>(com.davec.expenses.R.id.textView)

        val result=detector.processImage(image)
            .addOnSuccessListener {

                val blocks =it.textBlocks
                resultText.setText(it.text)


                for (block in blocks) {
                    val blockText = block.text
                    val blockConfidence = block.confidence
                    val blockRecognizedLanguages = block.recognizedLanguages
                    val blockFrame = block.boundingBox
                    for (word in block.lines) {
                        sb.add(word.text)
                    }
                }
                val answer = sb.joinToString { " " }


            }
            .addOnFailureListener {
                Log.d("Firebase Text Failure", "Text Recognition Failed")

                resultText.setText("Firebase Reading Suscess")
            }
    }


}
