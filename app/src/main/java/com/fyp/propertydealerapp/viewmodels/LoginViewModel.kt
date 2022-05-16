package com.fyp.propertydealerapp.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fyp.propertydealerapp.model.User
import com.fyp.propertydealerapp.repostiories.LoginRepository
import com.fyp.propertydealerapp.util.ObservableViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class LoginViewModel(application: Application) :AndroidViewModel(application) {

    var email: ObservableField<String>?=ObservableField<String>("")
     var password:ObservableField<String>?=ObservableField<String>("")
    var sharedPrefs: SharedPreferences?=null
    var editor: SharedPreferences.Editor?=null

    var loginRepository: LoginRepository? = null

    init {
        sharedPrefs = application.getSharedPreferences("Main", Context.MODE_PRIVATE)
        editor = sharedPrefs!!.edit()
        var mAuth =  FirebaseAuth.getInstance();
        var db  = FirebaseFirestore.getInstance();
        loginRepository   = LoginRepository(mAuth = mAuth,db=db);
    }
    fun signInUser():MutableLiveData<User> {
        editor?.putString("email",email?.get()!!)
        editor?.putString("password",password?.get()!!)
        editor?.commit()
         return  loginRepository?.signInUser(email = email?.get()!!, password = password?.get()!!)!!
    }




     fun getCurrentUser(userId:String):MutableLiveData<User>{
         return  loginRepository?.getCurrentUser(userId)!!
     }



}