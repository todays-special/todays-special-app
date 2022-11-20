package com.example.app.RecAdapter

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "EndTable")
data class EndCook (
    @PrimaryKey
    var id:Int?,

    @ColumnInfo(name = "Ingredient")
    var ingerdient: String,
    @ColumnInfo(name = "Used")
    var usedIn: String
)