package com.fyp.propertydealerapp.activities.dashboard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.activities.onboarding.LoginActivity
import com.fyp.propertydealerapp.adapter.TabsPagerAdapterAdmin
import com.fyp.propertydealerapp.adapter.TabsPagerAdapterUser
import com.fyp.propertydealerapp.databinding.ActivityDashboardBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.kashif.veterinarypharmacy.base.BaseActivity

class UserDashboard : BaseActivity<ActivityDashboardBinding>() {
    private var mAdaterUser: TabsPagerAdapterUser? = null
    var sharedPrefs: SharedPreferences?=null
    var mAuth:FirebaseAuth  = FirebaseAuth.getInstance()
    var editor: SharedPreferences.Editor?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPrefs = application.getSharedPreferences("Main", Context.MODE_PRIVATE)
        editor = sharedPrefs!!.edit()
        mAdaterUser =  TabsPagerAdapterUser(supportFragmentManager)

        dataBinding?.tablayout?.addTab( dataBinding?.tablayout?.newTab()?.setText("Pending")!!)
        dataBinding?.tablayout?.addTab( dataBinding?.tablayout?.newTab()?.setText("Completed")!!)
        dataBinding?.tablayout?.setTabGravity(TabLayout.GRAVITY_FILL);
       // mAdaterUser  = TabsPagerAdapterAdmin(supportFragmentManager)

        dataBinding?.pager?.adapter  = mAdaterUser


        dataBinding?.pager?.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                dataBinding?.tablayout!!
            )
        )


        dataBinding?.tablayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                dataBinding?.pager?.currentItem = tab?.position!!
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if(id==R.id.logout){
            editor?.putString("email","")
            editor?.putString("password","")
            mAuth.signOut()
            this.finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }


        return super.onOptionsItemSelected(item);
    }

    override val layoutRes: Int
        get() = R.layout.activity_dashboard
}