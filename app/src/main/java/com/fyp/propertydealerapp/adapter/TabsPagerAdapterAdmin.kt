package com.fyp.propertydealerapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.fyp.propertydealerapp.fragment.CompletedTasksAdminFragment
import com.fyp.propertydealerapp.fragment.PendingTaskFragmentAdmin



class TabsPagerAdapterAdmin(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    override fun getItem(index: Int): Fragment {
        when (index) {
            0 ->             // Top Rated fragment activity
                return PendingTaskFragmentAdmin()
            1 ->             // Games fragment activity
                return CompletedTasksAdminFragment()
        }
        return   PendingTaskFragmentAdmin()
    }

    override fun getCount(): Int {
        // get item count - equal to number of tabs
        return 2
    }
}