package com.davec.expenses.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

public class ExpenseRepository(private val expenseDao: ExpenseDao) {


    val allExpenses: LiveData<List<Expense>> = expenseDao.getAllExpenses()

    suspend fun insert(expense: Expense) {
        expenseDao.insert(expense)
    }

    suspend fun delete(expense: Expense){
        expenseDao.deleteExpense(expense)
    }


}