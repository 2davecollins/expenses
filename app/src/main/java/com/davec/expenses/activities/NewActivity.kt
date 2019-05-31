package com.davec.expenses.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.davec.expenses.R
import java.util.*

class NewActivity : AppCompatActivity() {

    private lateinit var newLocView: EditText
    private lateinit var newDescView: EditText
    private lateinit var newTotalView: EditText
    private lateinit var newDateinView: EditText

    private val EXTRA_LOC = "loc"
    private val EXTRA_DESC = "desc"
    private val EXTRA_TOTAL = "total"
    private val EXTRA_DATEIN ="date_in"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        newLocView=findViewById(R.id.etNewLoc)
        newDescView=findViewById(R.id.etNewDesc)
        newTotalView=findViewById(R.id.etNewTotal)
        newDateinView=findViewById(R.id.etNewDateIn)

        setTitle("Add Expense")


        val calander = Calendar.getInstance()
        val year = calander.get(Calendar.YEAR)
        val month = calander.get(Calendar.MONTH)
        val day = calander.get(Calendar.DAY_OF_MONTH)

        var dateString = String.format("%02d-%02d-%d",day,month+1,year)

        newDateinView.setText(dateString)


        newDateinView.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this@NewActivity,DatePickerDialog.OnDateSetListener{
                view,year,monthOfYear,dayOfMonth ->
                dateString = String.format("%02d-%02d-%d",dayOfMonth,(monthOfYear+1),year)
                //newDateinView.setText(""+ dayOfMonth + "-"+(monthOfYear+1)+"-"+year)
                newDateinView.setText(dateString)
            },year,month,day)
            datePickerDialog.show()
        }

        val btnSnap = findViewById<Button>(R.id.btnSnap)

        btnSnap.setOnClickListener {
            val cameraIntent = Intent(this, CameraActivity::class.java)
            startActivity(cameraIntent)
        }


        val btnAdd = findViewById<Button>(R.id.bAdd)
        btnAdd.setOnClickListener {

            Log.d("NewActivity", "Btn Add Clicked")
            val  replyIntent = Intent()
            if (
                TextUtils.isEmpty(newLocView.text) ||
                TextUtils.isEmpty(newDescView.text) ||
                TextUtils.isEmpty(newTotalView.text) ||
                TextUtils.isEmpty(newDateinView.text)
            ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }else {
                val loc = newLocView.text.toString()
                val desc = newDescView.text.toString()
                val total = newTotalView.text.toString()
                val datein = newDateinView.text.toString()

                replyIntent.putExtra(EXTRA_LOC, loc)
                replyIntent.putExtra(EXTRA_DESC, desc)
                replyIntent.putExtra(EXTRA_TOTAL, total)
                replyIntent.putExtra(EXTRA_DATEIN, datein)

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

    }
}
