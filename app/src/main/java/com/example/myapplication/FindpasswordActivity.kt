package com.example.signinup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.signinup.databinding.ActivityFindPasswordBinding
import com.example.signinup.databinding.ActivitySelectImageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FindPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFindPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_password)
        binding= ActivityFindPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val Useremail=binding.findUserEmail.text.toString()
        //val user= Firebase.auth.currentUser
        //var email=user!!.email
        binding.okBtn.setOnClickListener{
            Firebase.auth.sendPasswordResetEmail(Useremail)
                .addOnCompleteListener {
                    if (it.isSuccessful)
                        Toast.makeText(this,"이메일을 전송했습니다",Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(this,"이메일 전송을 실패했습니다",Toast.LENGTH_SHORT).show()
                }
        }
    }
}