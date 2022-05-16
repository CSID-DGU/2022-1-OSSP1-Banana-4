package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main_page.*

class MainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        val adapter = ButtonAdapter()
        adapter.itemList.add(MainButton("icon_pizza", "Pizza1", "icon_pizza", "Pizza2"))
        adapter.itemList.add(MainButton("icon_chiken", "chiken", "icon_pizza", "Pizza3"))
        adapter.itemList.add(MainButton("icon_korean_food", "korean food", "icon_pizza", "Pizza4"))

        recyclerView.adapter = adapter

        adapter.listener = object : OnButtonItemClickListener{
            override fun onItemClick(holder: ButtonAdapter.ViewHolder?, view: View, position: Int, index:Int) {

                if(index == 0) {
                    showToast("아이템 클릳됨 : ${adapter.itemList[position].name1}")
                }else{
                    showToast("아이템 클릳됨 : ${adapter.itemList[position].name2}")
                }

            }
        }

    }

    fun showToast(msg:String){
        //Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}