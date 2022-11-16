package com.example.app.RecAdapter

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EndDao {
    @androidx.room.Query("SELECT * FROM EndTable ORDER BY id DESC")
    fun getAll(): LiveData<List<EndCook>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(End : EndCook)

    @Update
    fun update(End: EndCook)

    @Delete
    fun delete(End: EndCook)

    @androidx.room.Query("DELETE FROM EndTable")
    fun deleteAll()
}