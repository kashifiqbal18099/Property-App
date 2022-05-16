package com.fyp.propertydealerapp.util
import android.app.Activity
import android.app.Dialog
import android.content.Context
import com.fyp.propertydealerapp.R


class CustomProgressDialog(var context: Context) {
    var dialog: Dialog? = null
    var activity: Activity
    fun Create(context: Context?) {
        try {
            dialog = Dialog(context!!, R.style.NewDialog)
            dialog.let {
                it?.setContentView(R.layout.customprogress_dialog)
            }
            //   dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog!!.setCancelable(true)

            /*          hud = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                     .setBackgroundColor(context.getResources().getColor(R.color.lenovo_blue));*/
        } catch (exp: Exception) {
            exp.printStackTrace()
        }
    }

    fun show() {
        if (dialog != null) {
            if (!dialog!!.isShowing) {
                if (!activity.isFinishing) {
                    dialog!!.show()
                }
            }
        }
    }

    fun dismiss() {
        if (dialog != null) {
            if (dialog!!.isShowing) {
                if (!activity.isFinishing) {
                    dialog!!.dismiss()
                }
            }
        }
    }

    init {
        Create(context)
        activity = context as Activity
    }
}