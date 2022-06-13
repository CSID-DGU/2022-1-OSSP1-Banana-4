package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_main_page.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.typeOf

class ChatActivity : AppCompatActivity() {
    private val adapter = CharAdapter()
    lateinit var nickname: String
    lateinit var chatNum: String
    lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        var database = FirebaseDatabase.getInstance()
        var auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()
        //이름 채팅방 설정
        //이거는 매칭 화면에서 정보를 넣어주면 됨
        nickname = "김효정"
        chatNum = "0"

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                dataSnapshot.children.forEach {
                    adapter.myuid = it.key!!
                    chat_recyclerView.adapter = adapter
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        myRef = database.getReference("Users")
        myRef.addValueEventListener(postListener)


        myRef = database.getReference("message").child(chatNum).child("contents")
        //database.getReference("message").child(chatNum).child("images").child("nick1").setValue("~~.png")
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

        //나가기 버터느
        chat_quit_button.setOnClickListener {
            database.getReference("message").child(chatNum).removeValue()
        }

        //여기서 작성했던 채팅 목록들 가져옴
        myRef.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var dataHash = snapshot.getValue() as HashMap<String, String>
                adapter.itemList.add(ChatData(dataHash["msg"], dataHash["nickname"], dataHash["time"]))
                chat_recyclerView.adapter = adapter
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                var dataHash = snapshot.getValue() as HashMap<String, String>
                //adapter.itemList.clear()
                //chat_recyclerView.adapter = adapter
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
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("h:mm a")
        val formatted = current.format(formatter)
        var chatdata:ChatData = ChatData(msg, nickname, formatted)


        if (msg != null && !msg.equals("")){
            myRef.child(adapter.itemList.size.toString()).setValue(chatdata)
        }
    }
}