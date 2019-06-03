package com.davec.expenses.room

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.davec.expenses.R
import kotlinx.android.synthetic.main.list_item.view.*


class ExpenseListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder>() {

    private val inflater: LayoutInflater=LayoutInflater.from(context)
    private var expenses=emptyList<Expense>() // Cached copy of words

    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var locItemView: TextView=itemView.findViewById(R.id.txvLoc)
        var descItemView: TextView=itemView.findViewById(R.id.txvDesc)
        var totalItemView: TextView=itemView.findViewById(R.id.txvTotal)
        var dateinItemView: TextView=itemView.findViewById(R.id.txvDateIn)
        var currentPos: Int = 0
        var imgDelete: ImageView= itemView.findViewById(R.id.ivRowDelete)

        init {
//            itemView.ivRowDelete.setOnClickListener {
//               /* TODO*/
//                Log.d("imageDelete", "Delete clicked" + getExpenseAtPosition(currentPos))
//
//            }
//            itemView.ivRowEdit.setOnClickListener {
//                /* TODO */
//                Log.d("Edit", "Edit item at" + getExpenseAtPosition(currentPos))
//            }
        }

    }
    interface OnDeleteClickListener {
        fun onItemRemoveClick(position : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val itemView=inflater.inflate(R.layout.list_item, parent, false)
        return ExpenseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val current=expenses[position]
        holder.locItemView.text=current.loc
        holder.descItemView.text=current.desc
        holder.dateinItemView.text=current.date_in
        holder.totalItemView.text=current.total.toString()
        holder.currentPos=position

    }

    fun getExpenseAtPosition(position: Int): Expense {
        return expenses.get(position)
        notifyDataSetChanged()
    }

    fun setExpenses(expenses: List<Expense>) {
        this.expenses=expenses
        notifyDataSetChanged()
    }


    override fun getItemCount()=expenses.size
}