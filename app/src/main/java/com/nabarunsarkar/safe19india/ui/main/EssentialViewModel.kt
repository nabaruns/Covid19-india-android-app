package com.nabarunsarkar.safe19india.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabarunsarkar.safe19india.model.EssentialsResponse
import com.nabarunsarkar.safe19india.repository.EssentialRepository
import com.nabarunsarkar.safe19india.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class EssentialViewModel(private val repository: EssentialRepository) : ViewModel() {

    private val _covidLiveData = MutableLiveData<State<EssentialsResponse>>()

    val covidLiveData: LiveData<State<EssentialsResponse>>
        get() = _covidLiveData

    fun getData() {
        viewModelScope.launch {
            repository.getData().collect {
                _covidLiveData.value = it
            }
        }
    }
}