package com.example.med_report.HomePageFeature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.med_report.databinding.ActivityRecordsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class Records : AppCompatActivity() {
    private lateinit var binding: ActivityRecordsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid.toString()
        val dbrefpateintreport = FirebaseDatabase.getInstance().getReference("Patient Report").child(userId)

        // Define these variables inside the success listener to get the data
        dbrefpateintreport.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                Toast.makeText(this, "Success To load", Toast.LENGTH_SHORT).show()

                // If data exists in the database for the current user, extract values
                val data = snapshot.value as Map<String, Any>

                // Extract data from the snapshot
                val bmi = data["bmi"].toString()
                val name = data["name"].toString()
                val bp = data["bp"].toString()
                val heartrate = data["heartrate"].toString()
                val sugar = data["sugar"].toString()
                val doctorname = data["doctorname"].toString()
                val location = data["location"].toString()

                // Update the UI with the retrieved data
                updateUI(bmi, name, bp, heartrate, sugar, doctorname, location)
            } else {
                // Data doesn't exist for the current user
                Toast.makeText(this, "Fail To load", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(bmi: String, name: String, bp: String, heartrate: String, sugar: String, doctorname: String, location: String) {
        // Update your UI elements with the retrieved data
        binding.recordbmi.text = bmi
        binding.recordname.text = name
        binding.recordbp.text = bp
        binding.recordheartrate.text = heartrate
        binding.recordsugar.text = sugar
        binding.recorddoctorname.text = doctorname
        binding.recordlocation.text = location
    }
}

