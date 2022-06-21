package com.fyp.propertydealerapp.activities.dashboard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.activities.ProfileActivity
import com.fyp.propertydealerapp.activities.agents.AddAgentsActivity
import com.fyp.propertydealerapp.activities.chat.ChatActivity
import com.fyp.propertydealerapp.activities.onboarding.LoginActivity
import com.fyp.propertydealerapp.activities.tasks.AddTasksActivity
import com.fyp.propertydealerapp.activities.tasks.AdminTaskActivity
import com.fyp.propertydealerapp.adapter.TabsPagerAdapterAdmin
import com.fyp.propertydealerapp.adapter.TabsPagerAdapterUser
import com.fyp.propertydealerapp.databinding.ActivityDashboardBinding
import com.fyp.propertydealerapp.databinding.AdminNavHeaderLayoutBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.kashif.veterinarypharmacy.base.BaseActivity

class UserDashboard : BaseActivity<ActivityDashboardBinding>() , NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {
    private var mAdaterUser: TabsPagerAdapterUser? = null
    var sharedPrefs: SharedPreferences?=null
    var drawerLayout: DrawerLayout? = null
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    var mAuth:FirebaseAuth  = FirebaseAuth.getInstance()
    var editor: SharedPreferences.Editor?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPrefs = application.getSharedPreferences("Main", Context.MODE_PRIVATE)
        editor = sharedPrefs!!.edit()
        mAdaterUser =  TabsPagerAdapterUser(supportFragmentManager)


        drawerLayout = dataBinding?.myDrawerLayout

        val navHeaderView  =  dataBinding?.adminNavView?.getHeaderView(0)
        val headerBinding  = AdminNavHeaderLayoutBinding.bind(navHeaderView!!)
        headerBinding.name  = sharedPrefs?.getString("userName","")
        headerBinding.userProfile.setOnClickListener {
            startActivity(Intent(this@UserDashboard, ProfileActivity::class.java))
        }
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, com.fyp.propertydealerapp.R.string.nav_open, com.fyp.propertydealerapp.R.string.nav_close)
        setToolbar()

        drawerLayout?.addDrawerListener(this);
        actionBarDrawerToggle?.syncState();
        dataBinding?.adminNavView?.setNavigationItemSelectedListener(this);


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

        setSupportActionBar(dataBinding?.toolBar!!)


        dataBinding?.tablayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                dataBinding?.pager?.currentItem = tab?.position!!
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        dataBinding?.chat?.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java))
        }

    }


    private fun setToolbar() {
        setSupportActionBar(dataBinding?.toolBar)
        dataBinding?.toolBar?.navigationIcon?.mutate().let {
            it?.setTint(resources.getColor(android.R.color.white))
            dataBinding?.toolBar?.navigationIcon  = it

        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(com.fyp.propertydealerapp.R.drawable.ic_menu)
    }

  /*  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       // menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }*/


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
            R.id.nav_logout->{
                editor?.putString("email","")
                editor?.putString("password","")
                editor?.putString("userName","")
                editor?.putBoolean("isAdmin",false)
                mAuth.signOut()
                this.finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }

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

    override val layoutRes: Int
        get() = R.layout.activity_dashboard
}