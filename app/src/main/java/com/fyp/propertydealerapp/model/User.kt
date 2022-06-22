package com.fyp.propertydealerapp.model

import android.os.Parcelable
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id:String?,
    @ColumnInfo(name ="email")
    @get: PropertyName("email") @set: PropertyName("email") var email:String = "",
    @ColumnInfo(name ="password")
    @get: PropertyName("password") @set: PropertyName("password") var   password:String  = "",
    @ColumnInfo(name ="isAdmin")
    @get: PropertyName("isAdmin") @set: PropertyName("isAdmin") var isAdmin: Boolean  =false,
    @ColumnInfo(name ="firstName")
    @get: PropertyName("firstName") @set: PropertyName("firstName") var firstName:String  ="",
    @ColumnInfo(name ="lastName")
    @get: PropertyName("lastName") @set: PropertyName("lastName") var lastName: String  ="",
    @ColumnInfo(name ="phoneNumber")
    @get: PropertyName("phoneNumber") @set: PropertyName("phoneNumber") var phoneNumber: String  = "",
    @ColumnInfo(name ="phoneNumber")
    @get: PropertyName("salary") @set: PropertyName("salary") var salary: String  = "",


    ):Parcelable{
    constructor() : this("", "", "", false, "", "","","")
}
