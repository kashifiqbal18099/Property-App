package com.fyp.propertydealerapp.activities.tasks

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.base.GenericAdapter
import com.fyp.propertydealerapp.databinding.ActivityTaskDetailsBinding
import com.fyp.propertydealerapp.databinding.CommentItemBinding
import com.fyp.propertydealerapp.model.Comments
import com.fyp.propertydealerapp.model.TasksModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.kashif.veterinarypharmacy.base.BaseActivity

class TaskDetailsActivity : BaseActivity<ActivityTaskDetailsBinding>() {
    var taskModel:TasksModel? = null
    var sharedPrefs: SharedPreferences?=null
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit  var genericAdapter:GenericAdapter<Comments,CommentItemBinding>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        taskModel  = intent.getParcelableExtra("task")

        sharedPrefs = getSharedPreferences("Main", Context.MODE_PRIVATE)

        dataBinding?.tasksModel  = TasksModel()

        db.collection("Tasks").document(taskModel!!.taskId).addSnapshotListener{value,error->

            if(error==null){
                var task  = value?.toObject(TasksModel::class.java)
                dataBinding?.tasksModel   = task


                genericAdapter = object : GenericAdapter<Comments,CommentItemBinding>(this,dataBinding?.tasksModel?.comments!!){
                    override val layoutResId: Int
                        get() = R.layout.comment_item

                    override fun onBindData(
                        model: Comments,
                        position: Int,
                        dataBinding: CommentItemBinding
                    ) {
                        dataBinding?.commentModel  =model
                    }

                    override fun onItemClick(
                        model: Comments,
                        position: Int,
                        dataBinding: CommentItemBinding
                    ) {
                    }

                }

                dataBinding?.commentsRec?.adapter  = genericAdapter

                genericAdapter.notifyDataSetChanged()

            }
            else{
                showSnackbar(error.message.toString(),resources.getColor(R.color.red))
            }

        }






        dataBinding?.send?.setOnClickListener {
            if(dataBinding?.commentEdt?.text.toString().isNullOrEmpty()){
                showSnackbar("please enter comment",resources.getColor(R.color.red))
            }
            else{
                var map: MutableMap<String, Any> = HashMap()
                map.put("comments", FieldValue.arrayUnion(Comments(dataBinding?.commentEdt?.text.toString(),System.currentTimeMillis().toString(),sharedPrefs?.getString("userName","")!!)))
                db  = FirebaseFirestore.getInstance()
                customProgressDialog?.show()
                db.collection("Tasks").document(taskModel!!.taskId).set(map, SetOptions.merge()).addOnSuccessListener {
                    customProgressDialog?.dismiss()
                    dataBinding?.commentEdt?.setText("")
                    showSnackbar("Comment Added",resources.getColor(R.color.green))
                }.addOnFailureListener {
                    customProgressDialog?.dismiss()
                    showSnackbar(it.message!!,resources.getColor(R.color.red))
                }
            }
        }
    }

    override val layoutRes: Int
        get() = R.layout.activity_task_details
}