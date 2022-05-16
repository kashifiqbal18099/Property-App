package com.fyp.propertydealerapp.model

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


interface UserDao {
    @Query("SELECT * FROM CART")
    fun getAll(): LiveData<List<User?>?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User?)
}