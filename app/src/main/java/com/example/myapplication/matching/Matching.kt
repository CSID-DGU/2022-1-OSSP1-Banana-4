package com.example.myapplication.matching

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Transaction
import kotlin.concurrent.timer

class Matching{
    private var userList: MutableList<WaitUserData> = mutableListOf()
    private var failUserList : MutableList<WaitUserData> = mutableListOf()
    private var waitUserNum: Int = 0
    private var mateList = mutableListOf<MateData>()

    fun getUserList() : MutableList<WaitUserData>{
        return this.userList
    }
    fun getFailUserList() : MutableList<WaitUserData>{
        return this.failUserList
    }
    fun getWaitUserNum() : Int{
        return this.waitUserNum
    }
    fun getMateList() : MutableList<MateData>{
        return this.mateList
    }

    // 초기화
    fun initData(waitUsers : DatabaseReference, waitUsersNum : Int) : Int{
        this.waitUserNum = waitUsersNum
        for(rank in 0 until waitUsersNum){
            val user = waitUsers.child(rank.toString())

            val uid = user.child("uid").toString()
            val grade = user.child("grade").toString().toFloat()
            val brandList : MutableList<Int> = mutableListOf()
            user.child("brandList").get().addOnSuccessListener {
                for(brand in it.children){
                    brandList.add(brand.value.toString().toInt())
                }
            }
            val waitUser = WaitUserData(uid, grade,rank, brandList)
            this.userList.add(waitUser)
        }
        return waitUsersNum
    }
/*
// 임시로 초기화
    fun init(userList: MutableList<WaitUserData>, failUser:MutableList<WaitUserData>): Int {
        val user1 = WaitUserData("1", 0f, 5, mutableListOf(1, 3, 5))
        val user2 = WaitUserData("2", 1f, 4, mutableListOf(1, 3, 7))
        val user3 = WaitUserData("3", 4.7f, 3, mutableListOf(4, 7))
        val user4 = WaitUserData("4", 4.5f, 2, mutableListOf(5, 6, 4))
        val user5 = WaitUserData("5", 4f, 1, mutableListOf(0))
        val user6 = WaitUserData("6", 2f, 0, mutableListOf(2, 1))
        val user7 = WaitUserData("7",4.3f,-1,mutableListOf(8)) // 매칭에 실패할 유저
        for(user in failUser){
            // 매칭에 실패한 유저 추가
            print(user.uid)
        }
        userList.add(user1)
        userList.add(user2)
        userList.add(user3)
        userList.add(user4)
        userList.add(user5)
        userList.add(user6)
        userList.add(user7)
        waitUserNum = 7

        return waitUserNum
    }*/

    // 정렬하기 : kotlin 내부 sort 클래스 이용, 퀵 정렬.
    fun sort() {
        this.userList.sortWith(compareBy<WaitUserData> { it.grade }.thenBy { it.rank })
        this.userList.reverse()
    }

    // 사용자 간의 브랜드 유사도 테이블(선호도 테이블) 작성.
    fun setPreferTable() {
        var sameNum: Int
        for (i in 0 until this.waitUserNum - 1) {
            for (j in i + 1 until this.waitUserNum) { // 자기 자신 다음 user 부터 마지막 user 까지 비교
                sameNum = 0
                for (a in this.userList[i].brandList) {
                    if (a == 0) {
                        sameNum = 3
                    } else {
                        for (b in this.userList[j].brandList) {
                            if (b == 0) {
                                sameNum = 3
                            } else if (b == a) {
                                sameNum++
                            }
                        }
                    }
                }
                this.userList[i].preferTable[sameNum].add(this.userList[j])
                this.userList[j].preferTable[sameNum].add(this.userList[i])
            }
        }
    }

    // 우선순위에 따라 선택한 후 mate table return
    fun choice(): MutableList<MateData> {
        var mateList: MutableList<MateData> = mutableListOf()
        var finishList: MutableList<WaitUserData> = mutableListOf()
        var mate = MateData(mutableListOf())
        var success: Int

        for (user in this.userList) {
            if (finishList.contains(user)) {
                continue
            }
            success = 0
            mate.userList.add(user)
            finishList.add(user)
            for (i in 3 downTo 1) { // 겹치는 개수가 3개->2개->1개
                for (j in user.preferTable[i]) {
                    if (success >= 2) { // 2명까지만 choice
                        break
                    } else if (!finishList.contains(j)) {
                        // println("matching됨" + user.uid + "와" + j.uid)
                        mate.userList.add(j)
                        success++
                        finishList.add(j)
                    }
                }
                if (success > 0) {
                    break
                }
            }
            if(success <=0 ){ // 매칭 실패자라면
                user.fail++
                user.grade+=0.5f
            }
            mateList.add(mate)
            mate = MateData(mutableListOf())
        }
        this.mateList = mateList
        return mateList
    }

    fun setFailUser(){
        for(user in this.userList){
            if(user.fail>0){
                this.failUserList.add(user)
            }
        }
    }
}