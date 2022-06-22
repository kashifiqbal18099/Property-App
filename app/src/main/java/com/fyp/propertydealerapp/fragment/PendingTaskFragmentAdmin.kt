package com.fyp.propertydealerapp.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.activities.tasks.TaskDetailsActivity
import com.fyp.propertydealerapp.base.BaseFramnet
import com.fyp.propertydealerapp.base.GenericAdapter
import com.fyp.propertydealerapp.databinding.AdminTaskItemBinding
import com.fyp.propertydealerapp.databinding.FragmentPendingTaskAdminBinding
import com.fyp.propertydealerapp.model.TasksModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PendingTaskFragmentAdmin : BaseFramnet<FragmentPendingTaskAdminBinding>() {

    lateinit var genericAdapter:GenericAdapter<TasksModel,AdminTaskItemBinding>
    var db:FirebaseFirestore  = FirebaseFirestore.getInstance()
    var list:MutableList<TasksModel>  = ArrayList()
    override fun OnCreateView() {
        genericAdapter   =  object  : GenericAdapter<TasksModel,AdminTaskItemBinding>(requireActivity(),list){
            override val layoutResId: Int
                get() = R.layout.admin_task_item

            override fun onBindData(
                model: TasksModel,
                position: Int,
                dataBinding: AdminTaskItemBinding
            ) {
                dataBinding.tasksModel  = model

                dataBinding.markComplete.setOnClickListener{
                    Log.e("Heelo","hello")

                    var dialog  = Dialog(requireActivity(),R.style.NewDialog)
                    dialog.setContentView(R.layout.bouns_dialog)



                    db  = FirebaseFirestore.getInstance()
                    customProgressDialog?.show()
                    db.collection("Tasks").document(model.taskId).update("status","Complete").addOnSuccessListener {
                        customProgressDialog?.dismiss()

                        var addBounsButton  = dialog.findViewById<Button>(R.id.add_bouns)
                        var amountTxt  = dialog.findViewById<EditText>(R.id.bouns_edt)
                        dialog.setCancelable(false)
                        dialog.show()
                        db  = FirebaseFirestore.getInstance()
                        val cal: Calendar = Calendar.getInstance()
                        val month_date = SimpleDateFormat("MMMM")
                        val month_name: String = month_date.format(cal.getTime())

                        addBounsButton.setOnClickListener {
                            if(amountTxt.text.toString().isEmpty()){
                                showSnackbar("Please enter amount",resources.getColor(R.color.red))
                            }
                            else{
                                var map: MutableMap<String, Any> = HashMap()
                                map.put("bouns", FieldValue.increment(amountTxt.text.toString().toDouble()))
                                map.put("month",month_name)
                                db.collection("Bouns").document(model.userId).collection("Months").document(month_name).update(map).addOnSuccessListener {
                                    dialog.dismiss()
                                    showSnackbar("Bouns Added",resources.getColor(R.color.green))
                                }.addOnFailureListener {
                                   // dialog.dismiss()
                                    map.put("bouns", FieldValue.increment(amountTxt.text.toString().toDouble()))
                                    db.collection("Bouns").document(model.userId).collection("Months").document(month_name).set(map).addOnSuccessListener {
                                        dialog.dismiss()
                                        showSnackbar("Bouns Added",resources.getColor(R.color.green))

                                    }.addOnFailureListener {
                                        dialog.dismiss()
                                        showSnackbar(it?.message!!,resources.getColor(R.color.red))
                                    }
                                   // showSnackbar(it?.message!!,resources.getColor(R.color.red))
                                }
                            }

                        }






                    }.addOnFailureListener {
                        customProgressDialog?.dismiss()
                    }
                }
            }

            override fun onItemClick(
                model: TasksModel,
                position: Int,
                dataBinding: AdminTaskItemBinding
            ) {
                var intent = Intent(context, TaskDetailsActivity::class.java)
                intent.putExtra("task",model)
                startActivity(intent)
            }

        }
        GetDataBinding()?.pendingTasksAdminRec?.adapter  = genericAdapter
        getPendingTasks()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    fun  getPendingTasks(){
        customProgressDialog?.show()
        db.collection("Tasks").whereEqualTo("status","Pending").addSnapshotListener { value, error ->
            customProgressDialog?.dismiss()
            if(error==null){
                list?.clear()
                for(document in value?.documents!!){
                    var taskmodel  = document.toObject(TasksModel::class.java)
                    list?.add(taskmodel!!)
                }

                if(list?.size<=0){
                    dataBinding?.noDataFound?.visibility  = View.VISIBLE;
                    dataBinding?.pendingTasksAdminRec?.visibility  = View.GONE;
                }
                else{
                    dataBinding?.noDataFound?.visibility  = View.GONE;
                    dataBinding?.pendingTasksAdminRec?.visibility  = View.VISIBLE;
                }

                genericAdapter.notifyDataSetChanged()
            }
            else{
                showSnackbar(error.message!!,resources.getColor(R.color.red))
            }
        }
    }

    override fun getlayout(): Int {
      return  R.layout.fragment_pending_task_admin
    }
}