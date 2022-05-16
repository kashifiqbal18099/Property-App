package com.fyp.propertydealerapp.activities.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.activities.dashboard.AdminDashboard
import com.fyp.propertydealerapp.activities.dashboard.UserDashboard
import com.fyp.propertydealerapp.databinding.ActivityLoginBinding
import com.fyp.propertydealerapp.util.ClickHandlers
import com.fyp.propertydealerapp.viewmodels.LoginViewModel
import com.kashif.veterinarypharmacy.base.BaseActivity


class LoginActivity : BaseActivity<ActivityLoginBinding>()  , ClickHandlers {
    lateinit var loginViewModel : LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_login)

        dataBinding?.clickHandlers =this
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        dataBinding?.loginViewmodel = loginViewModel
    }



    override val layoutRes: Int
        get() = R.layout.activity_login

    override fun onClick(view: View?) {
        customProgressDialog?.show();
        loginViewModel.signInUser().observe(this) {
            customProgressDialog?.dismiss()
            if(it!=null){
               /* var userDao:UserDao?
                val db = ApplicationDatabase.getInstance(application)
                userDao  = db?.userDao()
                userDao?.insert(it)*/


                if(it.isAdmin){
                    var intent = Intent(LoginActivity@this,AdminDashboard::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    var intent = Intent(LoginActivity@this,UserDashboard::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            else{
                showSnackbar(message = "Email or Password is incorrect",resources.getColor(R.color.red));
            }
        }


    }


}