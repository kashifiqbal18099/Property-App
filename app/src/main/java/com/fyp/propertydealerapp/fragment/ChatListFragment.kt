package com.fyp.propertydealerapp.fragment

import android.view.View
import android.widget.Toast
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.adapter.MessageAdapter
import com.fyp.propertydealerapp.base.BaseFramnet
import com.fyp.propertydealerapp.databinding.FragmentChatListBinding
import com.fyp.propertydealerapp.model.ChatUser
import com.fyp.propertydealerapp.model.FeedItem
import com.fyp.propertydealerapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore

class ChatListFragment : BaseFramnet<FragmentChatListBinding>(){

    var mAuth:FirebaseAuth  = FirebaseAuth.getInstance()
    var chatUsers: ArrayList<ChatUser>? = ArrayList()
    var firebBaseDb:DatabaseReference  = FirebaseDatabase.getInstance().reference
    private val db = FirebaseFirestore.getInstance()
   lateinit var messageAdapter: MessageAdapter


    override fun OnCreateView() {
        messageAdapter  = MessageAdapter(this, this.chatUsers);
        dataBinding?.chatListFragment?.adapter  = messageAdapter
        getAllChats()
    }

    fun getAllChats(){
        customProgressDialog?.show()
        firebBaseDb.child("Message_Feed").child(mAuth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                customProgressDialog?.dismiss()

                if (dataSnapshot.hasChildren()) {
                    chatUsers!!.clear()
                    for (dataSnapshot1 in dataSnapshot.children) {
                        val feedItem: FeedItem = dataSnapshot1.getValue(FeedItem::class.java) as FeedItem
                        if (feedItem != null) {
                            GetDataBinding()?.noMessageFoundTxt?.visibility  = View.GONE
                            GetDataBinding()?.chatListFragment?.visibility  = View.VISIBLE
                            getData(feedItem)

                        }
                    }
                } else {
                    GetDataBinding()?.noMessageFoundTxt?.visibility  = View.VISIBLE
                    GetDataBinding()?.chatListFragment?.visibility  = View.GONE
                }

                messageAdapter.notifyDataSetChanged();
            }

            override fun onCancelled(error: DatabaseError) {
               customProgressDialog?.dismiss()
            }

        })
    }

    fun getData(feedItem: FeedItem) {
        customProgressDialog?.show()
        db.collection("User").document(feedItem.user_id!!).get().addOnCompleteListener { task ->

            customProgressDialog?.dismiss()
            if (task.isSuccessful) {
                val document = task.result
                if (document != null) {
                    // chatUsers.clear();
                    val user = document.toObject(User::class.java)
                    if (chatUsers!!.size <= 0) {
                        chatUsers?.add(ChatUser(feedItem, user))
                    } else {
                        if (!CheckUserInList(chatUsers!!, user!!)) {
                           chatUsers?.add(ChatUser(feedItem, user))
                        }
                    }

                    messageAdapter.notifyDataSetChanged()

                }
            }
        }.addOnFailureListener { e ->
            customProgressDialog?.dismiss()
            Toast.makeText(requireActivity(), "Try Again", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun getlayout(): Int {
      return  R.layout.fragment_chat_list
    }


    fun CheckUserInList(chatUsers: ArrayList<ChatUser>, chatUser1: User): Boolean {
        for (i in chatUsers.indices) {
            if (chatUsers[i].user?.id.equals(chatUser1.id)) {
                return true
            }
        }
        return false
    }
}


