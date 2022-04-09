package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


        button.setOnClickListener{
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
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