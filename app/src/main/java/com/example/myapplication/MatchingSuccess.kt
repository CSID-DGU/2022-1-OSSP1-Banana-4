package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
//import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_matching_success.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.brand_name.*
import org.w3c.dom.Text

class MatchingSuccess : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    lateinit var teamReference: DatabaseReference
    lateinit var imageIv:ImageView

    lateinit var storage:FirebaseStorage
    lateinit var firestore:FirebaseStorage

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_success)

        var database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference()


        var userid="id" //유저아이디, 별점은 선택시 전송하는걸로
        userid= FirebaseAuth.getInstance().currentUser?.uid.toString()


        teamReference=database.getReference("FinishedMatch")

        var teamid="id"
        teamReference.child(userid).child("teamID").get().addOnSuccessListener{
            teamid= it.value.toString()
        }

        databaseReference=database.getReference("MatchingUsers")
        //var arr= arrayListOf<String>("0","0","0")
        var arr = intent.getStringExtra("brandList" )
        var grade = intent.getStringExtra("grade" )
        var category = intent.getStringExtra("category" )

        var tv=findViewById(R.id.tv2) as TextView

        Log.e("grade",grade.toString())
        Log.e("cate",category.toString())
        Log.e("arr",arr.toString())

        //Log.e("arr",arr[1].toString())

       // Log.e("arr",arr[2].toString())

        println(grade)
        tv.setText(grade.toString())


        //##유저불러오기
        var i=0


        val btn_back=findViewById<Button>(R.id.btn_back) //뒤로가기버튼
        btn_back.setOnClickListener({

            val intent= Intent(this, MainPage::class.java) //다른 메뉴 찾아보기

            startActivity(intent)
        })

        val btn_done=findViewById<Button>(R.id.btn_done) //매칭 완료 채팅방생성
        btn_done.setOnClickListener({
            val intent= Intent(this, ChatActivity::class.java)
            intent.putExtra("teamid", teamid.toString())

            startActivity(intent)
        })
    }

    fun showToast(message: String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }
}

