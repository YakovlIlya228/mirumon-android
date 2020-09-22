package com.redbox.mirumon.main.presentation.common.hardware

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.redbox.mirumon.R
import com.redbox.mirumon.main.presentation.common.CommonRepository
import com.redbox.mirumon.main.presentation.main.devicelist.DeviceListViewModel
import kotlinx.android.synthetic.main.fragment_hardware.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named


class HardwareFragment : Fragment() {

    private val viewModel: DeviceListViewModel by sharedViewModel(named("AuthViewModel"))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        motherboard_btn.setActionListener {
            viewModel.getDeviceHardware(CommonRepository.getAddress()).observe(viewLifecycleOwner, Observer {
                motherboard_name_tv.text = it.name
                hardware_serial_tv.text = it.serial_number
                hardware_product_tv.text = it.product
                motherboard_layout.isVisible = motherboard_btn.stateOpened
            })
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hardware, container, false)
    }

}