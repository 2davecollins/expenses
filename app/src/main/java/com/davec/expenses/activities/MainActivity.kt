package com.davec.expenses.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.davec.expenses.R
import com.davec.expenses.room.AppDatabase
import com.davec.expenses.room.Expense
import com.davec.expenses.room.ExpenseListAdapter
import com.davec.expenses.room.ExpenseViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    private  val REQUEST_CODE = 200
    private var mExpenseViewModel: ExpenseViewModel?=null

    private var db: AppDatabase?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

       db = Room.databaseBuilder(applicationContext,AppDatabase::class.java,"expense_database").build()

        GlobalScope.launch {
            db?.expenseDao()!!.insertExpense(Expense(0,"a",1,"b",100.00,"28-05-2019",false))
            var data = db?.expenseDao()!!.getAllExpenses()

            Log.d("Daaaaaaaaaaaaa",data.toString())
            


        }

        val recyclerView=findViewById<RecyclerView>(R.id.recyclerview)
        val adapter=ExpenseListAdapter(this)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)

        mExpenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel::class.java)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Create new Expense", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            val i=Intent(this@MainActivity, NewActivity::class.java)
            startActivityForResult(i, REQUEST_CODE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        Log.d("replyIntent", requestCode.toString()+": "+resultCode+"Activity RESULT  "+Activity.RESULT_OK)

        if ( resultCode == Activity.RESULT_OK) {


            intentData?.let { data ->

                val loc = data.getStringExtra("loc")
                val desc = data.getStringExtra("desc")
                val total = data.getStringExtra("total")
                //val date_in = data.getStringExtra(("data_in"))
                val date_in = "12-12-2010"

                val expense = Expense(0,loc,0,desc,total.toDouble(),date_in,false)
                Log.d("Expense ",loc+" : "+desc+" : "+total+" : "+date_in)

                mExpenseViewModel?.insert(expense)
               // ExpenseViewModel.insert(expense)
            }

        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }


}
