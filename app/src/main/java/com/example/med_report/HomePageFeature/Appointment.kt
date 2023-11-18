package com.example.med_report.HomePageFeature



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.med_report.MainActivity
import com.example.med_report.Models.AppointmentModel
import com.example.med_report.databinding.ActivityAppointmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Appointment : AppCompatActivity() {

    private lateinit var binding : ActivityAppointmentBinding
    private lateinit var dbrefhw : DatabaseReference
    private lateinit var dbrefaplist :DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
            supportActionBar?.hide()


            dbrefhw = FirebaseDatabase.getInstance().getReference("Health Worker")
           dbrefaplist = FirebaseDatabase.getInstance().getReference("AppointmentData")



        binding.btnAppointment.setOnClickListener {

            val name = binding.appointmentName.text.toString()
            val age = binding.appointmentAge.text.toString()
            val location = binding.appointmentLocation.text.toString()
            val date = binding.appointmentDate.text.toString()


            if(checkField()){
                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser != null) {
                    //getting its uid
                    val userId = currentUser.uid

                    // Create a reference to the user's data using their UID
                    val userRef = dbrefhw.child(userId)
                    val userdbrefaplist = dbrefaplist.child(userId)


                    // Create a patient object
                    val patient = AppointmentModel(userId, name, age, location, date,)

                    // Set the patient data under the user's reference
                    userRef.setValue(patient).addOnCompleteListener {

                        Toast.makeText(this, "Appointment Book Sucessfully", Toast.LENGTH_SHORT).show()
                        var i = Intent(this, MainActivity::class.java)


                        val handler = Handler(Looper.getMainLooper())
                        handler.postDelayed({
                            finish()
                            startActivity(i)
                        }, 1000)
                        Log.e("error",it.exception.toString())

                    }.addOnFailureListener {
                        Toast.makeText(this, "Fail To Book Appointment", Toast.LENGTH_SHORT).show()
                    }

                        //data insertion on appointment node
                    userdbrefaplist.setValue(patient).addOnCompleteListener {
                        var i = Intent(this, MainActivity::class.java)


                        val handler = Handler(Looper.getMainLooper())
                        handler.postDelayed({
                            finish()
                            startActivity(i)
                        }, 1000)
                        Log.e("error",it.exception.toString())

                    }







                } else {
                    // Handle the case where the current user is null
                    Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                }

                //ssetting value in appointment list node



            }


        }


    }

    private fun checkField():Boolean{
        if(binding.appointmentAge.text.toString()==""){
            binding.appointmentAge.error="Field Required"
            return false
        }
        if(binding.appointmentName.text.toString()==""){
            binding.appointmentName.error="Field Required"
            return false
        }
        if(binding.appointmentLocation.text.toString()==""){
            binding.appointmentLocation.error="Field Required"
            return false
        }
        if(binding.appointmentDate.text.toString()==""){
            binding.appointmentDate.error="Field Required"
            return false
        }

        return true
    }
}