package com.example.myapplication.matching

import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
fun main() {

/*    var userList: MutableList<WaitUserData> = mutableListOf()
    var userNum = 0
    var mateList = mutableListOf<MateData>()
    var second = 0
    val gradeMin : Float = 0f
    val gradeMax : Float = 5f
    val brandRange = 0..237
    var ranGrade = 0f
    var ranBrandList = Random().nextInt(6)+1

    for(i in 100..1000 step 100){
        for(j in 0 until i){
            ranGrade = gradeMin + Random.nextFloat() * (gradeMax - gradeMin)
            userList.add(WaitUserData("", ranGrade,j,mutableListOf()))
        }

    }
    val user1 = WaitUserData("1", 0f, 5, mutableListOf(1, 3, 5))
    val user2 = WaitUserData("2", 1f, 4, mutableListOf(1, 3, 7))
    val user3 = WaitUserData("3", 4.7f, 3, mutableListOf(4, 7))
    val user4 = WaitUserData("4", 4.5f, 2, mutableListOf(5, 6, 4))
    val user5 = WaitUserData("5", 4f, 1, mutableListOf(0))
    val user6 = WaitUserData("6", 2f, 0, mutableListOf(2, 1))
    userList.add(user1)
    userList.add(user2)
    userList.add(user3)
    userList.add(user4)
    userList.add(user5)
    userList.add(user6)
    userNum = 6

    val measuredTime = measureTimedValue {
        // input code
        // 정렬
        userList.sortWith(compareBy<WaitUserData> { it.grade }.thenBy { it.rank })
        userList.reverse()
    }
    println("result : ${measuredTime.value}, measured time : ${measuredTime.duration}")

    val measuredTime1 = measureTimedValue {
        // 선호도 테이블 생성
        var sameNum: Int
        for (i in 0 until userNum - 1) {
            for (j in i + 1 until userNum) { // 자기 자신 다음 user 부터 마지막 user 까지 비교
                sameNum = 0
                for (a in userList[i].brandList) {
                    if (a == 0) {
                        sameNum = 3
                    } else {
                        for (b in userList[j].brandList) {
                            if (b == 0) {
                                sameNum = 3
                            } else if (b == a) {
                                sameNum++
                            }
                        }
                    }
                }
                userList[i].preferTable[sameNum].add(userList[j])
                userList[j].preferTable[sameNum].add(userList[i])
            }
        }
    }
    println("result : ${measuredTime1.value}, measured time : ${measuredTime1.duration}")


    val measuredTime2 = measureTimedValue {
        // 선택
        var finishList: MutableList<WaitUserData> = mutableListOf()
        var mate = MateData(" ",mutableListOf())
        var success: Int

        for (user in userList) {
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
                        mate.userList.add(j)
                        finishList.add(j)
                    }
                }
                if (success > 0) {
                    break
                }
            }
            mateList.add(mate)
            mate = MateData(" ",mutableListOf())
        }
    }
    println("result : ${measuredTime2.value}, measured time : ${measuredTime2.duration}")

    // print
    userList.forEach { user ->
        println("uid=${user.uid}, grade=${user.grade}, rank=${user.rank}, brandList=${user.brandList}, fail=${user.fail}")
    }*/

}