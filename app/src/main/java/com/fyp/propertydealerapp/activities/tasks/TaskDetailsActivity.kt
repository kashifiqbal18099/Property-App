package com.fyp.propertydealerapp.activities.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.base.GenericAdapter
import com.fyp.propertydealerapp.databinding.ActivityTaskDetailsBinding
import com.fyp.propertydealerapp.databinding.CommentItemBinding
import com.fyp.propertydealerapp.model.Comments
import com.fyp.propertydealerapp.model.TasksModel
import com.kashif.veterinarypharmacy.base.BaseActivity

class TaskDetailsActivity : BaseActivity<ActivityTaskDetailsBinding>() {
    var taskModel:TasksModel? = null
    lateinit  var genericAdapter:GenericAdapter<Comments,CommentItemBinding>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        taskModel  = intent.getParcelableExtra("task")
        dataBinding?.tasksModel  =taskModel


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

    override val layoutRes: Int
        get() = R.layout.activity_task_details
}