package com.redbox.mirumon.main.presentation.main


import android.app.Application
import androidx.lifecycle.*
import com.redbox.mirumon.main.domain.common.CommonRepository
import com.redbox.mirumon.main.domain.info.DeviceService
import com.redbox.mirumon.main.domain.pojo.*
import com.redbox.mirumon.main.presentation.common.overview.OverViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import com.redbox.mirumon.R
import com.redbox.mirumon.main.presentation.common.hardware.HardwareState
import com.redbox.mirumon.main.presentation.common.software.SoftwareState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class MainViewModel(private val deviceService: DeviceService, private val app: Application) :
    ViewModel(), LifecycleObserver {

    val cpu: MutableLiveData<HardwareState> by lazy { MutableLiveData<HardwareState>() }
    val motherboard: MutableLiveData<HardwareState> by lazy { MutableLiveData<HardwareState>() }
    val gpu: MutableLiveData<HardwareState> by lazy { MutableLiveData<HardwareState>() }
    val network: MutableLiveData<HardwareState> by lazy { MutableLiveData<HardwareState>() }
    val software: MutableLiveData<SoftwareState> by lazy { MutableLiveData<SoftwareState>() }
    val device: MutableLiveData<OverViewState> by lazy { MutableLiveData<OverViewState>() }
    val devices: MutableLiveData<OverViewState> by lazy { MutableLiveData<OverViewState>() }
    val responseCode: Flow<Response<Unit>> =
        flow { emit(shutdown(CommonRepository.getAddress()).await()) }


    fun getDevices() = viewModelScope.launch(Dispatchers.IO) {
        try {
            this@MainViewModel.devices.postValue(OverViewState.SuccessList(deviceService.getDevices()))
        } catch (e: Exception) {
            this@MainViewModel.device.postValue(
                OverViewState.Error(
                    app.applicationContext.resources.getString(
                        R.string.error_message
                    )
                )
            )
        }

    }

    fun getDeviceDetail(id: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            this@MainViewModel.device.postValue(
                OverViewState.Success(
                    deviceService.getDeviceDetail(
                        id
                    )
                )
            )
        } catch (e: Exception) {
            this@MainViewModel.device.postValue(
                OverViewState.Error(
                    app.applicationContext.resources.getString(
                        R.string.error_message
                    )
                )
            )
        }
    }

    fun getDeviceSoftware(id: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            software.postValue(SoftwareState.Success(deviceService.getDeviceSoftware(id)))
        } catch (e: Exception) {
            software.postValue(SoftwareState.Error(app.applicationContext.resources.getString(R.string.error_message)))
        }
    }

    fun getDeviceHardware(id: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            deviceService.getDeviceHardware(id).apply {
                this@MainViewModel.cpu.postValue(HardwareState.SuccessCpu(this.cpu))
                this@MainViewModel.motherboard.postValue(HardwareState.SuccessMB(this.motherboard))
                this@MainViewModel.gpu.postValue(HardwareState.SuccessGpu(this.gpu))
                this@MainViewModel.network.postValue(HardwareState.SuccessNet(this.network))
            }
        } catch (e: Exception) {
            software.postValue(SoftwareState.Error(app.applicationContext.resources.getString(R.string.error_message)))
        }

    }


    fun shutdown(id: String) = viewModelScope.async {
        deviceService.shutdown(id)
    }

}