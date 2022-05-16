package com.fyp.propertydealerapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.activities.onboarding.LoginActivity
import com.fyp.propertydealerapp.activities.dashboard.AdminDashboard
import com.fyp.propertydealerapp.activities.dashboard.UserDashboard
import com.fyp.propertydealerapp.databinding.ActivitySplashBinding
import com.fyp.propertydealerapp.viewmodels.LoginViewModel
import com.kashif.veterinarypharmacy.base.BaseActivity


class SplashActivity :BaseActivity<ActivitySplashBinding>() {
   lateinit var loginViewModel:LoginViewModel
    /** Duration of wait  */
    private val SPLASH_DISPLAY_LENGTH:Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel  = ViewModelProvider(this).get(LoginViewModel::class.java)
       // setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable { /* Create an Intent that will start the Menu-Activity. */

            if(firebaseAuth.currentUser!=null){


                loginViewModel.getCurrentUser(firebaseAuth?.currentUser!!.uid).observe(this){
                    if(it!=null){
                        if(it.isAdmin){
                            val mainIntent = Intent(this@SplashActivity, AdminDashboard::class.java)
                            this@SplashActivity.startActivity(mainIntent)
                            this@SplashActivity.finish()
                        }
                        else{
                            val mainIntent = Intent(this@SplashActivity, UserDashboard::class.java)
                            this@SplashActivity.startActivity(mainIntent)
                            this@SplashActivity.finish()
                        }
                    }
                    else{
                        val mainIntent = Intent(this@SplashActivity, LoginActivity::class.java)
                        this@SplashActivity.startActivity(mainIntent)
                        this@SplashActivity.finish()
                    }
                }

            }
            else{
                val mainIntent = Intent(this@SplashActivity, LoginActivity::class.java)
                this@SplashActivity.startActivity(mainIntent)
                this@SplashActivity.finish()
            }

        }, SPLASH_DISPLAY_LENGTH)

    }

    override val layoutRes: Int
        get() = R.layout.activity_splash



}