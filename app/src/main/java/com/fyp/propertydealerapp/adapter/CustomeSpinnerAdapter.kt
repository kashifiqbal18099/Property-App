package com.fyp.propertydealerapp.adapter


import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.model.User


class CustomeSpinnerAdapter(context: Activity, resource: Int, objects: MutableList<User>, textviewId:Int) :
    ArrayAdapter<User>(context, resource, objects) {

    var flater: LayoutInflater? = null

    init {
        flater = context.layoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var user  = getItem(position)
        val rootView: View? = flater?.inflate(R.layout.spinner_view, null, true)
        val txtName = rootView?.findViewById<TextView>(R.id.name)
        txtName?.text = user?.firstName + " " + user?.lastName

        return  rootView!!


    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var user  = getItem(position)
        val rootView: View? = flater?.inflate(R.layout.spinner_view, null, true)
        val txtName = rootView?.findViewById<TextView>(R.id.name)
        txtName?.text = user?.firstName + " " + user?.lastName

        return  rootView!!
    }

}