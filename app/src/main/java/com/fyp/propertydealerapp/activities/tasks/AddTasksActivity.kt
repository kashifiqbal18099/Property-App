package com.fyp.propertydealerapp.activities.tasks

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.fyp.propertydealerapp.BR
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.adapter.CustomeSpinnerAdapter
import com.fyp.propertydealerapp.databinding.ActivityAddTasksBinding
import com.fyp.propertydealerapp.model.TasksModel
import com.fyp.propertydealerapp.model.User
import com.fyp.propertydealerapp.util.ClickHandlers
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.kashif.veterinarypharmacy.base.BaseActivity
import com.zeeshan.material.multiselectionspinner.MultiSelectionSpinner
import java.util.*
import kotlin.collections.ArrayList


class AddTasksActivity : BaseActivity<ActivityAddTasksBinding>(),ClickHandlers {
    lateinit var db:FirebaseFirestore;
    var userList:MutableList<Any>  =ArrayList()
    var userNameList:MutableList<Any>  = ArrayList()
    var finalUserList:MutableList<User>  = ArrayList()
    lateinit var userAdapter: CustomeSpinnerAdapter
    var firebaseStorage:FirebaseStorage  = FirebaseStorage.getInstance();
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_add_tasks)
        dataBinding?.viewmodel  = TasksModel()
        dataBinding?.clickHandlers  =this
        dataBinding?.multiSelection?.isSort =false;
        db  = FirebaseFirestore.getInstance()
        /* userAdapter = CustomeSpinnerAdapter(
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
        }*/

        dataBinding?.multiSelection?.setOnItemSelectedListener(object : MultiSelectionSpinner.OnItemSelectedListener {
            override fun onItemSelected(view: View, isSelected: Boolean, position: Int) {
              // Toast.makeText(this@AddTasksActivity, "On Item selected : " +(userList.get(position)as User).firstName, Toast.LENGTH_SHORT).show()
             //  Toast.makeText(this@AddTasksActivity, "On Item selected : " +userNameList.get(position), Toast.LENGTH_SHORT).show()

                finalUserList.add(userList.get(position) as User)
            }

            override fun onSelectionCleared() {
//                Toast.makeText(MainActivity.this, "All items are unselected", Toast.LENGTH_SHORT).show();
            }
        })

        dataBinding?.uploadImage?.setOnClickListener{
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

        getAllUsers()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val uri: Uri = data?.data!!
            val storageRef  = firebaseStorage.reference.child(System.currentTimeMillis().toString()  + ".png")

            customProgressDialog?.show()

           storageRef.putFile(uri).addOnSuccessListener {
               storageRef.downloadUrl.addOnSuccessListener {
                   customProgressDialog?.dismiss()

                   showSnackbar("Image Uploaded Successfully ",resources.getColor(R.color.green))
                   dataBinding?.viewmodel?.imageUrl  = it.toString()
                   dataBinding?.viewmodel?.notifyPropertyChanged(BR._all)

               }.addOnFailureListener {
                   customProgressDialog?.dismiss()
                   Log.e("ImageUploadError",it.message!!)
                   showSnackbar(it.message!!,resources.getColor(R.color.red))
               }

           }.addOnFailureListener{
               customProgressDialog?.dismiss()
               Log.e("ImageUploadError",it.message!!)
               showSnackbar(it.message!!,resources.getColor(R.color.red))
           }
        }


    }

    fun getAllUsers(){

      var userRef =   db.collection("User")
        customProgressDialog?.show()
        userRef.whereEqualTo("isAdmin",false).addSnapshotListener { value, error ->
            customProgressDialog?.dismiss()
            if(error==null){
                for(document in value?.documents!!){
                     val user  = document.toObject(User::class.java)
                      userList.add(user!!)
                    userNameList.add(user?.firstName!! +  " "   + user?.lastName)


                }
                Log.e("USERLIST",userNameList.toString());
                dataBinding?.multiSelection?.items  = userNameList

              //  userAdapter.notifyDataSetChanged()
               // dataBinding?.usersSpinner?.setSelection(0)
            }
            else{

                showSnackbar(error.message!!,resources.getColor(R.color.red))
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
        else if(task.propertyPrice.isNullOrEmpty()){
            showSnackbar("Please enter property price",resources.getColor(R.color.red))
        }
        else if(task.propertyArea.isNullOrEmpty()){
            showSnackbar("Please enter property area",resources.getColor(R.color.red))
        }
        else if(task.propertyAddress.isNullOrEmpty()){
            showSnackbar("Please enter property address",resources.getColor(R.color.red))
        }
        else if(task.propertyPupose.isNullOrEmpty()){
            showSnackbar("Please enter property purpose",resources.getColor(R.color.red))
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
        else if(finalUserList.isNullOrEmpty()){
            showSnackbar("Please select at least one  employee",resources.getColor(R.color.red))
        }
        else if(task.completetionDate.isNullOrEmpty()){
            showSnackbar("Please enter completion date",resources.getColor(R.color.red))
        }

        else{
            addTaskFinalStep()
        }
    }

    fun addTaskFinalStep(){

        for(finaluser in finalUserList){
            db  = FirebaseFirestore.getInstance()
            customProgressDialog?.show()
            var docRef = db.collection("Tasks").document()
            var docId  =docRef.id
            dataBinding?.viewmodel?.userId  = finaluser.id!!
            dataBinding?.viewmodel?.taskId  = docId
            dataBinding?.viewmodel?.userName  = finaluser.firstName  +" " +   finaluser.lastName
            docRef.set(dataBinding?.viewmodel!!).addOnSuccessListener {
                customProgressDialog?.dismiss()

            }.addOnFailureListener {
                showSnackbar(it.message!!,resources.getColor(R.color.red))
            }
        }
        showSnackbar("Tasks Added",resources.getColor(R.color.green))
        
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