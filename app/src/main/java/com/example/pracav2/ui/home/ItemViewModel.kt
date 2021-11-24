package com.example.pracav2.ui.home

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pracav2.data.responses.EventResponseItem

class ItemViewModel : ViewModel() {
    private val mutableSelectedItem = MutableLiveData<EventResponseItem>()
    val selectedItem: LiveData<EventResponseItem> get() = mutableSelectedItem

    private val mutableIdUser = MutableLiveData<Int>()
    val idUser: LiveData<Int> get() = mutableIdUser

    fun selectItem(item: EventResponseItem) {
        mutableSelectedItem.value = item
    }

    fun idUser(item: Int) {
        mutableIdUser.value = item
    }

}
