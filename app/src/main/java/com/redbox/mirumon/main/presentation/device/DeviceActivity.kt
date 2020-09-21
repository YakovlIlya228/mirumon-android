package com.redbox.mirumon.main.presentation.device

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.redbox.mirumon.R
import com.redbox.mirumon.main.extensions.applyErrorState
import com.redbox.mirumon.main.extensions.applyTextLoadingState
import com.redbox.mirumon.main.extensions.applyTextSuccessState
import com.redbox.mirumon.main.presentation.common.CommonInfoActivity
import com.redbox.mirumon.main.presentation.device.DeviceState.Error
import com.redbox.mirumon.main.presentation.device.DeviceState.ShuttingDown
import com.redbox.mirumon.main.presentation.main.devicelist.DeviceListViewModel
import kotlinx.android.synthetic.main.activity_device.device_back_btn
import kotlinx.android.synthetic.main.activity_device.device_common_btn
import kotlinx.android.synthetic.main.activity_device.device_domain_tv
import kotlinx.android.synthetic.main.activity_device.device_exec_btn
import kotlinx.android.synthetic.main.activity_device.device_exec_et
import kotlinx.android.synthetic.main.activity_device.device_name_tv
import kotlinx.android.synthetic.main.activity_device.device_shutdown_btn
import kotlinx.android.synthetic.main.activity_device.device_user_tv
import kotlinx.android.synthetic.main.activity_device.device_workgroup_tv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class DeviceActivity : AppCompatActivity() {

    private val viewModel: DeviceListViewModel by viewModel(named("AuthViewModel"))

    //    lateinit var dataTransmitter: CommonInfoActivity.DataTransmitter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)
        val deviceId = intent.extras?.getString("DEVICE_ID")
        deviceId?.let {
            viewModel.getDeviceDetail(it).observe(this, Observer {
                device_name_tv.text = it.name
                device_domain_tv.text = it.domain
                device_workgroup_tv.text = it.workgroup
                device_user_tv.text = it.lastUser.fullName
            })
        }

        device_back_btn.setOnClickListener {
            super.onBackPressed()
        }

        device_common_btn.setOnClickListener {
            startActivity(Intent(this, CommonInfoActivity::class.java).apply {
                putExtra("DEVICE_ID", deviceId)
            })
        }
//            deviceId?.let {
//                dataTransmitter.passId(it)
//            }
    }

//
//        vm.state.observe(this, Observer {
//            when (it) {
//                is DeviceState.Initial ->
//                    GlobalScope.launch(Dispatchers.IO) {
//                        vm.getDeviceInfo()
//                    }
//                is DeviceState.Loading -> this.applyTextLoadingState(
//                    device_name_tv,
//                    device_domain_tv,
//                    device_workgroup_tv,
//                    device_user_tv
//                )
//                is DeviceState.Success -> {
//                    this.applyTextSuccessState(
//                        device_name_tv,
//                        device_domain_tv,
//                        device_workgroup_tv,
//                        device_user_tv
//                    )
//
//                    device_name_tv.text = it.device.name
//                    device_domain_tv.text = it.device.domain
//                    device_workgroup_tv.text = it.device.workgroup
//                    device_user_tv.text = it.device.user.name
//                }
//                is ShuttingDown -> {
//                    device_common_btn.isClickable = false
//                    Toast.makeText(this, R.string.offline_message, Toast.LENGTH_LONG).show()
//                }
//                is Error -> {
//                    applyErrorState()
//                }
//            }
//        })
//
//        device_shutdown_btn.setOnClickListener {
//            GlobalScope.launch(Dispatchers.IO) {
//                vm.shutdownPC()
//            }
//        }
//
//        device_exec_btn.setActionListener {
//            device_exec_et.isVisible = !device_exec_et.isVisible
//        }
//
//        device_exec_et.setOnEditorActionListener { _: TextView, _: Int, _: KeyEvent? ->
//            GlobalScope.launch(Dispatchers.IO) {
//                vm.executeCommand(device_exec_et.text.toString())
//            }
//            return@setOnEditorActionListener true
//        }
}

