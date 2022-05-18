package com.fyp.propertydealerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Comments(
    var comment:String= "",
    var createdAt:String = "",
    var userName:String = "",
):Parcelable{
    fun convertTimeStamptoDate(timeStamp:String): String {
        var finalDate = getDate(timeStamp.toLong(),"dd/MM/yyyy hh:mm:ss.SSS")

        return  finalDate!!
    }

    fun getDate(milliSeconds: Long, dateFormat: String?): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(milliSeconds)
        return formatter.format(calendar.getTime())
    }
}
