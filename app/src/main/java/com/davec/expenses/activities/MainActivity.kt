package com.davec.expenses.activities

import android.app.Activity
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davec.expenses.Constants
import com.davec.expenses.R
import com.davec.expenses.room.Expense
import com.davec.expenses.room.ExpenseListAdapter
import com.davec.expenses.room.ExpenseViewModel
import com.davec.expenses.showToast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val newActivityRequestCode=1
    private val editActivityRequestCode=2
    private val deleteRequestCode=3
    private lateinit var mExpenseViewModel: ExpenseViewModel


    companion object {
        val TAG: String=MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.davec.expenses.R.layout.activity_main)
        setSupportActionBar(toolbar)


        val recyclerView=findViewById<RecyclerView>(com.davec.expenses.R.id.recyclerview)
        val adapter=ExpenseListAdapter(this)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)





        mExpenseViewModel=ViewModelProviders.of(this).get(ExpenseViewModel::class.java)

        mExpenseViewModel.allExpenses.observe(
            this,
            Observer { expenses ->
                expenses?.let { adapter.setExpenses(it) }
            }
        )

        val helper=ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override// We are not implementing onMove() in this app
                fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override// When the use swipes a word,
                // delete that word from the database.
                fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    if (direction == ItemTouchHelper.LEFT) {
                        val builder=AlertDialog.Builder(this@MainActivity)
                        builder.setTitle("Confim")
                        builder.setMessage("Do You Want To Delete Item?")
                        builder.setPositiveButton("YES") { dialog, which ->
                            showToast("Deleting .....")
                            val position=viewHolder.adapterPosition
                            val myExpense=adapter.getExpenseAtPosition(position)
                            mExpenseViewModel.delete(myExpense)
                        }
                        builder.setNegativeButton("No") { dialog, which ->
                            showToast("Not Deleting")
                            adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                        }
                        val dialog: AlertDialog=builder.create()
                        dialog.show()
                    } else {
                        val i=Intent(this@MainActivity, NewActivity::class.java)
                        val position=viewHolder.adapterPosition
                        val myExpense=adapter.getExpenseAtPosition(position)

                        var total = myExpense.total.toString()
                        var eid=myExpense.id
                        Log.d("Expense pre :",total)


                        i.putExtra(Constants.EXTRA_EDIT, Constants.EXTRA_EDIT)
                        i.putExtra(Constants.EXTRA_LOC, myExpense.loc)
                        i.putExtra(Constants.EXTRA_DESC, myExpense.desc)
                        i.putExtra(Constants.EXTRA_DATEIN, myExpense.date_in)
                        i.putExtra(Constants.EXTRA_TOTAL,total)
                        i.putExtra(Constants.EXTRA_ID, eid)
                        startActivityForResult(i, editActivityRequestCode)
                       // adapter.notifyItemChanged(viewHolder.getAdapterPosition());

                    }
                }
            })


        // Attach the item touch helper to the recycler view
        helper.attachToRecyclerView(recyclerView)


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Create new Expense", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            val i=Intent(this@MainActivity, NewActivity::class.java)

            startActivityForResult(i, newActivityRequestCode)
        }


    }//onCreate


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(com.davec.expenses.R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            com.davec.expenses.R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        Log.d(TAG, requestCode.toString() + ": " + resultCode + "Activity RESULT  " + Activity.RESULT_OK)

        if (requestCode == newActivityRequestCode && resultCode == Activity.RESULT_OK) {

            showToast("AddNew Expense")
            intentData?.let { data ->

                val loc=data.getStringExtra(Constants.EXTRA_LOC)
                val desc=data.getStringExtra(Constants.EXTRA_DESC)
                val total=data.getStringExtra(Constants.EXTRA_TOTAL)
                val date_in=data.getStringExtra(Constants.EXTRA_DATEIN)
                val expenseId=data.getIntExtra(Constants.EXTRA_ID,0)


                val expense=Expense(expenseId, loc, 0, desc, total.toDouble(), date_in, false)
                Log.d(TAG, loc + " : " + desc + " : " + total + " : " + date_in)
                mExpenseViewModel?.insert(expense)

            }

        } else if (requestCode == editActivityRequestCode && resultCode == Activity.RESULT_OK) {

            showToast("Edit Activity called")

            intentData?.let { data ->

                val loc=data.getStringExtra(Constants.EXTRA_LOC)
                val desc=data.getStringExtra(Constants.EXTRA_DESC)
                val total=data.getStringExtra(Constants.EXTRA_TOTAL)
                val date_in=data.getStringExtra(Constants.EXTRA_DATEIN)
                val expenseId=data.getIntExtra(Constants.EXTRA_ID, -1)

                Log.d("Expense after :",total+" id "+expenseId)

                val expense=Expense(expenseId, loc, 0, desc, total.toDouble(), date_in, false)

                mExpenseViewModel?.update(expense)

            }



        } else {
            showToast("Not Saved")


        }
    }


}
