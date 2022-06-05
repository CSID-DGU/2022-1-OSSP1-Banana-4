package com.example.myapplication

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
class Repo {
    fun getData(): LiveData<MutableList<Brand>> {

        // https://gloria94.tistory.com/19 참고블로그


        val mutableData = MutableLiveData<MutableList<Brand>>()
        lateinit var databaseReference: DatabaseReference

        var database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference()
        //databaseReference.child("categories").child(id).child(category)

        val myRef = database.getReference("matching")
        databaseReference.addValueEventListener(object : ValueEventListener {
            val listData: MutableList<Brand> = mutableListOf<Brand>()
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val name= snapshot.child("0").child("name").value as String
                        listData.add(Brand(name)!!)

                        mutableData.value = listData
                    }
//                    for (userSnapshot in snapshot.children){
//                        val getData = userSnapshot.getValue(Brand::class.java)
//                        listData.add(getData!!)
//
//                        mutableData.value = listData
//                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return mutableData
    }
}