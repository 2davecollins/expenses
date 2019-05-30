package com.davec.expenses.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExpenseRepository
    val allExpenses : LiveData<List<Expense>>

    init {
        val expenseDao = AppDatabase.getDatabase(application,viewModelScope).expenseDao()
        repository = ExpenseRepository(expenseDao)
        allExpenses = repository.allExpenses
    }

    fun insert(expense: Expense) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(expense)
    }

    fun delete(expense:Expense) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(expense)
    }




}