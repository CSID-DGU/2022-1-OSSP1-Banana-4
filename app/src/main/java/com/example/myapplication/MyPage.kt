package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_my_page.*
import kotlin.properties.Delegates

class MyPage : AppCompatActivity() {

    // firebase
    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var dbReference: DatabaseReference

    // review recyclerview
    lateinit var reviewAdapter: ReviewAdapter
    private val reviews = mutableListOf<ReviewData>()

    // mate recyclerview
    lateinit var mateAdapter:MateAdapter
    private val mates = mutableListOf<MateData>()

    private lateinit var ratingbar :RatingBar
    private var grade : Float = 0.0f
    private var reviewList = mutableListOf<String>()
    private var mateList = mutableListOf<String>()
    private var mateNum = 0

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        auth = FirebaseAuth.getInstance()
        ratingbar = findViewById(R.id.rating_bar)

        // firebase 데이터 연동
        val user = db.collection("users").document(auth.currentUser?.uid.toString())

        // 현재 user의 속성 불러오기
        user.get().addOnSuccessListener{ documentSnapshot ->
            username.text = documentSnapshot.get("userNickname").toString()         // 닉네임
            val uri : Uri = Uri.parse(documentSnapshot.get("userImage").toString()) // 프로필이미지 uri
            profile_image.setImageURI(uri)                                          // profile_image URI를 uri로 설정
            grade = documentSnapshot.getDouble("userGrade")?.toFloat()!!       // 평점
            reviewList = documentSnapshot.get("userReview") as MutableList<String>  // 리뷰
            mateList = documentSnapshot.get("userMate") as MutableList<String>      // 메이트 리스트
            mateNum=mateList.count()                                                // 메이트 수(mateList의 수)
        }

        // rating_bar 초기화
        // 임시 코드
        rating_bar.rating = 2f

        // 실제 코드
        // rating_bar.rating = grade
        ratingbar.onRatingBarChangeListener

        // review adapter
        reviewAdapter = ReviewAdapter(this)
        my_review.adapter = reviewAdapter

        reviews.apply{
            // 임시 코드
            add(ReviewData("- 돈을 너무 늦게 줍니다."))
            add(ReviewData("- 주문을 너무 잘 해주셨습니다."))
            add(ReviewData("- 너무 친절해요!"))
            add(ReviewData("- 응답이 너무 느려요.."))

            // 실제 코드
            /*
            for(i in 0 until reviewList.count()) {
                add(ReviewData(reviewList[i]))
            }
            */
            reviewAdapter.reviews = reviews
            reviewAdapter.notifyDataSetChanged()
        }

        // mate adapter
        mateAdapter = MateAdapter(this)
        user_mate.adapter=mateAdapter

        mates.apply {
            // 임시코드
            val uri: Uri = Uri.parse("test")
            add(MateData("", "Apple", uri))
            mateAdapter.listener = object : OnClickListener {
                override fun btnClick(
                    holder: MateAdapter.ViewHolder?,
                    view: View,
                    position: Int
                ) {
                    val intent = Intent(view.context, Review::class.java)
                    intent.putExtra("mate_id", user.id)
                    intent.putExtra("mate_name","Apple")
                    startActivity(intent)
                }
            }
            add(MateData("","Tomato",uri))
            mateAdapter.listener = object : OnClickListener {
                override fun btnClick(
                    holder: MateAdapter.ViewHolder?,
                    view: View,
                    position: Int
                ) {
                    val intent = Intent(view.context, Review::class.java)
                    intent.putExtra("mate_id", user.id)
                    intent.putExtra("mate_name","Tomato")
                    startActivity(intent)
                }
            }

            // 실제 코드
            /*
            for(i in 0 until mateNum) {
                val mate = db.collection("users").document(mateList[i])
                var username : String =""
                var uri : Uri = Uri.parse("")
                mate.get().addOnSuccessListener { documentSnapshot ->
                    username = documentSnapshot.get("userNickname").toString()
                    uri = Uri.parse(documentSnapshot.get("userImage").toString())
                }
                add(MateData(mateList[i], username, uri))
                mateAdapter.listener = object : OnClickListener {
                    override fun btnClick(
                        holder: MateAdapter.ViewHolder?,
                        view: View,
                        position: Int
                    ) {
                        val intent = Intent(view.context, Review::class.java)
                        intent.putExtra("mate_id", mateList[i])
                        startActivity(intent)
                    }
                }
            }
             */
            mateAdapter.mates = mates
            mateAdapter.notifyDataSetChanged()
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


