package com.example.pracav2.ui.home

import android.view.View
import com.example.pracav2.data.responses.EventResponseItem

interface RecyclerViewClickListener {
    fun onItemClick(view : View, event : EventResponseItem)
}