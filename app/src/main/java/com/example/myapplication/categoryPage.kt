package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_category.*
import java.lang.reflect.Member
import java.sql.Types.NULL

class categoryPage : AppCompatActivity() {
    private lateinit var adapter: brandAdapter
    private val viewModel by lazy {ViewModelProvider(this).get(ListViewModel::class.java)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)




        val database by lazy { FirebaseDatabase.getInstance() }
        val userRef = database.getReference("matchingUser")
        //버튼
        val btn_search=findViewById<Button>(R.id.btn_search) //매칭 시작 버튼 일단 메인페이지가게설정
        btn_search.setOnClickListener({
            val intent=Intent(this, MainPage::class.java)
            startActivity(intent)
        })

        val btn_again=findViewById<Button>(R.id.btn_again) //다시하기버튼 메인페이지로
        btn_again.setOnClickListener({
            val intent=Intent(this, MainPage::class.java)
            startActivity(intent)


            userRef.removeValue() //올라간데이터를삭제해줌


        })


        //
       // val database=Firebase.database
        val myRef = database.getReference("message")
        myRef.setValue("Success")
        //위에 3줄은 테스트용 실시간데이터베이스에 데이터 쓰기코드

        val layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recycleView.layoutManager = layoutManager

        val adapter = brandAdapter()
        recycleView.adapter=adapter



        adapter.brandList.add(Brand("피자에땅","피자",0,6))
        adapter.brandList.add(Brand("피자헛","피자",1))

        adapter.brandList.add(Brand("피자에땅","피자",0))
        adapter.brandList.add(Brand("피자헛","피자",1))
        adapter.brandList.add(Brand("피자에땅","피자",0))
        adapter.brandList.add(Brand("상관없음","피자",1))

//데이터읽기가 안되서 하드코딩으로 일단 했습니다 원래면 repo 페이지에서 가져오게됨
// https://gloria94.tistory.com/19 참고블로그
// 구현필요한거: 카테고리선택페이지에서 선택한 카테고리에 해당하는 목록을 가져오게해야함!
//
//
        var count=0
        var user_id="id"
        adapter.listener = object: OnBrandClickListener {
            override fun onItemClick(
                holder: brandAdapter.ViewHolder?,
                view: View?,
                position: Int,
                checkStatus: SparseBooleanArray,
                text_name: CharSequence,
                text_cate: CharSequence,
                text_num: String,
                text_cate_num: String,
            ) {


                //3개 선택
                if(checkStatus.get(position,true)){
                    if (count <3 &&view != null) {
                        view.setBackgroundColor(Color.YELLOW)

                        count++
                        userRef.child("name").setValue(text_name)
                        userRef.child("cate").setValue(text_cate)
                        userRef.child("num").setValue(text_num.toInt())
                        userRef.child("cate_num").setValue(text_cate_num.toInt())
                        userRef.child("user_id").setValue(user_id)
                        // 매칭을 위해 실시간데이터의 matchingUser데이터에 카테고리정보를 넣어줍니다
                        // 유저정보도 같이 넣어야하는데 일단 브랜드이름, 카테고리, 숫자만 함

                        checkStatus.put(position, false)
                    }
                }
                else{ //true 처음엔 true상태
                    if (view != null) {
                        view.setBackgroundColor(Color.WHITE)
                        count--
                        userRef.removeValue() //올라간데이터를삭제해줌


                        checkStatus.put(position,true)
                    }
                }

                //showToast("아이템 클릭: ${text_name} ${checkStatus} ") //그냥 잘되는지 확인용



            }
        }

    }

    fun showToast(message: String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    fun observerData(){
        viewModel.fetchData().observe(this, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
    }
}
 //
