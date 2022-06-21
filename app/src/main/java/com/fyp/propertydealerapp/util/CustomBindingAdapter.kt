package com.fyp.propertydealerapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.fyp.propertydealerapp.R
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso

class CustomBindingAdapter {

    companion object{
        @JvmStatic
        @BindingAdapter("app:errorText")
        fun setErrorMessage(view: TextInputLayout, errorMessage: String) {
            view.error = errorMessage
        }

        @JvmStatic
        @BindingAdapter("app:imageurl")
        fun imageUrl(view:ImageView,url:String){
            if(!url.isNullOrEmpty()){
             Picasso.get().load(url).placeholder(R.drawable.task_list_icon).error(R.drawable.task_list_icon).into(view)
            }
        }
    }

}