package com.example.myapplication

class ChatData {
    var msg:String? = ""
    var nickname:String? = ""

    constructor(msg:String?,nickname:String?){
        this.msg = msg
        this.nickname = nickname
    }

    override fun toString(): String {
        return nickname + ":" + msg
    }
}