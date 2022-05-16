package com.fyp.propertydealerapp.activities.tasks

import android.os.Bundle
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.adapter.TabsPagerAdapterAdmin
import com.fyp.propertydealerapp.databinding.ActivityAdminTaskBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.kashif.veterinarypharmacy.base.BaseActivity


class AdminTaskActivity :BaseActivity<ActivityAdminTaskBinding>() {
    private var mAdapterAdmin: TabsPagerAdapterAdmin? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAdapterAdmin =  TabsPagerAdapterAdmin(supportFragmentManager);

        dataBinding?.tablayout?.addTab( dataBinding?.tablayout?.newTab()?.setText("Pending")!!)
        dataBinding?.tablayout?.addTab( dataBinding?.tablayout?.newTab()?.setText("Completed")!!)
        dataBinding?.tablayout?.setTabGravity(TabLayout.GRAVITY_FILL);
        mAdapterAdmin  = TabsPagerAdapterAdmin(supportFragmentManager)

        dataBinding?.pager?.adapter  = mAdapterAdmin


        dataBinding?.pager?.addOnPageChangeListener(TabLayoutOnPageChangeListener(dataBinding?.tablayout!!))


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




    override val layoutRes: Int
        get() = R.layout.activity_admin_task
}