package com.example.pracav2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pracav2.data.network.Resource
import com.example.pracav2.data.repository.UserRepository
import com.example.pracav2.data.responses.EventResponse
import com.example.pracav2.data.responses.MessageResponse
import com.example.pracav2.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository,
) : BaseViewModel(repository) {


    private val _event: MutableLiveData<Resource<EventResponse>> = MutableLiveData()
    val event: LiveData<Resource<EventResponse>>
        get() = _event

    private val _enroll: MutableLiveData<Resource<MessageResponse>> = MutableLiveData()
    val enroll: MutableLiveData<Resource<MessageResponse>>
        get() = _enroll

    private val _user: MutableLiveData<Resource<MessageResponse>> = MutableLiveData()
    val user: MutableLiveData<Resource<MessageResponse>>
        get() = _user

//    private val _userDetail: MutableLiveData<Resource<User>>

    fun getUser()= viewModelScope.launch {
        _user.value = repository.getUser()
    }


    fun getEvents(token :String)= viewModelScope.launch {
        _event.value = repository.getEvents(token)
    }

    fun enroll(
        requestBody : RequestBody
    ) = viewModelScope.launch {
        _enroll.value =  repository.enroll(requestBody)
    }

}