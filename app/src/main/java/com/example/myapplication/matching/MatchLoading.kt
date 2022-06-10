package com.example.myapplication.matching

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.MainPage
import com.example.myapplication.R
import com.google.firebase.database.FirebaseDatabase

class MatchLoading : AppCompatActivity() {

    private lateinit var loading : ImageView
    private lateinit var stopButton: Button
    lateinit var matching : Matching

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_loading)

        loading = findViewById(R.id.loading)
        stopButton = findViewById(R.id.stop_button)
        // loading.gif 파일 불러오기
        Glide.with(this).load(R.raw.loading).circleCrop().into(loading)

        match(matching)

        stopButton.setOnClickListener {
            val intent = Intent(this, MainPage::class.java)

            // 매칭 버튼 취소 클릭 시 요청 큐에서 삭제하기

            showToast("매칭이 취소되었습니다.")
            startActivity(intent)
        }
    }
    private fun match(matching : Matching){
        val waitUsers = FirebaseDatabase.getInstance().getReference("WaitUsers").child("List")
        val waitUsersNum = FirebaseDatabase.getInstance().getReference("WaitUsers").child("WaitUserNum").toString().toInt()

        // 초기화
        matching.initData(waitUsers, waitUsersNum)

        // 초기화를 제외한 함수들은 10초(?)에 한 번씩 반복
        // 정렬
        matching.sort()
/*        matching.getUserList().forEach { user ->
            println("uid=${user.uid}, grade=${user.grade}, rank=${user.rank}, brandList=${user.brandList}")
        }

        println()*/

        // 선호도 테이블 생성
        matching.setPreferTable()
/*        matching.getUserList().forEach { user ->
            print("uid=${user.uid}, grade=${user.grade}, rank=${user.rank}, brandList=${user.brandList}, preferTable=")
            user.preferTable.forEach { list ->
                print("[ ")
                list.forEach {
                    print(it.uid)
                }
                print(" ]")
            }
            println()
        }

        println()*/

        // 선택
        matching.choice()
/*        matching.getMateList().forEach { mate ->
            print("userList=[ ")
            mate.userList.forEach {
                print(it.uid)
            }
            println(" ]")
        }

        println()*/

        // mate Info firebase에 저장

        matching.setFailUser()

/*        matching.getUserList().forEach { user ->
            println("uid=${user.uid}, grade=${user.grade}, rank=${user.rank}, brandList=${user.brandList}, fail=${user.fail}")
        }*/
    }
    private fun showToast(msg:String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}