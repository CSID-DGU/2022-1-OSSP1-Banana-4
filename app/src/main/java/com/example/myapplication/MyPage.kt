package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_my_page.*

class MyPage : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    // review recyclerview
    lateinit var reviewAdapter: ReviewAdapter
    private val reviews = mutableListOf<ReviewData>()

    private lateinit var ratingbar :RatingBar

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

        ratingbar = findViewById(R.id.rating_bar)

        // firebase 데이터 연동
        val user = db.collection("users").document(auth.currentUser?.uid.toString())

        user.get().addOnSuccessListener{ documentSnapshot ->
            username.text = documentSnapshot.get("userNickname").toString()
            val uri : Uri = Uri.parse(documentSnapshot.get("userImage").toString())
            profile_image.setImageURI(uri)
            // rating_bar.rating = documentSnapshot.getDouble("userGrade")?.toFloat()!!
        }

        rating_bar.rating = 2f
        ratingbar.onRatingBarChangeListener

        // review 불러오기
        reviewAdapter = ReviewAdapter(this)
        my_review.adapter = reviewAdapter

        reviews.apply{
            add(ReviewData("- 돈을 너무 늦게 줍니다."))
            add(ReviewData("- 주문을 너무 잘 해주셨습니다."))
            add(ReviewData("- 너무 친절해요!"))
            add(ReviewData("- 응답이 너무 느려요.."))

            reviewAdapter.reviews = reviews
            reviewAdapter.notifyDataSetChanged()
        }

        // mate review button 클릭하면 review.kt 로 메이트 uid 데이터 전달 & 이동
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

