package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.mainpage_item.view.*

interface OnButtonItemClickListener{
    fun onItemClick(holder: ButtonAdapter.ViewHolder?, view: View, position: Int, index:Int)
}

class ButtonAdapter : RecyclerView.Adapter<ButtonAdapter.ViewHolder>(){
    var itemList = ArrayList<MainButton>();
    lateinit var listener: OnButtonItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.mainpage_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        init {
            itemView.foodButton1.setOnClickListener {
                listener?.onItemClick(this, itemView, adapterPosition, 0)
            }
            itemView.foodButton2.setOnClickListener {
                listener?.onItemClick(this, itemView, adapterPosition, 1)
            }
        }

        fun setItem(item: MainButton):Unit{
            itemView.foodName1.text = item.name1
            itemView.foodName2.text = item.name2

            when(item.filename1){
                "icon_pizza" -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon_pizza)
                }
                else -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon)
                }
            }

            when(item.filename2){
                "icon_pizza" -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon_pizza)
                }
                else -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon)
                }
            }

        }
    }
}