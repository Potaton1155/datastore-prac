package com.tomo_app.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: MyRepository,
) : ViewModel() {
    private val _profile = MutableStateFlow(Profile())
    val profile = _profile.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            repository.dataFlow.collect {
                _profile.value = it
            }
        }
    }

    fun saveData(data: String, count: Int) {
        viewModelScope.launch {
            repository.saveData(Profile(data, count))
        }
    }
}