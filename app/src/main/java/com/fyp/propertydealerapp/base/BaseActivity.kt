package com.kashif.veterinarypharmacy.base

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.fyp.propertydealerapp.util.CustomProgressDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


abstract class BaseActivity<DB : ViewDataBinding?> : AppCompatActivity() {
    var dataBinding: DB? = null
    protected var mContext: Context? = null
    protected var customProgressDialog: CustomProgressDialog? = null
   lateinit var firebaseAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth  = FirebaseAuth.getInstance()
        mContext = this
        dataBinding = DataBindingUtil.setContentView(this, layoutRes)
        customProgressDialog  = CustomProgressDialog(this)
    }

    @get:LayoutRes
    abstract val layoutRes: Int

    fun GetDataBinding(): DB? {
        return dataBinding
    }

   fun showSnackbar(message:String,@ColorInt color:Int){
     var snackBar  =   Snackbar.make(dataBinding?.root!!, message, Snackbar.LENGTH_SHORT)
      var snackBarView  = snackBar.view
      snackBarView.setBackgroundColor(color)

       snackBar.show()

    }
}