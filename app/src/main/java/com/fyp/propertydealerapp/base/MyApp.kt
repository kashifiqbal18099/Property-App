package com.fyp.propertydealerapp.base

import android.app.Application
import android.content.ContentValues
import android.text.TextUtils
import java.util.*


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        intance = this
    }



    companion object {
        @get:Synchronized
        var intance: MyApp? = null
    }
}