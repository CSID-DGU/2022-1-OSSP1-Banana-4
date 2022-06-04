package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_chat.view.*

class CharAdapter : RecyclerView.Adapter<CharAdapter.ViewHolder>(){
    var itemList = ArrayList<ChatData>()
    var myNickname = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_chat, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CharAdapter.ViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun setItem(item: ChatData):Unit{
            itemView.chat_msg.text = item.msg
            itemView.chat_nickname.text = item.nickname

            if(item.nickname.equals(myNickname)){
                itemView.chat_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END)
                itemView.chat_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END)
            }
        }
    }
}