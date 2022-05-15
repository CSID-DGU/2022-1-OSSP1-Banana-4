package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_my_page.*

class MyPage : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    /*
       * <DB, ID>
       * 1. 프로필 사진 : <userImage, profile_image>
       * 2. 사용자 닉네임 : <userNickname, username>
       * 3. 사용자 점수 : <userGrade, rating_bar>
       * 4. 사용자가 받은 평가 : <userReview, my_review>
       * 5. 사용자의 메이트 : <userMate, user_mate_list>
       * 6. 평가하기 버튼 : <mate1_review_button>
       * 7. 뒤로가기 버튼 : <before_button>
       */

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        auth = FirebaseAuth.getInstance()


        // firebase 데이터 연동
        val user = db.collection("users").document(auth.currentUser?.uid.toString())

        user.get().addOnSuccessListener{ documentSnapshot ->
            username.setText(documentSnapshot.get("userNickname").toString())
            val uri : Uri = Uri.parse(documentSnapshot.get("userImage").toString())
            profile_image.setImageURI(uri)
            rating_bar.rating = documentSnapshot.getDouble("userGrade")?.toFloat()!!
            my_review.setText(documentSnapshot.get("userReview").toString())
        }


        // mate review button 클릭하면 review.kt 로 데이터 전달 & 이동
       mate1_review_button.setOnClickListener{
            val intent = Intent(this, Review::class.java)
            // intent.putExtra("mate_id","${auth.currentUser?.mate.uid.toString()}")
            startActivity(intent)
        }

        /*
        // 이전으로 가기 버튼 클릭하면 메인 페이지로 이동
        to_main_button.setOnClickListener{
            val intent = Intent(this, Main::class.java)
            startActivity(intent)
        }
        */

    }
}

