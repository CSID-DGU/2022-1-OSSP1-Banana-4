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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.concurrent.timer

class MatchLoading : AppCompatActivity() {

    private lateinit var loading : ImageView
    private lateinit var stopButton: Button
    lateinit var matching : Matching
    private lateinit var auth: FirebaseAuth
    var waitUsersNum : Int = 0
    var isFailed : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_loading)

        loading = findViewById(R.id.loading)
        // loading.gif 파일 불러오기
        Glide.with(this).load(R.raw.loading).circleCrop().into(loading)

        val waitUsers = FirebaseDatabase.getInstance().getReference("WaitUsers").child("List")
        FirebaseDatabase.getInstance().getReference("WaitUsers").child("WaitUserNum").get().addOnSuccessListener {
            waitUsersNum = it.toString().toInt()
        }
        val matchedUsers = FirebaseDatabase.getInstance().getReference("MatchedUsers")
        val uid = auth.currentUser?.uid.toString()

        /*waitUsers.child("0").child("uid").toString().equals(uid)*/
        if(true){
            // 총대
            // 10초 간 기다림
            match(matching, waitUsers, waitUsersNum)
            // 대기열 리셋

            // mate Info firebase 에 저장
            for(team in matching.getMateList()){
                // team push
                matchedUsers.child(team.id).setValue(team)
                for(user in team.userList){
                    // user matchedUsers push
                    matchedUsers.child(team.id).child("user.uid").setValue(user)
                }
            }
            // 매칭 실패자
            if(matching.isSuccess(uid)) {
                // 성공이면 성공페이지 전환, teamID 값 보냄
            }else{
                // 실패면 실패 페이지 전환 or 다시 대기열에 넣어줌(4번까지)
                /*
                finish();//인텐트 종료
                    overridePendingTransition(0, 0);//인텐트 효과 없애기
                    Intent intent = getIntent(); //인텐트
                    startActivity(intent); //액티비티 열기
                    overridePendingTransition(0, 0);//인텐트 효과 없애기
                 */
            }
        }else{ // 대기열에 추가만 되고 총대 x
            // 총대를 멘 user가 매칭을 끝낼 때까지 기다린다.
            timer(period = 1000, initialDelay = 10000){

            }
        }

        // 내가 성공 인지 실패 인지 검사

    }
    private fun match(matching : Matching, waitUsers : DatabaseReference, waitUsersNum : Int){

        // 초기화
        // matching.initData(waitUsers, waitUsersNum)
        // waitUsers.removeValue()

        // 임시 초기화
        matching.init()

        // 정렬
        matching.sort()
        // print
        matching.getUserList().forEach { user ->
            println("uid=${user.uid}, grade=${user.grade}, rank=${user.rank}, brandList=${user.brandList}")
        }
        println()

        // 선호도 테이블 생성
        matching.setPreferTable()
        // print
        matching.getUserList().forEach { user ->
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
        println()

        // 선택
        matching.choice()
        // print
        matching.getMateList().forEach { mate ->
            print("userList=[ ")
            mate.userList.forEach {
                print(it.uid)
            }
            println(" ]")
        }
        println()

        // failUser failList에 저장
        matching.setFailUser()

        // print
        matching.getUserList().forEach { user ->
            println("uid=${user.uid}, grade=${user.grade}, rank=${user.rank}, brandList=${user.brandList}, fail=${user.fail}")
        }
    }
    private fun showToast(msg:String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}