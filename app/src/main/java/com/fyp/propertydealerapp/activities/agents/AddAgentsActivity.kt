package com.fyp.propertydealerapp.activities.agents

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.databinding.ActivityAddAgentsBinding
import com.fyp.propertydealerapp.util.ClickHandlers
import com.fyp.propertydealerapp.viewmodels.AgentViewModel
import com.google.firebase.auth.FirebaseAuth
import com.kashif.veterinarypharmacy.base.BaseActivity

class AddAgentsActivity : BaseActivity<ActivityAddAgentsBinding>() , ClickHandlers{

  lateinit  var agentViewModel: AgentViewModel
  var mAuth:FirebaseAuth  = FirebaseAuth.getInstance()
    var sharedPrefs: SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = getSharedPreferences("Main", Context.MODE_PRIVATE)
       setSupportActionBar(dataBinding?.toolBar!!)
        agentViewModel  = ViewModelProvider(this).get(AgentViewModel::class.java)
        dataBinding?.agentViewModel  = agentViewModel
        dataBinding?.clickHandler  = this

    }

    override val layoutRes: Int
    get() = R.layout.activity_add_agents

    override fun onClick(view: View?) {
        if(agentViewModel.isFormValid()){
            customProgressDialog?.show()
            agentViewModel.addAgent(completion = {

                var email =  sharedPrefs?.getString("email","")
                var password  = sharedPrefs?.getString("password","")

                mAuth.signInWithEmailAndPassword(email!!,password!!).addOnSuccessListener {
                    customProgressDialog?.dismiss()
                    showSnackbar("Agent Added",resources.getColor(R.color.green))
                }.addOnFailureListener {
                    customProgressDialog?.dismiss()
                    showSnackbar(it.message!!,resources.getColor(R.color.red))
                }



            }, onError = {
                customProgressDialog?.dismiss()
                showSnackbar(it,resources.getColor(R.color.red))
            } )
        }

    }


}