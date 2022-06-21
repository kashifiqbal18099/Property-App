package com.fyp.propertydealerapp.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class TasksModel(
    var propertyType:String="",
    var propertySize:String="",
    var userId:String="",
    var propertyNumber:String = "",
    var propertyOwnerName:String="",
    var propertyOwnerPhone:String="",
    var completetionDate:String="",
    var status:String="Pending",
    var taskId:String = "",
    var taskDetails:String = "",
    var imageUrl:String = "",
    var comments:List<Comments> = ArrayList<Comments>()

):BaseObservable(),Parcelable{

}
