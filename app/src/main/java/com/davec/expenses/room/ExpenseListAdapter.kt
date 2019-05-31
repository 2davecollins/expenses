package com.davec.expenses.room

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.davec.expenses.R
import com.davec.expenses.activities.NewActivity


class ExpenseListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var expenses = emptyList<Expense>() // Cached copy of words

    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val locItemView : TextView = itemView.findViewById(R.id.txvLoc)
        val descItemView : TextView = itemView.findViewById(R.id.txvDesc)
        val totalItemView : TextView = itemView.findViewById(R.id.txvTotal)
        val dateinItemView :TextView = itemView.findViewById(R.id.txvDateIn)

        val imgDelete :ImageView = itemView.findViewById(R.id.ivRowDelete)
        val imgEdit : ImageView= itemView.findViewById(R.id.ivRowEdit)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val itemView = inflater.inflate(R.layout.list_item, parent, false)
        return ExpenseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val current = expenses[position]
        holder.locItemView.text = current.loc
        holder.descItemView.text = current.desc
        holder.dateinItemView.text = current.date_in
        holder.totalItemView.text = current.total.toString()

    }

    internal fun setExpenses(expenses: List<Expense>) {
        this.expenses = expenses
        notifyDataSetChanged()
    }





    override fun getItemCount() = expenses.size
}