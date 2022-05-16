package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private lateinit var id:EditText
    private lateinit var button:Button
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var password_check:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_main)

        id=findViewById(R.id.ID)
        email=findViewById(R.id.email)
        password=findViewById(R.id.Password)
        button=findViewById(R.id.button)
        password_check=findViewById(R.id.pwd_check)

        var User_pwd=password.text.toString() //입력받은 비밀번호를 문자열로 바꿔서 User_pwd에 저장
        var User_repwd=password_check.text.toString() //재확인할 비밀번호를 문자열로 User_repwd에 저장
        button.setOnClickListener{

            //빈칸 확인 및 비밀번호 확인
            if(TextUtils.isEmpty(id.text.toString())){
                Toast.makeText(this,"아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
            }

            if(TextUtils.isEmpty(email.text.toString())){
                Toast.makeText(this,"이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            }

            if(!User_pwd.equals(User_repwd)){
                Toast.makeText(this,"비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            }

            if(TextUtils.isEmpty(password_check.text.toString())){
                Toast.makeText(this,"비밀번호 확인을 입력해주세요", Toast.LENGTH_SHORT).show()
            }

            auth.createUserWithEmailAndPassword(email.text.toString(), User_pwd)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        var user: FirebaseUser?=auth.currentUser
                        var userId:String=user!!.uid
                        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(userId)
                        Log.d("TAG", "createUserWithEmail:success")

                        var hashMap:HashMap<String,String> = HashMap()
                        hashMap.put("userId",userId)
                        hashMap.put("userName",id.text.toString())

                        databaseReference.setValue(hashMap).addOnCompleteListener(this){
                            if(it.isSuccessful){
                               //id.setText("")
                               //email.setText("")
                               //password.setText("")
                               //password_check.setText("")
                            }
                        }

                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        }
    }

    private fun updateUI(user:FirebaseUser?) {
        val intent=Intent(this,Login::class.java)
        startActivity(intent)
    }


}