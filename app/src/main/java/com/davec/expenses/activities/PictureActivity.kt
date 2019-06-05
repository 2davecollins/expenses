package com.davec.expenses.activities

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.davec.expenses.R

class PictureActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)

        val imageView = findViewById<ImageView>(R.id.imageView)

        val uriString = intent.getStringExtra("ImageSrc")
        val uri = Uri.parse(uriString)

        Log.d("imgsrc",uriString)


        val bmImg = BitmapFactory.decodeFile(uriString)
        imageView.setImageBitmap(bmImg)

        val btnReturn = findViewById<Button>(R.id.btnReturn)

        btnReturn.setOnClickListener {
            val intent=Intent(this@PictureActivity, NewActivity::class.java)
            startActivity(intent)
        }



    }
}
