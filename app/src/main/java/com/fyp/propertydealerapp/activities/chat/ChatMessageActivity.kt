package com.fyp.propertydealerapp.activities.chat

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.adapter.ChatAdapter
import com.fyp.propertydealerapp.databinding.FragmentChat2Binding
import com.fyp.propertydealerapp.model.FeedItem
import com.fyp.propertydealerapp.model.Message
import com.fyp.propertydealerapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.kashif.veterinarypharmacy.base.BaseActivity

class ChatMessageActivity : BaseActivity<FragmentChat2Binding>(){

    var rUser: User? = null
    var rUserId = ""
    var rUserName = ""
    var mainUserId = ""
   lateinit var messageAdapter: ChatAdapter
    var messages: ArrayList<Message>? = ArrayList()
    var rootReference: DatabaseReference? = FirebaseDatabase.getInstance().reference
    var mAuth:FirebaseAuth  = FirebaseAuth.getInstance()
    var sharedPrefs: SharedPreferences?=null
    var mUserName:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPrefs = getSharedPreferences("Main", Context.MODE_PRIVATE)
        mainUserId  = mAuth.currentUser?.uid!!
        rUserName = intent.getStringExtra("userName")!!
        mUserName  = sharedPrefs?.getString("userName","")!!
        rUserId = intent.getStringExtra("visitUserId")!!
        messageAdapter  =  ChatAdapter(this.messages, this, this.mainUserId)
        dataBinding?.toolBar?.setTitle(rUserName)
        getUsers()
        dataBinding?.messageList?.setHasFixedSize(true)
        dataBinding?.messageList?.adapter  = messageAdapter
        dataBinding?.messageList?.scrollToPosition(this.messages?.size!! - 1)

        dataBinding?.imageViewSend?.setOnClickListener{
            val message_push_id: String = rootReference?.child("messages")?.push()?.key!!
            if(!dataBinding?.editText?.text?.toString().isNullOrEmpty()){
                sendMessage(dataBinding?.editText?.text?.toString(),message_push_id)
            }


        }

        fetchAllMessages()
    }


  fun  fetchAllMessages(){
      rootReference!!.child("messages").child(mainUserId).child(rUserId).orderByChild("time")
          .addChildEventListener(object : ChildEventListener {
              override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                  var message: Message?=null
                  if (dataSnapshot!!.exists()) {
                      message  = dataSnapshot.getValue(Message::class.java)
                     messages?.add(message!!)
                     messageAdapter.notifyItemInserted(messages?.size!! - 1)
                     dataBinding?.messageList?.scrollToPosition(messages?.size!! - 1)
                  }
              }

              override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
              override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
              override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
              override fun onCancelled(databaseError: DatabaseError) {}
          })
    }

    fun  getUsers(){
        FirebaseFirestore.getInstance().collection("User").document(rUserId).get()
            .addOnCompleteListener { task ->
                var document: DocumentSnapshot?=null
                if (task.isSuccessful && task.result.also { document = it } != null) {
                    rUser = document?.toObject(User::class.java)
                }
            }.addOnFailureListener { e ->
            Toast.makeText(this, "Try Again", Toast.LENGTH_LONG).show()
            Log.e("tag", " = = chat error = = " + e.message)
        }
    }

    fun sendMessage(message: String?, message_push_id: String) {
        if (!TextUtils.isEmpty(message)) {
            val message_text_body = HashMap<String, Any?>()
            message_text_body["seen"] = false
            message_text_body["time"] = ServerValue.TIMESTAMP
            message_text_body["from"] = mainUserId
            message_text_body["type"] = "text"
            message_text_body["messageId"] = message_push_id
            message_text_body["message"] = message
            message_text_body["user_id"] =  rUserId
            message_text_body["user_name"]   = rUserName

            dataBinding?.editText?.setText("")
            val messageBodyDetails = HashMap<String, Any>()
            messageBodyDetails["messages/$mainUserId/$rUserId/$message_push_id"] = message_text_body
            messageBodyDetails["messages/$rUserId/$mainUserId/$message_push_id"] = message_text_body
            rootReference!!.updateChildren(
                messageBodyDetails
            ) { databaseError, databaseReference ->
                if (databaseError != null) {
                    Log.e("Sending message", databaseError.message)
                }
            }





            val messageItemReference = FirebaseDatabase.getInstance().reference.child("Message_Feed").child(mainUserId).child(rUserId)


            var message_text_body1 = HashMap<String, Any?>()
            message_text_body1["last_message"] = message
            message_text_body1["last_message_read"] = false
            message_text_body1["last_message_time"] = ServerValue.TIMESTAMP
            message_text_body1["type"] = "text"
            message_text_body1["user_id"] = rUserId
            message_text_body1["user_name"]   = rUserName

            messageItemReference.setValue(message_text_body1)





            val feedItemReference = FirebaseDatabase.getInstance().reference.child("Message_Feed").child(rUserId).child(mainUserId)


            var message_text_body2 = HashMap<String, Any?>()
            message_text_body2["last_message"] = message
            message_text_body2["last_message_read"] = false
            message_text_body2["last_message_time"] = ServerValue.TIMESTAMP
            message_text_body2["type"] = "text"
            message_text_body2["user_id"] = mainUserId
            message_text_body2["user_name"]   = mUserName

            feedItemReference.setValue(message_text_body2)


        }
    }



    override val layoutRes: Int
        get() =R.layout.fragment_chat2


}