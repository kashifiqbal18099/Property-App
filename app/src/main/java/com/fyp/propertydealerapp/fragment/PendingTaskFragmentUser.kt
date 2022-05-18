package com.fyp.propertydealerapp.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.fyp.propertydealerapp.databinding.FragmentPendingTaskUserBinding
import com.fyp.propertydealerapp.databinding.UserTaskItemBinding
import com.fyp.propertydealerapp.model.Comments
import com.fyp.propertydealerapp.model.TasksModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class PendingTaskFragmentUser : BaseFramnet<FragmentPendingTaskUserBinding>() {

    lateinit var genericAdapter:GenericAdapter<TasksModel, UserTaskItemBinding>
    var db:FirebaseFirestore  = FirebaseFirestore.getInstance()
    var list:MutableList<TasksModel>  = ArrayList()
    var sharedPrefs: SharedPreferences?=null
    var mAuth:FirebaseAuth = FirebaseAuth.getInstance()
    override fun OnCreateView() {
        genericAdapter   =  object  : GenericAdapter<TasksModel,UserTaskItemBinding>(requireActivity(),list){
            override val layoutResId: Int
                get() = R.layout.user_task_item


            override fun onBindData(
                model: TasksModel,
                position: Int,
                dataBinding: UserTaskItemBinding
            ) {
                dataBinding.tasksModel  = model

                dataBinding.comment.setOnClickListener {
                    if(dataBinding.commentLayout.visibility==View.GONE){
                        dataBinding.commentLayout.visibility  = View.VISIBLE
                    }
                    else{
                        dataBinding.commentLayout.visibility  = View.GONE
                    }
                }

                dataBinding.send.setOnClickListener {
                    if(dataBinding.commentEdt.text.toString().isNullOrEmpty()){
                        showSnackbar("please enter comment",resources.getColor(R.color.red))
                    }
                    else{
                        var map: MutableMap<String, Any> = HashMap()
                        map.put("comments", FieldValue.arrayUnion(Comments(dataBinding.commentEdt.text.toString(),System.currentTimeMillis().toString(),sharedPrefs?.getString("userName","")!!)))
                        db  = FirebaseFirestore.getInstance()
                        customProgressDialog?.show()
                        db.collection("Tasks").document(model.taskId).set(map, SetOptions.merge()).addOnSuccessListener {
                            customProgressDialog?.dismiss()
                            dataBinding?.commentEdt.setText("")
                            showSnackbar("Comment Added",resources.getColor(R.color.green))
                        }.addOnFailureListener {
                            customProgressDialog?.dismiss()
                            showSnackbar(it.message!!,resources.getColor(R.color.red))
                        }
                    }
                }

            }

            override fun onItemClick(
                model: TasksModel,
                position: Int,
                dataBinding: UserTaskItemBinding
            ) {
                var intent = Intent(context, TaskDetailsActivity::class.java)
                intent.putExtra("task",model)
                startActivity(intent)
            }

        }

        sharedPrefs = requireActivity().getSharedPreferences("Main", Context.MODE_PRIVATE)
        GetDataBinding()?.pendingTasksUserRec?.adapter  = genericAdapter
        getPendingTasks()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    fun  getPendingTasks(){
        customProgressDialog?.show()
        db.collection("Tasks").whereEqualTo("userId",mAuth.currentUser?.uid!!).whereEqualTo("status","Pending").addSnapshotListener { value, error ->
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
      return  R.layout.fragment_pending_task_user
    }
}