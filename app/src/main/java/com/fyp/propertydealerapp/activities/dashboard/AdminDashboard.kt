package com.fyp.propertydealerapp.activities.dashboard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.activities.agents.AddAgentsActivity
import com.fyp.propertydealerapp.activities.chat.ChatActivity
import com.fyp.propertydealerapp.activities.chat.UserListActivity
import com.fyp.propertydealerapp.activities.onboarding.LoginActivity
import com.fyp.propertydealerapp.activities.tasks.AddTasksActivity
import com.fyp.propertydealerapp.activities.tasks.AdminTaskActivity
import com.fyp.propertydealerapp.databinding.ActivityAdminDashboardBinding
import com.fyp.propertydealerapp.util.ClickHandlers
import com.google.firebase.auth.FirebaseAuth
import com.kashif.veterinarypharmacy.base.BaseActivity

class AdminDashboard : BaseActivity<ActivityAdminDashboardBinding>(),ClickHandlers {
     var mAuth:FirebaseAuth  = FirebaseAuth.getInstance()
    var sharedPrefs: SharedPreferences?=null
    var editor: SharedPreferences.Editor?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = application.getSharedPreferences("Main", Context.MODE_PRIVATE)
        editor = sharedPrefs!!.edit()
        setSupportActionBar(dataBinding?.toolBar!!)
        dataBinding?.clickHandlers  = this

        dataBinding?.chat?.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java))
        }
    }

    override val layoutRes: Int
        get() = R.layout.activity_admin_dashboard

    override fun onClick(view: View?) {
        if(view?.id == R.id.add_agent_btn){
            startActivity(Intent(AdminDashboard@this,AddAgentsActivity::class.java))
        }
        else if(view?.id==R.id.add_post_btn){
            startActivity(Intent(AdminDashboard@this,AddTasksActivity::class.java))
        }
        else{
            startActivity(Intent(AdminDashboard@this,AdminTaskActivity::class.java))
        }
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
            editor?.putString("userName","")
            editor?.putBoolean("isAdmin",false)
            mAuth.signOut()
            this.finish()
            startActivity(Intent(this,LoginActivity::class.java))
        }


        return super.onOptionsItemSelected(item);
    }
}