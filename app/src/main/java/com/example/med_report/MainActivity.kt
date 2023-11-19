package com.example.med_report

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.med_report.Authenticate.login
import com.example.med_report.HomePageFeature.Appointment
import com.example.med_report.HomePageFeature.Records
import com.example.med_report.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private  lateinit var  binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1.setOnClickListener {
            var i = Intent(this, Appointment::class.java)
            startActivity(i)
            //finish or not
        }

        binding.button2.setOnClickListener{
            var i = Intent(this, Records::class.java)
            startActivity(i)
        }

        binding.help.setOnClickListener {


                // Replace "89743" with the actual phone number you want to call
                val phoneNumber = "tel:9680905523"

                // Create an intent with the ACTION_CALL action and the phone number URI
                val callIntent = Intent(Intent.ACTION_CALL,Uri.parse(phoneNumber))

                // Check if the CALL_PHONE permission is granted before making the call
                if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    // Start the call
                    startActivity(callIntent)
                } else {
                    // Request the CALL_PHONE permission
                    requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE), 1)
                }

        }

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            var i = Intent(this, login::class.java)
//            val sharePreference = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
//            val editor = sharePreference.edit()
//            editor.putString("name","false")
//            editor.apply()
            startActivity(i)
            finish()
        }






    }
}