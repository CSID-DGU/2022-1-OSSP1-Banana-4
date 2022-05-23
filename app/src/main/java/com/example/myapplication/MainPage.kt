package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main_page.*

class MainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        //레이아웃의 방향을 관리하는
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        //여기서 버튼들을 추가
        val adapter = ButtonAdapter()
        adapter.context = this
        adapter.itemList.add(MainButton("icon_sushi", "돈까스/회/일식", "icon_chinese_food", "중식"))
        adapter.itemList.add(MainButton("icon_chicken", "치킨", "icon_rice", "백반"))
        adapter.itemList.add(MainButton("icon_dessert", "카페/디저트", "icon_hot_dog", "핫도그"))
        adapter.itemList.add(MainButton("icon_zzim", "찜/탕/찌개", "icon_pizza", "피자"))
        adapter.itemList.add(MainButton("icon_western_food", "양식", "icon_meat", "고기/구이"))
        adapter.itemList.add(MainButton("icon_pig", "족발/보쌈", "icon_asian", "아시안"))
        adapter.itemList.add(MainButton("icon_buger", "패스트푸드", "icon_lunch", "도시락"))

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

}