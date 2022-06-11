package com.example.myapplication.matching

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.MainPage
import com.example.myapplication.MyPage
import com.example.myapplication.R
import com.example.myapplication.Review
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.concurrent.timer

class MatchLoading : AppCompatActivity() {

    private lateinit var loading : ImageView
    private var matching : Matching = Matching()
    private lateinit var auth: FirebaseAuth
    var waitUsersNum : Int = 0
    var isSuccess = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_loading)

        auth = FirebaseAuth.getInstance()
        loading = findViewById(R.id.loading)
        // loading.gif 파일 불러오기
        Glide.with(this).load(R.raw.loading).circleCrop().into(loading)

        val waitUsers = FirebaseDatabase.getInstance().getReference("WaitUsers")
        val waitUserNum = FirebaseDatabase.getInstance().getReference("WaitUsers").child("waitUserNum")
        val uid = auth.currentUser?.uid.toString()
        val finish = FirebaseDatabase.getInstance().getReference("FinishedMatch")

        // intent로 넘어와야 할 값
        // 1. failedNum = 0, 2. 선택한 brandList 3. grade
        var failedNum = intent.extras?.getInt("failedNum").toString().toInt()
        println("자고싶다 : $failedNum")
        var grade = intent.extras?.getFloat("grade").toString().toFloat()
        val brandList : ArrayList<Int>? = intent.extras?.getIntegerArrayList("brandList")
        /*var failedNum
        var grade = 3.5
        val brandList : ArrayList<Int>? = arrayListOf()*/

        if(failedNum>0){ // 실패한 유저가 다시 들어오는 경우에는 대기열에 새로 추가해줘야 함.
            waitUsers.child("waitUsersNum").get().addOnSuccessListener{
                waitUsers.child(it.value.toString()).child("uid").setValue(uid)
                waitUsers.child(it.value.toString()).child("grade").setValue(grade+0.5*failedNum)
                waitUsers.child(it.value.toString()).child("brandList").setValue(brandList)
            }
        }

        // waitUsers.child("0").child("uid").toString().equals(uid)

        waitUserNum.get().addOnSuccessListener {
            waitUsersNum = it.value.toString().toInt()
            println("userNum : ${it.value}")
            println("value : $waitUsersNum" )
            if(true){
                // 총대
                /*timer(period = 1000,initialDelay = 10000){
                    cancel()
                }*/
                println("2value : $waitUsersNum" )
                match(matching, waitUsers, waitUsersNum) // 매칭 & 대기열 리셋

                Handler(Looper.getMainLooper()).postDelayed({
                println("matching.getMateList()")
                // 성공한 사람들
                for(team in matching.getMateList()){
                    team.id = team.userList[0].uid
                    for(user in team.userList){
                        // user matchedUsers push
                        println("userid : ${user.uid}")
                        finish.child(user.uid).child("isSuccess").setValue(true)
                        finish.child(user.uid).child("teamID").setValue(team.id)
                    }
                }
                // 실패한 사람들
                for(failUser in matching.getFailUserList()){
                    finish.child(failUser.uid).child("isSuccess").setValue(false)
                } }, 3000)
            }else{ // 대기열에 추가만 되고 총대 x
                // 총대를 멘 user가 매칭을 끝낼 때까지 기다린다.
                timer(period = 1000, initialDelay = 10000){
                    finish.get().addOnSuccessListener {
                        it.children.forEach{
                            if(uid.equals(it.key)){ // finish
                                cancel()
                            }
                        }
                    }
                }
            }

            Handler(Looper.getMainLooper()).postDelayed({
                finish.child(uid).child("isSuccess").get().addOnSuccessListener {
                    isSuccess = it.value.toString().toBoolean()
                    println("isSuccess : $isSuccess")
                    // 내가 성공 인지 실패 인지 검사
                    if(isSuccess) {
                        // val intent = Intent(this, MatchingSuccess::class.java)
                        val intent = Intent(this, MyPage::class.java)
                        finish.child(uid).child("teamID").get().addOnSuccessListener {
                            intent.putExtra("teamID", it.value.toString())
                        }
                        finish.child(uid).removeValue()
                        startActivity(intent)
                    }else {
                        if(failedNum<5){
                            finish();//인텐트 종료
                            overridePendingTransition(0, 0);//인텐트 효과 없애기
                            val recIntend = getIntent()
                            recIntend.putExtra("failedNum",failedNum+1)
                            recIntend.putExtra("grade", grade)
                            recIntend.putExtra("brandList", brandList)
                            finish.child(uid).removeValue()
                            startActivity(recIntend); //액티비티 열기
                            overridePendingTransition(0, 0);//인텐트 효과 없애기
                        }else{// 횟수가 넘어가면 실패 페이지 전환
                            // val intent = Intent(this, MatchFailed::class.java)
                            finish.child(uid).removeValue()
                            startActivity(intent)
                        }
                    }
                }

            }, 3000)
        }
    }
    private fun match(matching : Matching, waitUsers : DatabaseReference, waitUsersNum : Int){

        // 초기화
        matching.initData(waitUsers, waitUsersNum)
        // waitUsers.removeValue()
        Handler(Looper.getMainLooper()).postDelayed({
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
        }, 3000)


        // 임시 초기화
        // matching.init()


    }
    private fun showToast(msg:String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}