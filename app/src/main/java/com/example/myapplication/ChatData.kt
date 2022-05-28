package com.example.myapplication

class ChatData {
    var msg:String? = null
    var nickname:String? = null

    constructor(msg:String?,nickname:String?){
        this.msg = msg
        this.nickname = nickname
    }

    fun getMeassege():String?{
        return this.msg
    }

    fun getNickName():String?{
        return this.nickname
    }
}