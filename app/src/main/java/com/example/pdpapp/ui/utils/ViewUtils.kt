package com.example.pdpapp.ui.utils

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes

fun View.visibility(state: Boolean){
    visibility = if(state) View.VISIBLE else View.GONE
}

fun View.setColor(context: Context, @ColorRes color: Int){
    setBackgroundColor(context.getColor(color))
}

