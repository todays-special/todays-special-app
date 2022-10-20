package com.example.app.localdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(RoomExpDB::class), version = 1, exportSchema = true)
// version을 써줘야하는 이유는 업데이트하면서 룸 테이블이 바뀔 수 있기 때문
abstract class RoomHelper : RoomDatabase() {
    //    autoMigrations = [
//    AutoMigration (from = 1, to = 2)
//    ]
    abstract fun roomExpDao(): RoomExpDAO // 추상메서드. 룸이 알아서 다 해줌
}