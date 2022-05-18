package com.fyp.propertydealerapp.activities.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.databinding.ActivityForgotPasswordBinding
import com.fyp.propertydealerapp.util.ClickHandlers
import com.google.firebase.auth.FirebaseAuth
import com.kashif.veterinarypharmacy.base.BaseActivity

class ForgotPassword : BaseActivity<ActivityForgotPasswordBinding>(),ClickHandlers {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding?.clickHandlers  = this

    }



    override val layoutRes: Int
        get() = R.layout.activity_forgot_password

    override fun onClick(view: View?) {

        var id   = view?.id!!

       if(id==R.id.newpassword){
           resetPassword()
       }

    }

    fun resetPassword(){
      if(dataBinding?.eMail?.text.isNullOrBlank()){
          showSnackbar("Please enter Email",resources.getColor(R.color.red))
      }
        else{
            customProgressDialog?.show()

          FirebaseAuth.getInstance().sendPasswordResetEmail(dataBinding?.eMail?.text.toString()).addOnSuccessListener {
              customProgressDialog?.dismiss()
              dataBinding?.eMail?.setText("")
              showSnackbar("Email sent to your email !",resources.getColor(R.color.green))

          }.addOnFailureListener{
              customProgressDialog?.dismiss()
              showSnackbar(it.message!!,resources.getColor(R.color.red))
          }
        }
    }
}