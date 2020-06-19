package com.redbox.mirumon.main.presentation.common.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redbox.mirumon.main.domain.info.DeviceRepository
import java.net.UnknownHostException

class OverViewModel(private val repository: DeviceRepository) : ViewModel() {

    val state = MutableLiveData<OverViewState>()

    init {
        state.value = OverViewState.Loading
    }

    suspend fun getOS(){
        try {
            state.postValue(OverViewState.Success(repository.getOS().os[0]))
        }
        catch (t: UnknownHostException){
            t.printStackTrace()
            state.postValue(OverViewState.Error)
        }
    }
}