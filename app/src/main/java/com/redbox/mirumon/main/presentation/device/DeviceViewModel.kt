package com.redbox.mirumon.main.presentation.device

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redbox.mirumon.main.domain.info.DeviceRepository
import com.redbox.mirumon.main.domain.pojo.Command
import java.net.UnknownHostException

class DeviceViewModel(private val rep: DeviceRepository) : ViewModel() {

    val state = MutableLiveData<DeviceState>()

    init {
        state.value = DeviceState.Initial
    }

    suspend fun getDeviceInfo(){
        state.postValue(DeviceState.Loading)
        try {
            state.postValue(DeviceState.Success(rep.getDeviceInfo()))
        }
        catch (t: UnknownHostException){
            t.printStackTrace()
            state.postValue(DeviceState.Error)
        }
    }

    suspend fun shutdownPC() {
            rep.shutdownPC()
            state.postValue(DeviceState.ShuttingDown)
    }

    suspend fun executeCommand(command: String) {
            rep.executeCommand(Command(command))
    }
}