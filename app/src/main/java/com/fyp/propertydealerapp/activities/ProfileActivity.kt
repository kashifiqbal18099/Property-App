package com.fyp.propertydealerapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.databinding.ActivityProfileBinding
import com.fyp.propertydealerapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kashif.veterinarypharmacy.base.BaseActivity

class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    var auth:FirebaseAuth  = FirebaseAuth.getInstance()
    var db:FirebaseFirestore  = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding?.user  = User()
        getUserData()

    }

  fun getUserData(){
      customProgressDialog?.show();
        db.collection("User").document(auth.currentUser?.uid!!).addSnapshotListener{value, error ->
            customProgressDialog?.dismiss()
            if(error==null){
                var user  = value?.toObject(User::class.java)

                dataBinding?.user  = user
            }
            else{
                showSnackbar(error.message!!,resources.getColor(R.color.red))
            }
        }
    }




    override val layoutRes: Int
        get() = R.layout.activity_profile
}