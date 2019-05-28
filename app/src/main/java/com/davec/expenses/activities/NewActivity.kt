package com.davec.expenses.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.davec.expenses.R

class NewActivity : AppCompatActivity() {

    private lateinit var newTitleView: EditText
    private lateinit var newMenuView: EditText
    private lateinit var newPriceView: EditText
    private lateinit var newWhenView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        newTitleView=findViewById(R.id.etNewTitle)
        newMenuView=findViewById(R.id.etNewNote)
        newPriceView=findViewById(R.id.etNewPrice)
        newWhenView=findViewById(R.id.etNewWhen)


        val btnAdd=findViewById<Button>(R.id.bAdd)

        btnAdd.setOnClickListener {
            val replyIntent=Intent()
            if (
                TextUtils.isEmpty(newTitleView.text) ||
                TextUtils.isEmpty(newMenuView.text) ||
                TextUtils.isEmpty(newPriceView.text) ||
                TextUtils.isEmpty(newWhenView.text)
            ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }else {
                val title = newTitleView.text.toString()
            }
        }

    }
}
