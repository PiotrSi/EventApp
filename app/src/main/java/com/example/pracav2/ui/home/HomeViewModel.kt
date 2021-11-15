package com.example.pracav2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pracav2.data.network.Resource
import com.example.pracav2.data.repository.UserRepository
import com.example.pracav2.data.responses.EventResponse
import com.example.pracav2.data.responses.LoginResponse
import com.example.pracav2.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel(repository) {

    private val _user: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val user: LiveData<Resource<LoginResponse>>
        get() = _user

    private val _event: MutableLiveData<Resource<EventResponse>> = MutableLiveData()
    val event: LiveData<Resource<EventResponse>>
        get() = _event

    fun getUser() = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.value = repository.getUser()
    }

    fun getEvents()= viewModelScope.launch {
        _event.value = repository.getEvents()

    }

}