package com.room.database.roomdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.room.database.roomdb.dao.UserDAO
import com.room.database.roomdb.model.UserModel

@Database(entities = [UserModel::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao() : UserDAO

    companion object{
        @Volatile
        private var instance : UserDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            "user_database"
            ).allowMainThreadQueries().build()
    }
}