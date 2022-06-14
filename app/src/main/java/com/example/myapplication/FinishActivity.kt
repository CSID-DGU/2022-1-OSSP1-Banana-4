package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FinishActivity : AppCompatActivity() {

    // mate recyclerview
    private lateinit var mateAdapter: MateAdapter
    private val mates = mutableListOf<MateData>()
    lateinit var userMate : RecyclerView
    lateinit var button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        button = findViewById(R.id.not_review_button)
        userMate = findViewById(R.id.user_mate)
        var mateList : MutableList<String> = mutableListOf()
        val mateHash = intent.getSerializableExtra("userMap") as HashMap<*, *>?
        if (mateHash != null) {
            for(mate in mateHash.values){
                mateList.add(mate.toString())
            }
        }
        var mateNum = mateList.size

        // mate adapter
        mateAdapter = MateAdapter(this)
        userMate.adapter = mateAdapter

        mates.apply {
            for (i in 0 until mateNum) {
                val mate =
                    FirebaseDatabase.getInstance().getReference("Users").child(mateList[i])

                mate.get().addOnSuccessListener { dataSnapshot ->
                    val username = dataSnapshot.child("userNickname").value.toString()
                    val imageURL = "https://firebasestorage.googleapis.com/v0/b/banana-8d3ab.appspot.com/o/Image%2F" +
                    "${mateList[i]}?alt=media"
                    add(MateData(mateList[i], username, imageURL))
                    mateAdapter.listener = object : OnClickListener {
                        override fun btnClick(
                            holder: MateAdapter.ViewHolder?,
                            view: View,
                            position: Int
                        ) {
                            val intent = Intent(view.context, Review::class.java)
                            intent.putExtra("mate_id", mateList[i])
                            startActivity(intent)
                        }
                    }
                }
            }
            mateAdapter.mates = mates
            mateAdapter.notifyDataSetChanged()
        }
        button.setOnClickListener {
            val intent = Intent(this, MainPage::class.java)
            startActivity(intent)
        }
    }

}