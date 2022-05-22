package com.example.myapplication

import android.util.SparseBooleanArray
import android.view.View
import android.widget.Button
import android.widget.TextView

interface OnBrandClickListener {

    fun onItemClick(
        holder: brandAdapter.ViewHolder?,
        view: View?,
        position: Int,
        checkboxStatus: SparseBooleanArray,
        text1: CharSequence,
        text2: CharSequence,
        text3: String,
        text4: String
    )



}