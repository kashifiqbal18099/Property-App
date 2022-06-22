package com.fyp.propertydealerapp.activities.bouns

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.base.GenericAdapter
import com.fyp.propertydealerapp.databinding.ActivityBounsBinding
import com.fyp.propertydealerapp.databinding.AdminTaskItemBinding
import com.fyp.propertydealerapp.databinding.BounsItemBinding
import com.fyp.propertydealerapp.model.Bouns
import com.fyp.propertydealerapp.model.TasksModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kashif.veterinarypharmacy.base.BaseActivity

class BounsActivity : BaseActivity<ActivityBounsBinding>(){

    lateinit var genericAdapter: GenericAdapter<Bouns, BounsItemBinding>
    var list:MutableList<Bouns>  = ArrayList()
    var mAuth:FirebaseAuth  = FirebaseAuth.getInstance()
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_bouns)

        genericAdapter  = object  : GenericAdapter<Bouns,BounsItemBinding>(this,list){
            override val layoutResId: Int
                get() = R.layout.bouns_item

            override fun onBindData(model: Bouns, position: Int, dataBinding: BounsItemBinding) {
              dataBinding.bouns  = model
            }

            override fun onItemClick(model: Bouns, position: Int, dataBinding: BounsItemBinding) {

            }

        }

        dataBinding?.bounsRes?.adapter  = genericAdapter

        getBounses()
    }

    fun getBounses(){
        customProgressDialog?.show()
        db.collection("Bouns").document(mAuth?.currentUser?.uid!!).collection("Months").addSnapshotListener{value,error->
            customProgressDialog?.dismiss()
            if(error==null){
                for(doc in value?.documents!!){
                    var bouns = doc.toObject(Bouns::class.java)
                    list.add(bouns!!)
                }

                genericAdapter.notifyDataSetChanged()
            }
            else{
                showSnackbar("No Data Found",resources.getColor(R.color.red))
            }
        }
    }

    override val layoutRes: Int
        get() = R.layout.activity_bouns
}