package com.fyp.propertydealerapp.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class TasksModel(
    var propertyType:String="",
    var propertySize:String="",
    var userId:String="",
    var userName:String = "",
    var propertyNumber:String = "",
    var propertyOwnerName:String="",
    var propertyOwnerPhone:String="",
    var completetionDate:String="",
    var status:String="Pending",
    var taskId:String = "",
    var taskDetails:String = "",
    var imageUrl:String = "",
    var propertyPrice:String="",
    var propertyArea:String="",

    var propertyPupose:String="",
    var bedRooms:String="0",
    var bathRooms:String="0",
    var diningRooms:String="0",
    var serventRooms:String="0",


    var propertyAddress:String="",
    var comments:List<Comments> = ArrayList<Comments>()

):BaseObservable(),Parcelable{

}
