package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main_page.*
import kotlin.reflect.typeOf


class MainPage : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    val adapter = ButtonAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        icon_mypage.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }


        var database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference()
        //databaseReference.child("categories").removeValue()
        addCategory("0", "icon_sushi","돈까스/회/일식")
        addCategory("1", "icon_chinese_food","중식")
        addCategory("2", "icon_chicken","치킨")
        addCategory("3", "icon_rice","백반/죽/국수")
        addCategory("4", "icon_dessert","카페/디저트")
        addCategory("5", "icon_hot_dog","분식")
        addCategory("6", "icon_zzim","찜/탕/찌개")
        addCategory("7", "icon_pizza","피자")
        addCategory("8", "icon_western_food","양식")
        addCategory("9", "icon_meat","고기/구이")
        addCategory("10", "icon_pig","족발/보쌈")
        addCategory("11", "icon_asian","아시안")
        addCategory("12", "icon_buger","패스트푸드")
        addCategory("13", "icon_lunch","도시락")


        //레이아웃의 방향을 관리하는
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        //여기서 버튼들을 추가
        adapter.context = this
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                dataSnapshot.children.forEach{
                    if(it.key == "categories"){
                        var list:ArrayList<Any?> = it.value as ArrayList<Any?>
                        var count:Int = 1
                        var data1:String? = ""
                        var data2:String? = ""

                        for (i in list){
                            var map = i as HashMap<String, String>

                            if(count % 2 != 0){
                                data1 = map["filename"]
                                data2 = map["name"]
                            }
                            else if(count % 2 == 0){
                                adapter.itemList.add(MainButton(data1, data2, map["filename"], map["name"]))
                            }
                            count++
                        }
                        recyclerView.adapter = adapter
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        databaseReference.addValueEventListener(postListener)
        //색상 추가
        adapter.colorList.add(R.color.f3a683)
        adapter.colorList.add(R.color.f7d794)
        adapter.colorList.add(R.color.f8a5c2)
        adapter.colorList.add(R.color.e778beb)
        adapter.colorList.add(R.color.e77f67)
        adapter.colorList.add(R.color.e786fa6)
        adapter.colorList.add(R.color.e63cdda)
        adapter.colorList.add(R.color.e93F1AA)
        adapter.colorList.add(R.color.e3C7AFF)
        adapter.colorList.add(R.color.cf6a87)

        recyclerView.adapter = adapter

        //각 버튼들이 클릭했을 때
        adapter.listener = object : OnButtonItemClickListener{
            override fun onItemClick(holder: ButtonAdapter.ViewHolder?, view: View, position: Int, index:Int) {
                var name:String?
                if(index == 0) {
                    name = adapter.itemList[position].name1
                    showToast("아이템 클릳됨 : ${adapter.itemList[position].name1}")
                }else{
                    name = adapter.itemList[position].name1
                    showToast("아이템 클릳됨 : ${adapter.itemList[position].name2}")
                }

                when(name){
                    "Pizza" -> {
                    }
                    else -> {
                        showToast("main button error")
                    }
                }
            }
        }
    }

    fun showToast(msg:String){
        //Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    fun addCategory(id:String, filename:String?, name:String?){
        var category:Category = Category(filename, name)

        databaseReference.child("categories").child(id).setValue(category)
    }

}