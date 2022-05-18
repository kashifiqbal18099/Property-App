package com.fyp.propertydealerapp.model

data class Message(
    var from: String? = "",
    val message: String = "",
    val messageId: String = "",
    val seen: Boolean = false,
    val time: Long = 0,
    val type: String? = ""
)
