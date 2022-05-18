package com.fyp.propertydealerapp.activities.chat

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.base.GenericAdapter
import com.fyp.propertydealerapp.databinding.ActivityUserListBinding
import com.fyp.propertydealerapp.databinding.UserListItemBinding
import com.fyp.propertydealerapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kashif.veterinarypharmacy.base.BaseActivity

class UserListActivity : BaseActivity<ActivityUserListBinding>() {
    var sharedPrefs: SharedPreferences?=null
     var isAdmin:Boolean  = false
    var mAuth:FirebaseAuth  = FirebaseAuth.getInstance()
    lateinit  var genericAdapter:GenericAdapter<User,UserListItemBinding>
    var db:FirebaseFirestore  = FirebaseFirestore.getInstance()
    var userList:MutableList<User>  =ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = getSharedPreferences("Main", Context.MODE_PRIVATE)
        isAdmin  = sharedPrefs?.getBoolean("isAdmin",false)!!

        initAdapter()

        if(isAdmin){
            customProgressDialog?.show()
            db.collection("User").whereEqualTo("isAdmin",false).addSnapshotListener { value, error ->
                customProgressDialog?.dismiss()
             if(error==null){
                 for(doument in value?.documents!!){
                     var user  = doument.toObject(User::class.java)
                     userList.add(user!!)
                 }

                 genericAdapter.notifyDataSetChanged()

             }
                else{
                 showSnackbar("No User Found",resources.getColor(R.color.red))
             }
            }
        }
        else{
            customProgressDialog?.show()
            db.collection("User").whereNotEqualTo("id",mAuth.currentUser?.uid!!).addSnapshotListener { value, error ->
                customProgressDialog?.dismiss()
                if(error==null){
                    for(doument in value?.documents!!){
                        var user  = doument.toObject(User::class.java)
                        userList.add(user!!)
                    }

                    genericAdapter.notifyDataSetChanged()

                }
                else{
                    showSnackbar("No User Found",resources.getColor(R.color.red))
                }

            }

        }



    }


   fun initAdapter(){
        genericAdapter = object : GenericAdapter<User,UserListItemBinding>(this,userList){
            override val layoutResId: Int
                get() = R.layout.user_list_item

            override fun onBindData(model: User, position: Int, dataBinding: UserListItemBinding) {

                dataBinding.user = model


            }

            override fun onItemClick(model: User, position: Int, dataBinding: UserListItemBinding) {

                var intent  = Intent(this@UserListActivity,ChatMessageActivity::class.java)
                intent.putExtra("visitUserId", model.id);
                intent.putExtra("userName", model.firstName  + " "  +  model.lastName);
                finish()
                startActivity(intent)
            }

        }
       dataBinding?.userListRec?.adapter  =genericAdapter
    }
    override val layoutRes: Int
        get() = R.layout.activity_user_list
}