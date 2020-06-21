package com.redbox.mirumon.main.presentation.common.software

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redbox.mirumon.main.domain.info.DeviceRepository
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class SoftwareViewModel(private val repository: DeviceRepository) : ViewModel() {

    val state = MutableLiveData<SoftwareState>()

    init {
        state.value = SoftwareState.Initial
    }

    fun getSoftware() {
        state.postValue(SoftwareState.Loading)
        viewModelScope.launch { try {
            state.postValue(SoftwareState.Success(repository.getSoftware()))
            }
        catch (t: UnknownHostException){
            t.printStackTrace()
            state.postValue(SoftwareState.Error)
            }
        }
    }
}
