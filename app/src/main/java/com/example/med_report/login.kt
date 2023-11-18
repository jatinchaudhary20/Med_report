package com.example.med_report


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.med_report.signup
import com.example.med_report.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class login : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        val sharePreference = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        var check = sharePreference.getString("name","")
        if(check.equals("true")){
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        auth = Firebase.auth




        binding.loginbutton.setOnClickListener {

            //just to check this clicks or not


            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()
            if(checkAllfield()){
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{

                    if(it.isSuccessful){
                        val sharePreference = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
                        val editor = sharePreference.edit()
                        editor.putString("name","true")
                        editor.apply()
                        //working
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this,"Wrong Credential",Toast.LENGTH_SHORT).show()

                        //error msg and clear entries
                        binding.loginEmail.text?.clear()
                        binding.loginPassword.text?.clear()
                        binding.loginEmail.error = "Wrong Credential"
                        binding.loginPassword.error = "Wrong Credential"

                        //after 2.5sec waring goes null

                        val handler = Handler(Looper.getMainLooper())
                        handler.postDelayed({

                            binding.loginEmail.error = null
                            binding.loginPassword.error = null
                        }, 2500)
                        Log.e("error",it.exception.toString())


                    }
                }
            }
        }

        binding.signupPageText.setOnClickListener {

            var intent = Intent(this, signup::class.java)
            startActivity(intent)
        }

    }

    private fun checkAllfield() :Boolean{

        if(binding.loginEmail.text.toString()==""){
            binding.loginEmail.error="Field required"
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(binding.loginEmail.text.toString()).matches()){
            binding.loginEmail.error="Format Invaild"
            return false
        }

        if(binding.loginPassword.text.toString()==""){
            binding.loginPassword.error="Field required"
            return false
        }

        return true
    }
}