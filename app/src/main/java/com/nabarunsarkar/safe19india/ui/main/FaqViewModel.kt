package com.nabarunsarkar.safe19india.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabarunsarkar.safe19india.model.FaqResponse
import com.nabarunsarkar.safe19india.repository.SafeFaqRepository
import com.nabarunsarkar.safe19india.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class FaqViewModel(private val repository: SafeFaqRepository) : ViewModel() {

    private val _covidLiveData = MutableLiveData<State<FaqResponse>>()

    val covidLiveData: LiveData<State<FaqResponse>>
        get() = _covidLiveData

    fun getData() {
        viewModelScope.launch {
            repository.getData().collect {
                _covidLiveData.value = it
            }
        }
    }
}