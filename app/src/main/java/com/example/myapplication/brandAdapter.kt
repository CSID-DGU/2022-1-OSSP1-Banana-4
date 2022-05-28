package com.example.myapplication

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_category.view.*
import kotlinx.android.synthetic.main.brand_name.view.*

class brandAdapter() : RecyclerView.Adapter<brandAdapter.ViewHolder>() {
    var brandList = mutableListOf<Brand>()

    lateinit var listener : OnBrandClickListener

    private val checkStatus = SparseBooleanArray(0)


    fun setListData(data:MutableList<Brand>){
        brandList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): brandAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.brand_name,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: brandAdapter.ViewHolder, position: Int) {
        val item = brandList[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return brandList.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val btn=itemView.btn_search

        init{
            itemView.setOnClickListener{
                listener.onItemClick(this,itemView,adapterPosition,
                    checkStatus,
                    itemView.name.text,
                    itemView.cate.text, itemView.num.text.toString(),
                    itemView.cate_num.text.toString())
            }

        }


        fun setItem(item:Brand){
            itemView.name.text= item.name
            itemView.cate.text=item.cate
            itemView.num.text= item.num.toString()
            itemView.cate_num.text= item.cate_num.toString()

        }

    }


}






