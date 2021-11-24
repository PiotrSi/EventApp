package com.example.pracav2.ui.home

import com.example.pracav2.data.repository.UserRepository
import com.example.pracav2.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


    @HiltViewModel
    class DescriptionViewModel @Inject constructor(
        private val repository: UserRepository
        ): BaseViewModel(repository){


}