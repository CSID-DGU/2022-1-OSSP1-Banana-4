package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_matching_success.*

class MatchingSuccess : AppCompatActivity() {
    private lateinit var adapter: MatchingAdapter

    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_success)

        var database = FirebaseDatabase.getInstance();

        adapter=MatchingAdapter (this)



        val btn_back=findViewById<Button>(R.id.btn_back) //뒤로가기버튼
        btn_back.setOnClickListener({
            val intent= Intent(this, CategoryPage::class.java) //다른 메뉴 찾아보기
            startActivity(intent)
        })

        val btn_done=findViewById<Button>(R.id.btn_done) //매칭 완료 채팅방생성
        btn_done.setOnClickListener({
            val intent= Intent(this, MainPage::class.java) //다시 매칭시도
            startActivity(intent)
        })

        val layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recycleView_m.layoutManager=layoutManager

        val adapter=MatchingAdapter(this)

       databaseReference=database.getReference("MatchingUsers")

        adapter.items.add(MatchingData("매칭1","냠1","냠냠2"))
        //매칭알고리즘만든후 유저데려오기

        recycleView_m.adapter=adapter


        var i=0
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val test = snapshot.child(i.toString())

                    for (es in test.children) {
                        val data= MatchingData(
                            test.child("name").value.toString(),
                            test.child("brand1").value.toString(),
                            test.child("brand2").value.toString(),
                            test.child("brand3").value.toString()
                        )
                        if (es.key.toString()=="name")
                            adapter.items.add(data)


                    }
                    i++

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        //#############################카테고리불러오기끝###########//






    }
}