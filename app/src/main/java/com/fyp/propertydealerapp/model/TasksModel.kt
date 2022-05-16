package com.fyp.propertydealerapp.model

import androidx.databinding.BaseObservable

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

):BaseObservable(){

}
