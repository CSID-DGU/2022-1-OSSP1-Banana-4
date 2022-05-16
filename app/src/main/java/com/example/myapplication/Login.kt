package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
    /*
    val binding=ActivityLoginBinding.inflate(layoutInflater)
    setcontentview(binding.root)
    sign_bt.setOnClickListener{
        var UserId=id.text.toString()
        var UserPwd=password.text.toString()
        var RePwd=password_re.text.toString()
        var UserEmail=email.text.toString()

        if(TextUtils.isEmpty(UserId)){
            Toast.makeText(applicationContext,"아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
        }

        if(TextUtils.isEmpty(UserEmail)){
            Toast.makeText(applicationContext,"이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
        }

        if(!UserPwd.equals(RePwd)){
            Toast.makeText(applicationContext,"비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
        }

        if(TextUtils.isEmpty(RePwd)){
            Toast.makeText(applicationContext,"비밀번호 확인을 입력해주세요", Toast.LENGTH_SHORT).show()
        }
        registerUser(UserId,UserPwd,UserEmail)
    }
    */

/*
    private fun registerUser(userName:String,email:String,password:String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    var user: FirebaseUser?=auth.currentUser
                    var userId:String=user!!.uid

//데이터베이스에서 데이터를 읽거나 쓰려면 DatabaseReference의 인스턴스가 필요합니다.
                    databaseReference= FirebaseDatabase.getInstance()
                        .getReference("Users").child(userId)

                    var hashMap:HashMap<String,String> = HashMap()
                    hashMap.put("userId",userId)
                    hashMap.put("userName",userName)
                    hashMap.put("profileImage","")

                    databaseReference.setValue(hashMap).addOnCompleteListener(this){
                        if(it.isSuccessful){
                            //open home activity
                            //val next=Intent(this,Database_example::class.java)
                            //startActivity(next)
                        }
                    }
                }
            }
    }
 */
}

