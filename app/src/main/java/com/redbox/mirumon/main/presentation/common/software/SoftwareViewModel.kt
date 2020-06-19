package com.redbox.mirumon.main.presentation.common.software

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redbox.mirumon.main.domain.info.DeviceRepository
import java.net.UnknownHostException

class SoftwareViewModel(private val repository: DeviceRepository) : ViewModel() {

    val state = MutableLiveData<SoftwareState>()

    init {
        state.value = SoftwareState.Initial
    }

    suspend fun getSoftware() {
        state.postValue(SoftwareState.Loading)
        try {
            state.postValue(SoftwareState.Success(repository.getSoftware()))
        }
        catch (t: UnknownHostException){
            t.printStackTrace()
            state.postValue(SoftwareState.Error)
        }
    }
}
