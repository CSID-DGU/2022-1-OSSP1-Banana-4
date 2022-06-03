package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.brand_name.view.*
import kotlinx.android.synthetic.main.matching_dataname.view.*

class MatchingAdapter: RecyclerView.Adapter<MatchingAdapter.ViewHolder>() {

    val items=ArrayList<MatchingData>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.matching_dataname, parent, false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item =items[position]
        holder.setItem(item)
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun setItem(item:MatchingData){
            itemView.username.text=item.name
            itemView.userbrand.text=item.brand
        }
    }

}





