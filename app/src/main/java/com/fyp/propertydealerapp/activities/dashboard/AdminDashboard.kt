package com.fyp.propertydealerapp.activities.dashboard
import android.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.fyp.propertydealerapp.activities.chat.ChatActivity
import com.fyp.propertydealerapp.databinding.ActivityAdminDashboardBinding
import com.fyp.propertydealerapp.databinding.AdminNavHeaderLayoutBinding
import com.fyp.propertydealerapp.util.ClickHandlers
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.kashif.veterinarypharmacy.base.BaseActivity


class AdminDashboard : BaseActivity<ActivityAdminDashboardBinding>(),ClickHandlers, NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {
     var mAuth:FirebaseAuth  = FirebaseAuth.getInstance()
    var sharedPrefs: SharedPreferences?=null
    var editor: SharedPreferences.Editor?=null


    var drawerLayout: DrawerLayout? = null
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = application.getSharedPreferences("Main", Context.MODE_PRIVATE)

        drawerLayout = dataBinding?.myDrawerLayout

       var navHeaderView  =  dataBinding?.adminNavView?.getHeaderView(0)
        var headerBinding  = AdminNavHeaderLayoutBinding.bind(navHeaderView!!)
        editor = sharedPrefs!!.edit()
        headerBinding.name  = sharedPrefs?.getString("userName","")
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, com.fyp.propertydealerapp.R.string.nav_open, com.fyp.propertydealerapp.R.string.nav_close)
        setToolbar()
        drawerLayout?.addDrawerListener(this);
        actionBarDrawerToggle?.syncState();
        dataBinding?.clickHandlers  = this



        dataBinding?.chat?.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java))
        }
    }

    override val layoutRes: Int
        get() = com.fyp.propertydealerapp.R.layout.activity_admin_dashboard

    override fun onClick(view: View?) {
       /* if(view?.id == com.fyp.propertydealerapp.R.id.add_agent_btn){
            startActivity(Intent(AdminDashboard@this,AddAgentsActivity::class.java))
        }
        else if(view?.id== com.fyp.propertydealerapp.R.id.add_post_btn){
            startActivity(Intent(AdminDashboard@this,AddTasksActivity::class.java))
        }
        else{
            startActivity(Intent(AdminDashboard@this,AdminTaskActivity::class.java))
        }*/
    }


    private fun setToolbar() {
        setSupportActionBar(dataBinding?.toolBar)
        dataBinding?.toolBar?.navigationIcon?.mutate().let {
            it?.setTint(resources.getColor(R.color.white))
            dataBinding?.toolBar?.navigationIcon  = it

        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(com.fyp.propertydealerapp.R.drawable.ic_menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

      /*
        return if (actionBarDrawerToggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)*/
        return when (item.itemId) {
            android.R.id.home -> {
                //Open left menu
                drawerLayout!!.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        var fragment: Fragment? = null

        when (menuItem.getItemId()) {

        }

        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .add(com.fyp.propertydealerapp.R.id.navFragment, fragment)
                .commit()
            menuItem.setChecked(true)
            supportActionBar?.setTitle(menuItem.getTitle())
            drawerLayout!!.closeDrawers()
        }

        return true

    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

    }

    override fun onDrawerOpened(drawerView: View) {

    }

    override fun onDrawerClosed(drawerView: View) {

    }

    override fun onDrawerStateChanged(newState: Int) {

    }


    /* override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // menuInflater.inflate(R.menu.toolbar_menu, menu);
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
     }*/
}