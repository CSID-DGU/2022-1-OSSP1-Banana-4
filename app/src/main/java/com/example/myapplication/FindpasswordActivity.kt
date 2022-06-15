package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityFindPasswordBinding
import com.example.myapplication.databinding.ActivitySelectImageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_find_password.*

class FindPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFindPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_password)
        binding= ActivityFindPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val Useremail=binding.findUserEmail
        //val user= Firebase.auth.currentUser
        //var email=user!!.email
        binding.okBtn.setOnClickListener{
            Firebase.auth.sendPasswordResetEmail(Useremail.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful)
                        Toast.makeText(this,"이메일을 전송했습니다",Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(this,"이메일 전송을 실패했습니다",Toast.LENGTH_SHORT).show()
                }
        }

        back_btn.setOnClickListener{
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}