package com.redbox.mirumon.main.presentation.device

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redbox.mirumon.main.domain.info.DeviceRepository
import com.redbox.mirumon.main.domain.pojo.Command
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class DeviceViewModel(private val rep: DeviceRepository) : ViewModel() {

    val state = MutableLiveData<DeviceState>()

    init {
        state.value = DeviceState.Initial
    }

    fun getDeviceInfo(){
        state.postValue(DeviceState.Loading)
        viewModelScope.launch {
            try {
                state.postValue(DeviceState.Success(rep.getDeviceInfo()))
            }
            catch (t: UnknownHostException){
                t.printStackTrace()
                state.postValue(DeviceState.Error)
            }
        }

    }

    fun shutdownPC() {
        viewModelScope.launch { rep.shutdownPC() }
            state.postValue(DeviceState.ShuttingDown)
    }

    fun executeCommand(command: String) {
            viewModelScope.launch { rep.executeCommand(Command(command)) }
    }
}