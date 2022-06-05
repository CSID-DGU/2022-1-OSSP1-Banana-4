package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_category.view.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.brand_name.*

class CategoryPage : AppCompatActivity() {
    private lateinit var adapter: BrandAdapter
    private val viewModel by lazy {ViewModelProvider(this).get(ListViewModel::class.java)}
    lateinit var databaseReference: DatabaseReference
    lateinit var userReference: DatabaseReference

    private lateinit var auth: FirebaseAuth




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        var userid="id" //유저아이디, 별점은 선택시 전송하는걸로
        var grade="3.5"
        val tt:TextView =findViewById(R.id.tv11)

        var database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference()

//        userReference=database.getReference("categories")
//
//        userReference.addValueEventListener(object :ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//               // val test= snapshot.child("0").child("filename").value as String
//                val test= snapshot.child("0")
//
//                tt.text= test.toString()
//                for (ds in test.children){
//                    Log.e("snap",ds.toString())
//                    }
//
//
////                for (ds in snapshot.children){
////                    when{
////                        "0".equals(ds.key)->{
////                            val zero=snapshot.child("0")
////                            for(item in zero.children){
////                                val id=item.key.toString()
////                                val filename=item.child("filename").value as String
////                                //tt.text=filename
////                            }
////                        }
////                    }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })

        //가져오기는 성공했음..!

        adapter = BrandAdapter(this)

        val layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        var  recycleView:RecyclerView= findViewById(R.id.recycleView)

        recycleView.layoutManager = layoutManager

        recycleView.adapter=adapter


        adapter.brandList.add(Brand("피자에땅","피자","0","5"))
        adapter.brandList.add(Brand("피자2","피자","0","5"))

        adapter.brandList.add(Brand("피자에3","피자","0","5"))

        adapter.brandList.add(Brand("피자4","피자","0","5"))



        //#############################카테고리불러오기###########//
        userReference=database.getReference("resData")
        var i=0
        userReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children){
                    val test= snapshot.child(i.toString())


                    for (es in test.children){
                        Log.e("snap",es.toString())
                        Log.d("snap","번째"+i)


                    }
                    i++

                    when{
                        "0".equals(ds.child("cate_num"))->{
                            val name: DataSnapshot =ds.child("name")
                            adapter.brandList.add(Brand(name.toString(),"피자","0","5"))
                            adapter.brandList.add(Brand("ㅇㅅㅇ","피자","0","5"))

                        }

                    }

                }





//               // val test= snapshot.child("0").child("name").value as String
//                val test2= snapshot.child("0").child("cate_num").value as String
//                val name=snapshot.child("0").child("name").value as String
//
//               // adapter.brandList.add(Brand("test","피자","0","5"))
//
////
//                  adapter.brandList.add(Brand(name,"피자","0",test2))


 //                    for (ds in snapshot.children){
////                    when{
////                        "0".equals(ds.key)->{
////                            val zero=snapshot.child("0")
////                            for(item in zero.children){
////                                val id=item.key.toString()
////                                val filename=item.child("filename").value as String
////                                //tt.text=filename
////                            }
////                        }
////                    }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        //####################################//



        //버튼
        val btn_search=findViewById<Button>(R.id.btn_search) //매칭 시작 버튼 일단 메인페이지가게설정
        btn_search.setOnClickListener({
            val intent=Intent(this, MainPage::class.java)
            startActivity(intent)
        })

        val btn_again=findViewById<Button>(R.id.btn_again) //다시하기버튼 메인페이지로
        btn_again.setOnClickListener({
     //       databaseReference.child("matching").child(user_id).removeValue() //올라간데이터를삭제해줌

            val intent=Intent(this, MainPage::class.java)
            startActivity(intent)
        })


//

//데이터읽기가 안되서 하드코딩으로 일단 했습니다 원래면 repo 페이지에서 가져오게됨
// https://gloria94.tistory.com/19 참고블로그
// 구현필요한거: 카테고리선택페이지에서 선택한 카테고리에 해당하는 목록을 가져오게해야함!
//
//
        var text:String
        var cate:String
        var cate_num:String
        var num:String

        var count=0 //브랜드 최대 3개선택

        adapter.listener = object: OnBrandClickListener {
            override fun onItemClick(
                holder: BrandAdapter.ViewHolder?,
                view: View?,
                position: Int,
                checkStatus: SparseBooleanArray,
                text_name: CharSequence,
                text_cate: CharSequence,
                text_num: CharSequence,
                text_cate_num: CharSequence,
                text5: CharSequence,
                text_grade: CharSequence
            ) {


                //3개 선택
                if(checkStatus.get(position,true)){
                    if (count <3 &&view != null) {
                        view.setBackgroundColor(Color.YELLOW)
                        count++

                        text= text_name.toString()
                        cate= text_cate.toString()
                        cate_num= text_cate_num.toString()
                        num= text_num.toString()



                        val data =Brand(text, cate,cate_num ,
                            num ,userid,grade )
                        databaseReference.child("matching").child(userid).child(count.toString()).setValue(data)

                        // 매칭을 위해 실시간데이터의 matchingUser데이터에 카테고리정보를 넣어줍니다
                        // 유저정보도 같이 넣어야하는데 일단 브랜드이름, 카테고리, 숫자만 함

                        checkStatus.put(position, false)
                    }
                }
                else{ //true 처음엔 true상태
                    if (view != null) {
                        view.setBackgroundColor(Color.WHITE)

                        databaseReference.child("matching").child(userid).child(count.toString()).removeValue() //올라간데이터를삭제해줌
                        count--
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

