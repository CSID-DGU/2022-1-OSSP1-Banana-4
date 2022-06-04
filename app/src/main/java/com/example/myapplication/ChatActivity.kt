package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.row_chat.*

class ChatActivity : AppCompatActivity() {
    private val adapter = CharAdapter()
    lateinit var nickname: String
    lateinit var chatNum: String
    lateinit var myRef: DatabaseReference
    lateinit var photo_url:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        var auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null){
            photo_url = auth.currentUser!!.photoUrl.toString()
            println(photo_url)
        }

        nickname = "홍연주"
        chatNum = "1"
        var database = FirebaseDatabase.getInstance()
        myRef = database.getReference("message").child(chatNum).child("contents")

        chat_submit_button.setOnClickListener {
            addChat(chat_inputBox.text.toString(),nickname)
        }

        myRef.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var dataHash = snapshot.getValue() as HashMap<String, String>
                println(dataHash)
                adapter.itemList.add(ChatData(dataHash["msg"], dataHash["nickname"]))
                chat_recyclerView.adapter = adapter
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })


        //유저 정보를 자동으로 가져와서 넣어줭
        adapter.myNickname = nickname
        //adapter.itemList.add(ChatData("안녕!", "nick1"))
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        chat_recyclerView.layoutManager = layoutManager
        chat_recyclerView.adapter = adapter
    }

    fun addChat(msg:String?, nickname:String?){
        var chatdata:ChatData = ChatData(msg, nickname)

        if (msg != null && !msg.equals("")){
            myRef.child(adapter.itemList.size.toString()).setValue(chatdata)
        }
    }
}