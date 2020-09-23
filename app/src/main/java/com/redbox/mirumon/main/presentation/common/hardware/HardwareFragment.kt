package com.redbox.mirumon.main.presentation.common.hardware

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.redbox.mirumon.R
import com.redbox.mirumon.main.domain.pojo.Hardware
import com.redbox.mirumon.main.presentation.common.CommonRepository
import com.redbox.mirumon.main.presentation.main.devicelist.DeviceListViewModel
import com.redbox.mirumon.main.presentation.util.RevealButton.Companion.animateView
import kotlinx.android.synthetic.main.fragment_hardware.*
import kotlinx.android.synthetic.main.fragment_software.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named


class HardwareFragment : Fragment() {

    private val viewModel: DeviceListViewModel by sharedViewModel(named("AuthViewModel"))
    private val cpuAdapter = CPUListAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cpu_rv.adapter = cpuAdapter
        cpu_rv.layoutManager = LinearLayoutManager(this.context)
        viewModel.getDeviceHardware(CommonRepository.getAddress()).observe(viewLifecycleOwner, Observer {
            motherboard_name_tv.text = it.motherboard.name
            hardware_serial_tv.text = it.motherboard.serial_number
            hardware_product_tv.text = it.motherboard.product
            cpuAdapter.setList(it.cpu as ArrayList<Hardware.CPU>)
        })
        motherboard_btn.setActionListener {
            context?.let { animateView(it,motherboard_layout,motherboard_btn) }
        }

        cpu_btn.setActionListener {
            context?.let { animateView(it,cpu_rv,cpu_btn) }
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