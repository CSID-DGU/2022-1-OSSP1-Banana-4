package com.example.myapplication.matching

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.concurrent.timer

fun main() {

    var userList: MutableList<WaitUserData>
    var failUserList : MutableList<WaitUserData> = mutableListOf()
    var userNum: Int
    var mateList = mutableListOf<MateData>()
    var second = 0
    // val mates: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Mates")

    timer(period = 10000, initialDelay = 10000) { // 10초 후 10초 마다 실행
        second += 10
        println("second : $second")
        // 임시로 초기화
        println("초기화")
        userList = mutableListOf()
        userNum = init(userList, failUserList)


        // 정렬
        sort(userList)
        userList.forEach { user ->
            println("uid=${user.uid}, grade=${user.grade}, rank=${user.rank}, brandList=${user.brandList}")
        }

        println()

        // 선호도 테이블 생성
        setPreferTable(userList, userNum)
        userList.forEach { user ->
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
        mateList = choice(userList)
        mateList.forEach { mate ->
            print("userList=[ ")
            mate.userList.forEach {
                print(it.uid)
            }
            println(" ]")
        }

        // mate Info firebase에 저장
        // moveChat(mateList, mates)

        if (second == 30) {
            cancel()
            println("종료 second : $second")
        }

        failUserList = failUser(userList)

    }
}

fun init(userList: MutableList<WaitUserData>, failUser:MutableList<WaitUserData>): Int { // 임시로 초기화함.
    var userNum: Int
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
    userNum = 7

    return userNum
}

// 정렬하기 : kotlin 내부 sort 클래스 이용, 퀵 정렬.
fun sort(userList: MutableList<WaitUserData>) {
    userList.sortWith(compareBy<WaitUserData> { it.grade }.thenBy { it.rank })
    userList.reverse()
}

// 사용자 간의 브랜드 유사도 테이블(선호도 테이블) 작성.
fun setPreferTable(userList: MutableList<WaitUserData>, userNum: Int) {
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

// 우선순위에 따라 선택한 후 mate table return
fun choice(userList: MutableList<WaitUserData>): MutableList<MateData> {
    var mateList: MutableList<MateData> = mutableListOf()
    var finishList: MutableList<WaitUserData> = mutableListOf()
    var mate = MateData(mutableListOf())
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
                    println("matching됨" + user.uid + "와" + j.uid)
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
        }
        mateList.add(mate)
        mate = MateData(mutableListOf())
    }
    return mateList
}

// 매칭 완료된 사용자들을 firebase에 저장(userId)
fun moveChat(mateList: MutableList<MateData>, mates : DatabaseReference) {
    val index = 0
    for (mate in mateList) {
        var mateIndex = mates.child("$index")
        var hashMap: HashMap<String, Any> = HashMap()
        hashMap["userList"] = mate
        mateIndex.setValue(hashMap).addOnCompleteListener {
            if (it.isSuccessful) {
                // 매칭 성공 페이지로 이동
            }
        }
        println(hashMap)
    }
}

fun failUser(userList: MutableList<WaitUserData>) : MutableList<WaitUserData>{
    var failUserList : MutableList<WaitUserData> = mutableListOf()
    for(user in userList){
        if(user.fail>0){
            failUserList.add(user)
        }
    }
    return failUserList
}


