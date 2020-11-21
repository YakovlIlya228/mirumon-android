package com.redbox.mirumon.main.presentation.main.devicelist


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
import com.redbox.mirumon.main.presentation.common.software.SoftwareState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class DeviceListViewModel(private val deviceService: DeviceService, private val app: Application) :
    ViewModel(), LifecycleObserver {

    val cpu: MutableLiveData<List<Hardware.CPU>> by lazy { MutableLiveData<List<Hardware.CPU>>() }
    val motherboard: MutableLiveData<Hardware.Motherboard> by lazy { MutableLiveData<Hardware.Motherboard>() }
    val software: MutableLiveData<SoftwareState> by lazy { MutableLiveData<SoftwareState>() }
    val device: MutableLiveData<OverViewState> by lazy { MutableLiveData<OverViewState>() }
    val responseCode: Flow<Response<Unit>> = flow { emit(shutdown(CommonRepository.getAddress()).await()) }


    fun getDevices(): LiveData<List<Device>> = liveData { emit(deviceService.getDevices()) }

    suspend fun refreshDevices(): List<Device> {
        return deviceService.getDevices()
    }

    fun getDeviceDetail(id: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            this@DeviceListViewModel.device.postValue(
                OverViewState.Success(
                    deviceService.getDeviceDetail(
                        id
                    )
                )
            )
        } catch (e: Exception) {
            this@DeviceListViewModel.device.postValue(
                OverViewState.Error(
                    app.applicationContext.resources.getString(
                        R.string.error_message
                    )
                )
            )
        }
    }

//    fun getDeviceDetail(id: String) = liveData {
//        emit(deviceService.getDeviceDetail(id))
//    }

    fun getDeviceSoftware(id: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            software.postValue(SoftwareState.Success(deviceService.getDeviceSoftware(id)))
        } catch (e: Exception) {
            software.postValue(SoftwareState.Error(app.applicationContext.resources.getString(R.string.error_message)))
        }
    }

    fun getDeviceHardware(id: String) = viewModelScope.launch(Dispatchers.IO) {
        deviceService.getDeviceHardware(id).apply {
            this@DeviceListViewModel.cpu.postValue(this.cpu)
            this@DeviceListViewModel.motherboard.postValue(this.motherboard)
        }
    }


    fun shutdown(id: String) = viewModelScope.async {
        deviceService.shutdown(id)
    }

//    fun shutDown(macAddress: String) {
//        WebSocketDispatcher.sendEvent(SHUTDOWN, DetailsRequest(macAddress))
//    }
//
//    fun observeDevices(
//        lifecycleOwner: LifecycleOwner,
//        callbackList: (ArrayList<DeviceListItem>) -> Unit
//    ) =
//        deviceList.observe(
//            lifecycleOwner,
//            Observer(callbackList)
//        )

//    @Subscribe(threadMode = ThreadMode.ASYNC)
//    fun onRecieve(response: DeviceListEvent) {
//        deviceList.postValue(response.list)
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    private fun onLifeCycleStart() {
//        EventBus.getDefault().register(this)
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//    private fun onLifeCyclePause() {
//        EventBus.getDefault().unregister(this)
//    }
}