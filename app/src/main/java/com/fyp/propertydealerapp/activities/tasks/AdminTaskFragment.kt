package com.fyp.propertydealerapp.activities.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.adapter.TabsPagerAdapterAdmin
import com.fyp.propertydealerapp.base.BaseFramnet
import com.fyp.propertydealerapp.databinding.AdminTaskItemBinding
import com.fyp.propertydealerapp.databinding.FragmentAdminTaskBinding
import com.google.android.material.tabs.TabLayout

class AdminTaskFragment : BaseFramnet<FragmentAdminTaskBinding>() {

    private var mAdapterAdmin: TabsPagerAdapterAdmin? = null
    override fun OnCreateView() {

        mAdapterAdmin =  TabsPagerAdapterAdmin(activity?.supportFragmentManager);

        dataBinding?.tablayout?.addTab( dataBinding?.tablayout?.newTab()?.setText("Pending")!!)
        dataBinding?.tablayout?.addTab( dataBinding?.tablayout?.newTab()?.setText("Completed")!!)
        dataBinding?.tablayout?.setTabGravity(TabLayout.GRAVITY_FILL);
        mAdapterAdmin  = TabsPagerAdapterAdmin(activity?.supportFragmentManager)

        dataBinding?.pager?.adapter  = mAdapterAdmin


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

    override fun getlayout(): Int {
       return  R.layout.fragment_admin_task
    }


}