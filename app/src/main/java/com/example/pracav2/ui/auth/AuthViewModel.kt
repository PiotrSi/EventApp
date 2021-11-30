package com.example.pracav2.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pracav2.data.network.Resource
import com.example.pracav2.data.repository.AuthRepository
import com.example.pracav2.data.responses.DepResponse
import com.example.pracav2.data.responses.LoginResponse
import com.example.pracav2.data.responses.MessageResponse
import com.example.pracav2.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    private val _depResponse : MutableLiveData<Resource<DepResponse>> = MutableLiveData()
    val depResponse : LiveData<Resource<DepResponse>>
        get() = _depResponse

    private val _regiResponse : MutableLiveData<Resource<MessageResponse>> = MutableLiveData()
    val regiResponse : LiveData<Resource<MessageResponse>>
        get() = _regiResponse

    fun login(
        requestBody :RequestBody
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(requestBody)
    }

    fun signup(
        requestBody: RequestBody
    ) = viewModelScope.launch {
        _regiResponse.value = repository.signup(requestBody)
    }

    suspend fun saveAccessTokens(accessToken: String, refreshToken: String) {
        repository.saveAccessTokens(accessToken, refreshToken)
    }

    fun getDep(
    ) = viewModelScope.launch {
        _depResponse.value = repository.getDep()
    }
}