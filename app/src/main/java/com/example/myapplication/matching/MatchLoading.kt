package com.example.myapplication.matching

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.R

class MatchLoading : AppCompatActivity() {

    private lateinit var loading : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_loading)

        loading = findViewById(R.id.loading)

        // loading.gif 파일 불러오기
       Glide.with(this).load(R.raw.loading).circleCrop().into(loading)
    }
}