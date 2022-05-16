package com.fyp.propertydealerapp.base

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.fyp.propertydealerapp.model.User
import com.fyp.propertydealerapp.model.UserDao

@Database(entities = [User::class], version = 2)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao?
    companion object {
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null
        fun getInstance(context: Context): ApplicationDatabase? {
            if (INSTANCE == null) {
                synchronized(ApplicationDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ApplicationDatabase::class.java, "userdb.db"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}