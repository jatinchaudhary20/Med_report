package com.example.med_report

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.med_report.MainActivity
import com.example.med_report.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class signup : AppCompatActivity() {
    private lateinit var binding :ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= Firebase.auth

        binding.tvAlreadySignin.setOnClickListener {
            var intent= Intent(this, login::class.java)
            startActivity(intent)

        }



        binding.Register.setOnClickListener{


            val email = binding.etEmailSignup.text.toString()
            val password = binding.etPassSignup.text.toString()


            if (checkAllfield()){

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {

                    if(it.isSuccessful){

                        auth.signOut()
                        Toast.makeText(this,"Account has been created ",Toast.LENGTH_SHORT).show()
                        binding.etEmailSignup.text?.clear()
                        binding.etPassSignup.text?.clear()
                        binding.etUsernameSignup.text?.clear()
                        binding.phoneSignup.text?.clear()

                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)

                            finish()
                        },100)
                    }
                    else{
                        Log.e("error",it.exception.toString())
                        Toast.makeText(this,"button not working",Toast.LENGTH_SHORT).show()
                    }

                }

            }
        }
    }



    private fun checkAllfield() : Boolean{
        val username= binding.etUsernameSignup.text.toString()
        val email = binding.etEmailSignup.text.toString()
        val password = binding.etPassSignup.text.toString()
        val phone = binding.phoneSignup.text.toString()

        if(username==""){
            binding.etUsernameSignup.error="This is required field"
            return false
        }

        if(email==""){
            binding.etEmailSignup.error="This is required field"
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmailSignup.error="Email format wrong"
            return false
        }


        //password criteria
        if(password==""){
            binding.etPassSignup.error="This is required field"
            return false
        }

        if(password.length<=6){
            binding.etPassSignup.error="Password Should be atleast of 6 character"
            return false
        }
        if (phone==""){
            binding.phoneSignup.error="This is required field"
            return false
        }

        return true
    }
}
