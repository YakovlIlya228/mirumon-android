package com.redbox.mirumon.main.presentation.main.devicelist

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.redbox.mirumon.R
import com.redbox.mirumon.main.domain.info.DeviceService
import com.redbox.mirumon.main.domain.pojo.DetailsRequest
import com.redbox.mirumon.main.domain.pojo.Device
import com.redbox.mirumon.main.domain.pojo.DeviceListItem
import com.redbox.mirumon.main.domain.websocket.DEVICE_LIST
import com.redbox.mirumon.main.domain.websocket.SHUTDOWN
import com.redbox.mirumon.main.domain.websocket.dispatcher.WebSocketDispatcher
import com.redbox.mirumon.main.domain.websocket.events.DeviceListEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DeviceListViewModel(val deviceService: DeviceService, val application: Application) : ViewModel(), LifecycleObserver {

    private val deviceList = MutableLiveData<ArrayList<DeviceListItem>>()

    fun getDevices(): LiveData<List<Device>>  =  liveData { deviceService.getDevices(application.getSharedPreferences(application.getString(R.string.pref_file_key),Context.MODE_PRIVATE).getString("USER_TOKEN","default")!!) }
//        WebSocketDispatcher.sendEvent(DEVICE_LIST, null)


    fun shutDown(macAddress: String) {
        WebSocketDispatcher.sendEvent(SHUTDOWN, DetailsRequest(macAddress))
    }

    fun observeDevices(
        lifecycleOwner: LifecycleOwner,
        callbackList: (ArrayList<DeviceListItem>) -> Unit
    ) =
        deviceList.observe(
            lifecycleOwner,
            Observer(callbackList)
        )

    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun onRecieve(response: DeviceListEvent) {
        deviceList.postValue(response.list)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onLifeCycleStart() {
        EventBus.getDefault().register(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onLifeCyclePause() {
        EventBus.getDefault().unregister(this)
    }
}