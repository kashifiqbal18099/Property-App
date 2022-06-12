package com.fyp.propertydealerapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.activities.tasks.TaskDetailsActivity
import com.fyp.propertydealerapp.base.BaseFramnet
import com.fyp.propertydealerapp.base.GenericAdapter
import com.fyp.propertydealerapp.databinding.AdminTaskItemBinding
import com.fyp.propertydealerapp.databinding.FragmentPendingTaskAdminBinding
import com.fyp.propertydealerapp.model.TasksModel
import com.google.firebase.firestore.FirebaseFirestore

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
                    db  = FirebaseFirestore.getInstance()
                    customProgressDialog?.show()
                    db.collection("Tasks").document(model.taskId).update("status","Complete").addOnSuccessListener {
                        customProgressDialog?.dismiss()

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