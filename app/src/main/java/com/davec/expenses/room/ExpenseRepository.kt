package com.davec.expenses.room

import androidx.lifecycle.LiveData

public class ExpenseRepository(private val expenseDao: ExpenseDao) {


    val allExpenses: LiveData<List<Expense>> = expenseDao.getAllExpenses()

    suspend  fun update(expense:Expense) {
        expenseDao.updateExpense(expense)
    }


    suspend fun insert(expense: Expense) {
        expenseDao.insertExpense(expense)
    }

    suspend fun delete(expense: Expense){
        expenseDao.deleteExpense(expense)
    }


}