package com.fyp.propertydealerapp.model

data class FeedItem(
    var last_message: String? ="",
    var last_message_time: Long = 0,
    var message_read: Boolean = false,
    var user_id: String? = null,
    var user_name: String? = null,
    var user_thumb_image: String? = null,
)
