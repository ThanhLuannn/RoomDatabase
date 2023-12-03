package com.room.database.roomdb.`interface`

import com.room.database.roomdb.model.UserModel

interface ItemUser {

    fun item(userModel: UserModel,status_Update : Boolean)
}