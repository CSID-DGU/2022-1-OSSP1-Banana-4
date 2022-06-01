package com.example.myapplication.matching

data class User(
    val uid: String,
    val grade: Float,
    val rank: Int,
    val brandList: MutableList<Int>,
    val preferTable: ArrayList<MutableList<User>> = arrayListOf(
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf()
    ),
    var success : Boolean = false
)

data class Mate(
    val userList: MutableList<User>
)

fun main() {
    var userList: MutableList<User> = mutableListOf()
    var userNum: Int = init(userList)
    var mateList = mutableListOf<Mate>()

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


}

fun init(userList: MutableList<User>): Int { // 임시로 초기화함.
    var userNum: Int
    val user1: User = User("1", 0f, 5, mutableListOf(1, 3, 5))
    val user2: User = User("2", 1f, 4, mutableListOf(1, 3,7))
    val user3: User = User("3", 4.7f, 3, mutableListOf(4, 7))
    val user4: User = User("4", 4.5f, 2, mutableListOf(5, 6,4))
    val user5: User = User("5", 4f, 1, mutableListOf(0))
    val user6 : User = User("6",2f,0, mutableListOf(2,1))
    userList.add(user1)
    userList.add(user2)
    userList.add(user3)
    userList.add(user4)
    userList.add(user5)
    userList.add(user6)
    userNum = 6

    return userNum
}

// 정렬하기 : kotlin 내부 sort 클래스 이용, 퀵 정렬.
fun sort(userList: MutableList<User>) {
    userList.sortWith(compareBy<User> { it.grade }.thenBy { it.rank })
    userList.reverse()
}

// 사용자 간의 브랜드 유사도 테이블(선호도 테이블) 작성.
fun setPreferTable(userList: MutableList<User>, userNum: Int) {
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

fun choice(userList: MutableList<User>): MutableList<Mate> {
    var mateList: MutableList<Mate> = mutableListOf()
    var finishList:MutableList<User> = mutableListOf()
    var mate = Mate(mutableListOf())
    var success: Int

    for(user in userList){
        if(finishList.contains(user)) {
            continue
        }
        success = 0
        mate.userList.add(user)
        finishList.add(user)
        for (i in 3 downTo 1) { // 겹치는 개수가 3개->2개->1개
            for (j in user.preferTable[i]) {
                if (success >= 2) { // 2명까지만 choice
                    break
                }
                else if (!finishList.contains(j)) {
                    println("matching됨"+user.uid+"와"+j.uid)
                    mate.userList.add(j)
                    success++
                    user.success = true
                    j.success = true
                    finishList.add(j)
                }
            }
            if (success > 0) {
                break
            }
        }
        mateList.add(mate)
        mate = Mate(mutableListOf())
    }
    return mateList
}

/*

4. 매칭이 완료된 사용자들을 위한 채팅방이 개설되고, 자동으로 참가하게 된다.
    - 매칭 성공 페이지에서 userId를 통해 정보 불러옴(mate가 선택한 brand 정보 띄워줌)
5. 매칭이 실패한 사용자는 가중치(0.5점)를 부여하여 임시로 평점을 상승시켜 매칭 확률을 높여준다. 이후, 다시 한번 매칭을 시작한다.
    - 다음 매칭 큐로 들어가고 userGrade +0.5
*/


