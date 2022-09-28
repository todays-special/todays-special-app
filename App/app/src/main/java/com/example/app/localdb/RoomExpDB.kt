package com.example.app.localdb

import androidx.room.ColumnInfo
import androidx.room.Entity

//@Entity(tableName = "roomexpDb")
@Entity(tableName = "internalExpDb", primaryKeys = ["name", "expiration", "keyValue"])
class RoomExpDB {
//    @PrimaryKey(autoGenerate = true) //PK로 지정된 no의 값이 insert된다면 없을 때 자동으로 증가된 숫자값을 넣어줌
//    @ColumnInfo
//    var no: Int? = null
    @ColumnInfo
    var name: String = ""
    @ColumnInfo
    var count: String = ""
    @ColumnInfo(name = "expiration") //코드에서는 exp으로 쓰지만 attribute는 expiration로 생성됨
    var exp: String = ""
    @ColumnInfo(defaultValue = "default")
    var keyValue: String = ""

    //이렇게 쓰는 이유 -> 데이터 넣을때 한줄한줄 넣는것보다 생성자로 넣으면 한줄로 끝남
    constructor(name:String, count:String, exp: String, keyValue: String){
        this.name = name
        this.count = count
        this.exp = exp
        this.keyValue = keyValue
    }
}
