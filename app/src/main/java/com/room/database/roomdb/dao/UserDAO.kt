package com.room.database.roomdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.room.database.roomdb.model.UserModel

@Dao
interface UserDAO {

    @Insert
    fun insertUser(userModel: UserModel)

    @Delete
    fun deleteUser(userModel: UserModel)

    @Update
    fun updateUser(userModel: UserModel)

    @Query("SELECT * FROM table_user ORDER BY id DESC") //DESC là từ lớn đên bé, ASC là từ bé đến lớn
    fun getAllUser() : List<UserModel>
}