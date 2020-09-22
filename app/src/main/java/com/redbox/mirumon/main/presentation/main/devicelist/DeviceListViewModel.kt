package com.redbox.mirumon.main.presentation.main.devicelist


import android.content.SharedPreferences
import androidx.lifecycle.*
import com.redbox.mirumon.main.domain.info.DeviceService
import com.redbox.mirumon.main.domain.pojo.DetailsRequest
import com.redbox.mirumon.main.domain.pojo.Device
import com.redbox.mirumon.main.domain.pojo.DeviceListItem
import com.redbox.mirumon.main.domain.pojo.Software
import com.redbox.mirumon.main.domain.websocket.SHUTDOWN
import com.redbox.mirumon.main.domain.websocket.dispatcher.WebSocketDispatcher
import com.redbox.mirumon.main.domain.websocket.events.DeviceListEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DeviceListViewModel(val deviceService: DeviceService) : ViewModel(), LifecycleObserver {

//    private val deviceList = MutableLiveData<ArrayList<DeviceListItem>>()
    lateinit var deviceId: String

    fun getDevices(): LiveData<List<Device>> = liveData { emit(deviceService.getDevices()) }

    suspend fun refreshDevices(): List<Device> {
        return deviceService.getDevices()
    }

    fun getDeviceDetail(id: String): LiveData<Device> = liveData {
        emit(deviceService.getDeviceDetail(id))
    }

    fun getDeviceSoftware(id: String): LiveData<List<Software>> = liveData {
        emit(deviceService.getDeviceSoftware(id))
    }

    fun getDeviceHardware(id: String) = liveData {
        emit(deviceService.getDeviceHardware(id))
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