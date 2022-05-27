package com.example.myapplication

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MatchLoading : AppCompatActivity() {

    // firebase
    private lateinit var auth: FirebaseAuth

    private lateinit var loading : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_loading)

        loading = findViewById(R.id.loading)
        // firebase 데이터 연동
        val uid = auth.currentUser?.uid.toString()
        val user = FirebaseDatabase.getInstance().getReference("Users").child(uid)

        Glide.with(this).load(R.raw.loading1).into(loading)
    }
}