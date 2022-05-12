package com.example.myapplication

import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.databinding.ActivityMyPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_my_page.*


class MyPage : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding:ActivityMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        /*
        * 0. 현재 사용자 정보
        * 1. 프로필 사진
        * 2. 사용자 닉네임
        * 3. 사용자 점수
        * 4. 사용자가 받은 평점
        * 5. 사용자의 메이트
        */

        // firebase 데이터 연동
        val dataRef = db.collection("users").document(auth.currentUser?.uid.toString())

        dataRef.get().addOnSuccessListener{ documentSnapshot ->
            username.setText(documentSnapshot.get("username").toString())
        }


        /*binding.ratingBar.rating = myProfile.*/


    }



}
