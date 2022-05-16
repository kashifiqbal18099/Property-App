package com.fyp.propertydealerapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.fyp.propertydealerapp.fragment.CompletedTasksAdminFragment
import com.fyp.propertydealerapp.fragment.CompletedTasksUserFragment
import com.fyp.propertydealerapp.fragment.PendingTaskFragmentAdmin
import com.fyp.propertydealerapp.fragment.PendingTaskFragmentUser


class TabsPagerAdapterUser(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    override fun getItem(index: Int): Fragment {
        when (index) {
            0 ->             // Top Rated fragment activity
                return PendingTaskFragmentUser()
            1 ->             // Games fragment activity
                return CompletedTasksUserFragment()
        }
        return   PendingTaskFragmentUser()
    }

    override fun getCount(): Int {
        // get item count - equal to number of tabs
        return 2
    }
}