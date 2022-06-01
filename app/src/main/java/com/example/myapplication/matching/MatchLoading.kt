package com.example.myapplication.matching

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.MainPage
import com.example.myapplication.R

class MatchLoading : AppCompatActivity() {

    private lateinit var loading : ImageView
    private lateinit var stopButton: Button
    private lateinit var waitList: MutableList<WaitUserData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_loading)

        loading = findViewById(R.id.loading)
        stopButton = findViewById(R.id.stop_button)
        // loading.gif 파일 불러오기
        Glide.with(this).load(R.raw.loading).circleCrop().into(loading)


        stopButton.setOnClickListener {
            val intent = Intent(this, MainPage::class.java)
            // 매칭 버튼 취소 클릭 시 요청 큐에서 삭제

            showToast("매칭이 취소되었습니다.")
            startActivity(intent)
        }

    }
    private fun showToast(msg:String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}