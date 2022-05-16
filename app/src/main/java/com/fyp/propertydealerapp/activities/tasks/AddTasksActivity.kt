package com.fyp.propertydealerapp.activities.tasks

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.fyp.propertydealerapp.BR
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.adapter.CustomeSpinnerAdapter
import com.fyp.propertydealerapp.databinding.ActivityAddTasksBinding
import com.fyp.propertydealerapp.model.TasksModel
import com.fyp.propertydealerapp.model.User
import com.fyp.propertydealerapp.util.ClickHandlers
import com.google.firebase.firestore.FirebaseFirestore
import com.kashif.veterinarypharmacy.base.BaseActivity
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlin.collections.ArrayList


class AddTasksActivity : BaseActivity<ActivityAddTasksBinding>(),ClickHandlers {
    lateinit var db:FirebaseFirestore;
    var userList:MutableList<User>  =ArrayList()
    lateinit var userAdapter: CustomeSpinnerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_add_tasks)
        dataBinding?.viewmodel  = TasksModel()
        dataBinding?.clickHandlers  =this
        db  = FirebaseFirestore.getInstance()
         userAdapter = CustomeSpinnerAdapter(
            this,
          R.layout.spinner_view,
             userList,
             R.id.name,
        )
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataBinding?.usersSpinner?.adapter  = userAdapter
        dataBinding?.usersSpinner?.onItemSelectedListener  = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                dataBinding?.viewmodel?.userId  = userList.get(position).id!!

            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        getAllUsers()

    }


    fun getAllUsers(){

      var userRef =   db.collection("User")
        customProgressDialog?.show()
        userRef.whereEqualTo("isAdmin",false).addSnapshotListener { value, error ->
            customProgressDialog?.dismiss()
            if(error==null){
                for(document in value?.documents!!){
                     var user  = document.toObject(User::class.java)
                    userList?.add(user!!)

                }
                userAdapter.notifyDataSetChanged()
               // dataBinding?.usersSpinner?.setSelection(0)
            }
            else{

                showSnackbar(error?.message!!,resources.getColor(R.color.red))
            }
        }
    }

    fun addTask(){

        var task  = dataBinding?.viewmodel!!
        if(task.propertyType.isNullOrEmpty()){
            showSnackbar("Please enter property type",resources.getColor(R.color.red))
        }
        else if(task.propertySize.isNullOrEmpty()){
            showSnackbar("Please enter property size",resources.getColor(R.color.red))
        }
        else if(task.propertyOwnerName.isNullOrEmpty()){
            showSnackbar("Please enter property owner name",resources.getColor(R.color.red))
        }
        else if(task.propertyNumber.isNullOrEmpty()){
            showSnackbar("Please enter property number",resources.getColor(R.color.red))
        }
        else if(task.propertyOwnerPhone.isNullOrEmpty()){
            showSnackbar("Please enter property owner phone",resources.getColor(R.color.red))
        }
        else if(task.userId.isNullOrEmpty()){
            showSnackbar("Please select employee",resources.getColor(R.color.red))
        }
        else if(task.completetionDate.isNullOrEmpty()){
            showSnackbar("Please enter completion date",resources.getColor(R.color.red))
        }
        else{
            addTaskFinalStep()
        }
    }

    fun addTaskFinalStep(){
        db  = FirebaseFirestore.getInstance()
        customProgressDialog?.show()
        var docRef = db.collection("Tasks").document()
        var docId  =docRef.id
        dataBinding?.viewmodel?.taskId  = docId
        docRef.set(dataBinding?.viewmodel!!).addOnSuccessListener {
            customProgressDialog?.dismiss()
            showSnackbar("Tasks Added",resources.getColor(R.color.green))
        }.addOnFailureListener {
            showSnackbar(it.message!!,resources.getColor(R.color.red))
        }
    }

    fun datePickerDialog(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            dataBinding?.viewmodel?.completetionDate  = "${year}-${monthOfYear}-${dayOfMonth}"
            dataBinding?.viewmodel?.notifyPropertyChanged(BR._all)

        }, year, month, day)

        dpd.show()
    }

    override val layoutRes: Int
        get() = R.layout.activity_add_tasks

    override fun onClick(view: View?) {
       // Log.e("Hello","Hello")

        val id = view?.id!!
        if(id==R.id.add_task_btn){
            addTask()
        }
        else if(id==R.id.completionDateEdt){

            datePickerDialog()
        }
    }
}