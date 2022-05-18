package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_my_page.*
import kotlinx.android.synthetic.main.activity_review.*
import kotlinx.android.synthetic.main.activity_review.profile_image
import kotlinx.android.synthetic.main.activity_review.rating_bar
import kotlinx.android.synthetic.main.activity_review.username

class Review : AppCompatActivity()  {

    // firebase
    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var dbReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        auth = FirebaseAuth.getInstance()

        // firebase 데이터 연동
        val user = db.collection("users").document(auth.currentUser?.uid.toString())

        // MyPage.kt 에서 mateID 가져옴
        val mateID : String? = intent.extras?.getString("mate_id")

        // mate_id의 속성 불러오기
        val mate = db.collection("users").document(mateID.toString())

        var mateName : String? = null
        var uri : Uri? = null
        var mateGrade : Float = 0f
        var mateNum : Int = 0

        mate.get().addOnSuccessListener{ documentSnapshot ->
            mateName = documentSnapshot.get("userNickname").toString()
            uri = Uri.parse(documentSnapshot.get("userImage").toString())
            mateGrade = documentSnapshot.getDouble("userGrade")?.toFloat()!!
            mateNum = documentSnapshot.getDouble("mateNum")?.toInt()!!
        }

        // xml 파일에 매핑
        // 임시 코드
        username.text = intent.extras?.getString("mate_name")
        // 실제 코드
        // username.text = mateName
        profile_image.setImageURI(uri)

        // rating bar
        var grade : Float = 0f
        rating_bar.setOnRatingBarChangeListener{ rating_bar, rating, fromUser ->
            grade = rating
        }


        review_button.setOnClickListener{
            val intent = Intent(this,MyPage::class.java)

            // 별점 평점 update
            var new_mateNum : Int = mateNum + 1
            var new_mateGrade : Float = (grade+mateGrade) / new_mateNum

            dbReference.child("userGrade").push().setValue(new_mateGrade);
            dbReference.child("mateNum").push().setValue(new_mateNum);

            // 사용자 후기 update
            dbReference.child("userReview").push().setValue(review.text.toString());

            startActivity(intent)
        }

        // 이전으로 가기 버튼 클릭하면 마이 페이지로 이동
        to_my_page_button.setOnClickListener{
            val intent = Intent(this, MyPage::class.java)
            startActivity(intent)
        }
    }
}

