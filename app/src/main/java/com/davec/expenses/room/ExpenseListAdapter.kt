package com.davec.expenses.room

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.davec.expenses.R
import kotlinx.android.synthetic.main.list_item.view.*

class ExpenseListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var expenses = emptyList<Expense>() // Cached copy of words

    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleItemView : TextView = itemView.findViewById(R.id.txvTitle)
        val noteItemView : TextView = itemView.findViewById(R.id.txvNote)
        val priceItemView : TextView = itemView.findViewById(R.id.txvPrice)
        val dateItem :TextView = itemView.findViewById(R.id.txvWhen)
       // val imgDelete :ImageView = itemView.findViewById(R.id.ivRowDelete)
       // val imgEdit : ImageView = itemView.findViewById(R.id.ivRowEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val itemView = inflater.inflate(R.layout.list_item, parent, false)
        return ExpenseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val current = expenses[position]
        holder.titleItemView.text = current.shop
        holder.noteItemView.text = current.description
        holder.dateItem.text = current.date.toString()
        holder.priceItemView.text = current.price.toString()

    }

    internal fun setExpenses(expenses: List<Expense>) {
        this.expenses = expenses
        notifyDataSetChanged()
    }

    override fun getItemCount() = expenses.size
}