package com.fyp.propertydealerapp.repostiories

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.fyp.propertydealerapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class LoginRepository(mAuth: FirebaseAuth, db: FirebaseFirestore) {
   private var mAuth: FirebaseAuth? = null;
   private var db: FirebaseFirestore? = null;



    init {

        this.mAuth = mAuth;
        this.db = db;
    }

   private  var userStream: MutableLiveData<User>? = MutableLiveData<User>()


     fun signInUser(email: String, password: String) : MutableLiveData<User>? {
         mAuth?.signInWithEmailAndPassword(email, password)?.addOnSuccessListener {
             mAuth?.currentUser?.uid?.let { it1 ->
                 val listenerRegistration =  db?.collection("User")?.document(it1)?.addSnapshotListener { value, error ->
                     if(error==null){
                         var user =   value?.toObject(User::class.java)
                         userStream?.value = user
                     }
                     else{
                         userStream?.value=null
                     }


                 }

             }

         }?.addOnFailureListener {
             print(
                 it.message
             )
             userStream?.value=null
         }

         return  userStream

     }

    fun getCurrentUser(userId:String) : MutableLiveData<User>?{
        db?.collection("User")?.document(userId)?.addSnapshotListener { value, error ->

            if(error==null){
                userStream?.value  = value?.toObject(User::class.java)


            }
            else{
                userStream?.value  =null
            }
        }
        return  userStream
    }
}