package com.davec.expenses.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "user_expense")
data class Expense(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    //@ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),

    @NotNull
    @ColumnInfo(name = "loc") val loc: String?,

    @ColumnInfo(name = "qty") val qty:Int?,

    @NotNull
    @ColumnInfo(name = "desc") val desc: String?,

    @NotNull
    @ColumnInfo(name = "total") val total: Double?,

    @NotNull
    @ColumnInfo(name ="date_in") val date_in: String?,

    @ColumnInfo(name = "uploaded") val uploaded : Boolean = false

)