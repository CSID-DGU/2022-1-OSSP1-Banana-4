package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.row_chat.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

        //이름 채팅방 설정
        //이거는 매칭 화면에서 정보를 넣어주면 됨
        nickname = "이지호"
        chatNum = "0"

        var database = FirebaseDatabase.getInstance()
        myRef = database.getReference("message").child(chatNum).child("contents")


        //입력 했을 때
        chat_submit_button.setOnClickListener {
            addChat(chat_inputBox.text.toString(),nickname)
            chat_inputBox.setText("")
        }

        //뒤로 가기 버튼
        chat_back_button.setOnClickListener {
            val intent = Intent(this, MainPage::class.java)
            startActivity(intent)
        }

        //여기서 작성했던 채팅 목록들 가져옴
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


        //adapter 설정
        adapter.context = this
        adapter.myNickname = nickname
        //adapter.itemList.add(ChatData("안녕!", "nick1"))
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        chat_recyclerView.layoutManager = layoutManager
        chat_recyclerView.adapter = adapter


        //시간 설정
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분")
        val formatted = current.format(formatter)

        chat_date_textView.text = formatted
    }

    fun addChat(msg:String?, nickname:String?){
        var chatdata:ChatData = ChatData(msg, nickname)

        if (msg != null && !msg.equals("")){
            myRef.child(adapter.itemList.size.toString()).setValue(chatdata)
        }
    }
}