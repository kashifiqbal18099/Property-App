package com.fyp.propertydealerapp.util

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

class CustomBindingAdapter {

    companion object{
        @JvmStatic
        @BindingAdapter("app:errorText")
        fun setErrorMessage(view: TextInputLayout, errorMessage: String) {
            view.error = errorMessage
        }
    }

}