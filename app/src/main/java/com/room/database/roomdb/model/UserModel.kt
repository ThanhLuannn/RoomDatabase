package com.room.database.roomdb.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "table_user")
class UserModel (
    var name : String,
    var age : Int,
    var address : String
){
    @PrimaryKey(autoGenerate = true) var id : Long = 0
}