package com.example.pracav2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pracav2.data.network.Resource
import com.example.pracav2.data.repository.UserRepository
import com.example.pracav2.data.responses.EventResponse
import com.example.pracav2.data.responses.MessageResponse
import com.example.pracav2.data.responses.UserInfoResponse
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


    private val _userInfo: MutableLiveData<Resource<UserInfoResponse>> = MutableLiveData()
    val userInfo: MutableLiveData<Resource<UserInfoResponse>>
        get() = _userInfo

    private val _rate: MutableLiveData<Resource<MessageResponse>> = MutableLiveData()
    val rate: MutableLiveData<Resource<MessageResponse>>
        get() = _rate



    fun getUserInfo()= viewModelScope.launch {
        _userInfo.value = repository.getUserInfo()
    }

    fun getEvents()= viewModelScope.launch {
        _event.value = repository.getEvents()
    }

    fun enroll(
        requestBody : RequestBody
    ) = viewModelScope.launch {
        _enroll.value =  repository.enroll(requestBody)
    }

    fun rate(
        requestBody : RequestBody
    ) = viewModelScope.launch {
        _rate.value =  repository.rate(requestBody)
    }

}