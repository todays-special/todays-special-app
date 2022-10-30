package com.example.app.localdb

import androidx.room.*

@Dao
interface RoomExpDAO {
    @Query("select * from internalExpDb")
    fun getAll(): List<RoomExpDB>

    @Query("DELETE FROM internalExpDb")
    fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE) //insert로 메모가 들어왔는데 이미 값이 있는 경우면 덮어쓰기가 됨.(update개념)
    fun insert(value: RoomExpDB)

    @Update
    fun update(value: RoomExpDB)

    @Delete
    fun delete(value: RoomExpDB)
}