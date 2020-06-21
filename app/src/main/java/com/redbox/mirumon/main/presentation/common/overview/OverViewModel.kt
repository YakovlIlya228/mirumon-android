package com.redbox.mirumon.main.presentation.common.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redbox.mirumon.main.domain.info.DeviceRepository
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class OverViewModel(private val repository: DeviceRepository) : ViewModel() {

    val state = MutableLiveData<OverViewState>()

    init {
        state.value = OverViewState.Loading
    }

    fun getOS(){
        viewModelScope.launch {
            try {
                state.postValue(OverViewState.Success(repository.getOS().os[0]))
            }
            catch (t: UnknownHostException){
                t.printStackTrace()
                state.postValue(OverViewState.Error)
            }
        }

    }
}