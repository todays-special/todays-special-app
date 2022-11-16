package com.example.app.RecAdapter

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(EndCook::class), version = 1, exportSchema = false)
abstract class EndDatabase: RoomDatabase(){
    abstract fun EndDao(): EndDao

    companion object{
        private var INSTANCE: EndDatabase? = null
        fun getInstance(context: Context): EndDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    EndDatabase::class.java,
                    "memo_database")
                    .build()
            }
            return INSTANCE as EndDatabase
        }
    }

}