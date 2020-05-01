package com.nabarunsarkar.safe19india.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabarunsarkar.safe19india.model.DistrictDataDetails
import com.nabarunsarkar.safe19india.repository.DistrictRepository
import com.nabarunsarkar.safe19india.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class DistrictViewModel(private val repository: DistrictRepository) : ViewModel() {

    private val _covidLiveData = MutableLiveData<State<Map<String, DistrictDataDetails>>>()

    val covidLiveData: LiveData<State<Map<String, DistrictDataDetails>>>
        get() = _covidLiveData

    fun getData() {
        viewModelScope.launch {
            repository.getData().collect {
                _covidLiveData.value = it
            }
        }
    }
}