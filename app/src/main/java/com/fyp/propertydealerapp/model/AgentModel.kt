package com.fyp.propertydealerapp.model

import com.google.firebase.firestore.PropertyName

data class AgentModel(
    var id:String="",
    var firstName:String = "",
    var lastName:String  = "",
    var email:String  = "",
    @get: PropertyName("isAdmin") @set: PropertyName("isAdmin")  var isAdmin:Boolean = false,
    var phoneNumber:String  = "",
    var password:String  = "",

    )
