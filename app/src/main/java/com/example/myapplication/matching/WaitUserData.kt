package com.example.myapplication.matching

data class WaitUserData (
    val uid: String,
    var grade: Float,
    val rank: Int,
    val brandList: MutableList<Int>,
    val preferTable: ArrayList<MutableList<WaitUserData>> = arrayListOf(
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf()
    ),
    var fail : Int = 0
)
