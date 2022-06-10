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
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
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
    //private val viewModel by lazy {ViewModelProvider(this).get(ListViewModel::class.java)}
    lateinit var databaseReference: DatabaseReference
    lateinit var userReference: DatabaseReference

    private lateinit var auth: FirebaseAuth




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)


        var userid="id" //유저아이디, 별점은 선택시 전송하는걸로
        var grade="3.5"

        var database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference()

        adapter = BrandAdapter(this)

        val layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        var  recycleView:RecyclerView= findViewById(R.id.recycleView)

        recycleView.layoutManager = layoutManager

        recycleView.adapter=adapter

        databaseReference.child("matching").child(userid).removeValue() //유저데이터초기화

        adapter.brandList.add(Brand("피자에땅","피자","240","13"))


        //#############################카테고리불러오기###########//
        userReference=database.getReference("resData")

        var i=0
        val resCate="9"
        userReference.addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val test = snapshot.child(i.toString())

                    for (es in test.children) {
                        val data= Brand(
                            test.child("name").value.toString(),
                            test.child("cate").value.toString(),
                            test.child("cate_num").value.toString(),
                            test.child("num").value.toString()
                        )


                        if (es.key.toString()=="cate_num"){
                            val tempkey:String=es.value.toString()
                            if(tempkey==resCate){
                                adapter.brandList.add(data)
                            }
                        }
                    }
                    i++

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        //#############################카테고리불러오기끝###########//

        
        

        //버튼
        val btn_search=findViewById<Button>(R.id.btn_search) //매칭 시작 버튼 일단 메인페이지가게설정
        btn_search.setOnClickListener({
            val intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })

        val btn_again=findViewById<Button>(R.id.btn_again) //다시하기버튼 메인페이지로
        btn_again.setOnClickListener({
           databaseReference.child("matching").child(userid).removeValue() //올라간데이터를삭제해줌

            val intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)

        })



        var text:String
        var cate:String
        var cate_num:String
        var num:String

        var count=0 //브랜드 최대 3개선택
        var arr=arrayOf("0","0","0")


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

                        var i=0
                        var temp="0"
                        while(i<3){
                            if (arr[i]=="0") {
                                temp = (i + 1).toString()
                                arr[i]=num
                                break
                            }
                            i++
                        }
                        databaseReference.child("matching").child(userid).child(temp.toString()).setValue(data)

                        // 매칭을 위해 실시간데이터의 matchingUser데이터에 카테고리정보를 넣어줍니다
                        checkStatus.put(position, false)
                    }
                }
                else{ //클릭시 삭제하기. true 처음엔 true상태
                    if (view != null) {
                        view.setBackgroundColor(Color.WHITE)
                        i=0
                        while(i<3){
                            if(arr[i]==text_num) {
                                databaseReference.child("matching").child(userid)
                                    .child((i + 1).toString()).removeValue() //올라간데이터를삭제해줌
                                arr[i]="0"
                                count--
                                break
                            }
                            i++
                        }
                        checkStatus.put(position,true)
                    }
                }

            }


        }
    }



    fun showToast(message: String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }


}

