package com.example.med_report

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.med_report.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private  lateinit var  binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1.setOnClickListener {
            var i = Intent(this,Appointment::class.java)
            startActivity(i)
            //finish or not
        }

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            var i = Intent(this,login::class.java)
//            val sharePreference = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
//            val editor = sharePreference.edit()
//            editor.putString("name","false")
//            editor.apply()
            startActivity(i)
            finish()
        }




    }
}