package com.example.myapplication

import android.view.View

interface OnMatchingListener {
    fun onItemClick(
        holder: MatchingAdapter.ViewHolder,
        view: View?,
        position: Int,
        text1: CharSequence
    )


}