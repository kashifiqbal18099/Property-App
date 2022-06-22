package com.fyp.propertydealerapp.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.fyp.propertydealerapp.R
import com.fyp.propertydealerapp.activities.bouns.BounsActivity
import com.fyp.propertydealerapp.databinding.ActivityProfileBinding
import com.fyp.propertydealerapp.model.User
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kashif.veterinarypharmacy.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.*


class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    var auth:FirebaseAuth  = FirebaseAuth.getInstance()
    var db:FirebaseFirestore  = FirebaseFirestore.getInstance()
    var bouns:Double  = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding?.user  = User()
        dataBinding?.bouns  = ""
        getUserData()
        getUserBounas()

        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "type"



        db = FirebaseFirestore.getInstance()
        val typeAmountMap: MutableMap<String, Int> = HashMap()
        db.collection("Tasks").whereEqualTo("userId",auth?.currentUser?.uid!!).whereEqualTo("status","Pending").addSnapshotListener{value,error->
           if(error==null){
               typeAmountMap["Pending Tasks"] = value?.documents!!.size

               db.collection("Tasks").whereEqualTo("userId",auth?.currentUser?.uid!!).whereEqualTo("status","Complete").addSnapshotListener{value,error->
                   if(error==null){
                       typeAmountMap["Completed Tasks"] = value?.documents!!.size

                       val colors: ArrayList<Int> = ArrayList()
                       colors.add(Color.parseColor("#304567"))
                       colors.add(Color.parseColor("#309967"))

                       for (type in typeAmountMap.keys) {
                           pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
                       }

                       initPieChart()

                       val pieDataSet = PieDataSet(pieEntries, label)
                       //setting text size of the value
                       //setting text size of the value
                       pieDataSet.valueTextSize = 12f
                       //providing color list for coloring different entries
                       //providing color list for coloring different entries
                       pieDataSet.colors = colors
                       //grouping the data set from entry to chart
                       //grouping the data set from entry to chart
                       val pieData = PieData(pieDataSet)
                       //showing the value of the entries, default true if not set
                       //showing the value of the entries, default true if not set
                       pieData.setDrawValues(true)

                      // pieData.setValueFormatter(PercentFormatter())

                       dataBinding?.chart1?.data  = pieData
                       dataBinding?.chart1?.invalidate()

                   }
               }
           }
        }







        dataBinding?.detailTxt?.setOnClickListener {
            startActivity(Intent(this@ProfileActivity,BounsActivity::class.java))
        }

    }
    private fun initPieChart() {
        //using percentage as values instead of amount
        dataBinding?.chart1?.setUsePercentValues(true)

        //remove the description label on the lower left corner, default true if not set
        dataBinding?.chart1?.getDescription()?.setEnabled(false)

        //enabling the user to rotate the chart, default true
        dataBinding?.chart1?.setRotationEnabled(true)
        //adding friction when rotating the pie chart
        dataBinding?.chart1?.setDragDecelerationFrictionCoef(0.9f)
        //setting the first entry start from right hand side, default starting from top
        dataBinding?.chart1?.setRotationAngle(0F)

        //highlight the entry when it is tapped, default true if not set
        dataBinding?.chart1?.setHighlightPerTapEnabled(true)
        //adding animation so the entries pop up from 0 degree
       // dataBinding?.chart1?.animateY(1400, Easing.Ease)
        //setting the color of the hole in the middle, default white
        dataBinding?.chart1?.setHoleColor(Color.parseColor("#000000"))
    }

    fun getUserBounas(){
        db =  FirebaseFirestore.getInstance()
        val cal: Calendar = Calendar.getInstance()
        val month_date = SimpleDateFormat("MMMM")
        val month_name: String = month_date.format(cal.getTime())

        customProgressDialog?.show()
        db.collection("Bouns").document(auth?.currentUser?.uid!!).collection("Months").addSnapshotListener{value,error->
            customProgressDialog?.dismiss()
            if(error==null){
                for(doc in value?.documents!!){
                    bouns += doc.getDouble("bouns")!!


                }
                dataBinding?.bouns  = bouns.toString()
            }
            else{
                showSnackbar("Error Loading Bouns",resources.getColor(R.color.red))
            }
        }
    }

  fun getUserData(){
      customProgressDialog?.show();
        db.collection("User").document(auth.currentUser?.uid!!).addSnapshotListener{value, error ->
            customProgressDialog?.dismiss()
            if(error==null){
                var user  = value?.toObject(User::class.java)

                dataBinding?.user  = user
            }
            else{
                showSnackbar(error.message!!,resources.getColor(R.color.red))
            }
        }
    }




    override val layoutRes: Int
        get() = R.layout.activity_profile
}