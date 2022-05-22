package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_my_page.*

class MyPage : AppCompatActivity() {

    // firebase
    private lateinit var auth: FirebaseAuth

    // review recyclerview
    private lateinit var reviewAdapter: ReviewAdapter
    private val reviews = mutableListOf<ReviewData>()

    // mate recyclerview
    lateinit var mateAdapter:MateAdapter
    private val mates = mutableListOf<MateData>()

    private lateinit var ratingbar :RatingBar
    private lateinit var username : TextView
    private lateinit var profileImage : CircleImageView
    private var grade : Float = 0.0f
    private var reviewList = mutableListOf<String>()
    private var mateList = mutableListOf<String>()
    private var mateNum = 0

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        auth = FirebaseAuth.getInstance()
        username = findViewById(R.id.username)
        profileImage = findViewById(R.id.profile_image)
        ratingbar = findViewById(R.id.rating_bar)

        // firebase 데이터 연동
        val uid = auth.currentUser?.uid.toString()
        val user = FirebaseDatabase.getInstance().getReference("Users").child(uid)

        // 현재 user의 속성 불러오기
        user.get().addOnSuccessListener { dataSnapshot ->
            // 임시 코드
            username.text = "Apple"

            // 실제 코드
            username.text = dataSnapshot.child("userNickname").toString()           // 닉네임
            val uri : Uri = Uri.parse(dataSnapshot.child("userImage").toString())   // 프로필이미지 uri
            profileImage.setImageURI(uri)                                                // profile_image URI를 uri로 설정
            grade = dataSnapshot.child("userGrade").toString().toFloat()            // 평점
            reviewList = dataSnapshot.child("userReview") as MutableList<String>    // 리뷰
            mateList = dataSnapshot.child("userMate") as MutableList<String>        // 메이트 리스트
            mateNum=mateList.count()
        }

        // rating_bar 초기화
        // 임시 코드
        ratingbar.rating = 2f

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
                    intent.putExtra("mate_id", uid)
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
                    intent.putExtra("mate_id", uid)
                    intent.putExtra("mate_name","Tomato")
                    startActivity(intent)
                }
            }

            // 실제 코드
            /*
            for(i in 0 until mateNum) {
                val mate = FirebaseDatabase.getInstance().getReference("Users").child(mateList[i])
                var username : String =""
                var uri : Uri = Uri.parse("")

                mate.get().addOnSuccessListener { dataSnapshot ->
                    username = dataSnapshot.child("userNickname").toString()
                    uri = Uri.parse(dataSnapshot.child("userImage").toString())
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


