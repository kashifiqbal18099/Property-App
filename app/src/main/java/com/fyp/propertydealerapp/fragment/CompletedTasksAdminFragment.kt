package com.fyp.propertydealerapp.fragment

import android.content.Intent
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.activities.tasks.TaskDetailsActivity
import com.fyp.propertydealerapp.base.BaseFramnet
import com.fyp.propertydealerapp.base.GenericAdapter
import com.fyp.propertydealerapp.databinding.AdminTaskItemBinding
import com.fyp.propertydealerapp.databinding.FragmentCompletedTasksAdminBinding
import com.fyp.propertydealerapp.model.TasksModel
import com.google.firebase.firestore.FirebaseFirestore

class CompletedTasksAdminFragment : BaseFramnet<FragmentCompletedTasksAdminBinding>() {

    lateinit var genericAdapter: GenericAdapter<TasksModel, AdminTaskItemBinding>
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var list:MutableList<TasksModel>  = ArrayList()


    override fun OnCreateView() {
       genericAdapter  = object   : GenericAdapter<TasksModel,AdminTaskItemBinding>(requireActivity(),list){
           override val layoutResId: Int
               get() =R.layout.admin_task_item

           override fun onBindData(
               model: TasksModel,
               position: Int,
               dataBinding: AdminTaskItemBinding
           ) {
               dataBinding?.tasksModel = model
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
        GetDataBinding()?.completedTaskAdminRec?.adapter  = genericAdapter
        getCompletedTasks()
    }

    fun  getCompletedTasks(){
        customProgressDialog?.show()
        db.collection("Tasks").whereEqualTo("status","Complete").addSnapshotListener { value, error ->
            customProgressDialog?.dismiss()
            if(error==null){
                list?.clear()
                for(document in value?.documents!!){
                    var taskmodel  = document.toObject(TasksModel::class.java)
                    list?.add(taskmodel!!)
                }

                genericAdapter.notifyDataSetChanged()
            }
            else{
                showSnackbar(error.message!!,resources.getColor(R.color.red))
            }
        }
    }
    override fun getlayout(): Int {
       return R.layout.fragment_completed_tasks_admin
    }


}