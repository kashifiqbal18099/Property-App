package com.fyp.propertydealerapp.activities.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.databinding.ActivityChat2Binding
import com.fyp.propertydealerapp.fragment.ChatListFragment
import com.kashif.veterinarypharmacy.base.BaseActivity

class ChatActivity : BaseActivity<ActivityChat2Binding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_chat2)

        var chatListFragment  = ChatListFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container,chatListFragment)
       // transaction.addToBackStack("CHATLISTFRAGMENT")
        transaction.commit()

        dataBinding?.startChat?.setOnClickListener {
            startActivity(Intent(this,UserListActivity::class.java))
        }
    }

    override val layoutRes: Int
        get() = R.layout.activity_chat2
}