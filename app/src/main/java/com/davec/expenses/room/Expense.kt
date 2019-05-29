package com.davec.expenses.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_expense")
data class Expense(

    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,

    @ColumnInfo(name = "shop") val shop: String?,

    @ColumnInfo(name = "qty") val qty:Int?,

    @ColumnInfo(name = "description") val description: String?,

    @ColumnInfo(name = "price") val price: Double?,

    @ColumnInfo(name ="date") val date: Long?

)