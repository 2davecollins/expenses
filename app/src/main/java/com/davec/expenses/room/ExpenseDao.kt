package com.davec.expenses.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    @Query("SELECT * FROM user_expense")
    fun getAllExpenses(): LiveData<List<Expense>>

    @Query("SELECT * FROM user_expense WHERE id= :expenseId")
    fun getExpense(expenseId: Int): LiveData<Expense>

    @Update
    suspend fun updateExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("DELETE FROM user_expense")
    suspend fun deleteAll()

}