package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MatchingSuccess : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_success)


        val btn_back=findViewById<Button>(R.id.btn_back) //뒤로가기버튼
        btn_back.setOnClickListener({
            val intent= Intent(this, categoryPage::class.java) //다른 메뉴 찾아보기
            startActivity(intent)
        })

        val btn_done=findViewById<Button>(R.id.btn_done) //매칭 완료 채팅방생성
        btn_done.setOnClickListener({
            val intent= Intent(this, MainPage::class.java) //다시 매칭시도
            startActivity(intent)
        })







    }
}