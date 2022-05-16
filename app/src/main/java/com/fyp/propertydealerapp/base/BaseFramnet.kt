package com.fyp.propertydealerapp.base


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.fyp.propertydealerapp.util.CustomProgressDialog
import com.google.android.material.snackbar.Snackbar


abstract class BaseFramnet<DB : ViewDataBinding?> : Fragment() {
    var dataBinding: DB? = null
    protected var customProgressDialog: CustomProgressDialog? = null
    protected var mContext: Context? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, getlayout(), container, false)
        customProgressDialog = CustomProgressDialog(requireActivity()!!)
        mContext = activity
        OnCreateView()
        return dataBinding?.root
    }

    abstract fun OnCreateView()
    abstract fun getlayout(): Int
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