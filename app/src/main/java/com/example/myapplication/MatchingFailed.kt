package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MatchingFailed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_failed)


        val btn_back=findViewById<Button>(R.id.btn_back) //매칭 시작 버튼 일단 메인페이지가게설정
        btn_back.setOnClickListener({
            val intent= Intent(this, categoryPage::class.java) //다른 메뉴 찾아보기
            startActivity(intent)
        })

        val btn_again=findViewById<Button>(R.id.btn_again) //매칭 시작 버튼 일단 메인페이지가게설정
        btn_again.setOnClickListener({
            val intent= Intent(this, MainPage::class.java) //다시 매칭시도
            startActivity(intent)
        })


    }
}