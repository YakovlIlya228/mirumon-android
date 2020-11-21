package com.redbox.mirumon.main.presentation.common.hardware

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.redbox.mirumon.R
import com.redbox.mirumon.main.domain.common.CommonRepository
import com.redbox.mirumon.main.domain.pojo.Hardware
import com.redbox.mirumon.main.presentation.main.devicelist.DeviceListViewModel
import com.redbox.mirumon.main.presentation.util.RevealButton.Companion.animateView
import kotlinx.android.synthetic.main.fragment_hardware.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named


class HardwareFragment : Fragment() {

    private val viewModel: DeviceListViewModel by sharedViewModel(named("AuthViewModel"))
    lateinit var cpuAdapter: GenericAdapter<Hardware.CPU>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cpu_rv.layoutManager = LinearLayoutManager(requireContext())
        cpuAdapter = object : GenericAdapter<Hardware.CPU>() {
            override fun getLayoutId(position: Int, obj: Hardware.CPU): Int = R.layout.cpu_list_item
        }
        cpu_rv.adapter = cpuAdapter
        with(viewModel) {
            motherboard.observe(viewLifecycleOwner) {
                motherboard_name_tv.text = it.name
                hardware_product_tv.text = it.product
                hardware_serial_tv.text = it.serial_number
            }
            cpu.observe(viewLifecycleOwner) {
                cpuAdapter.update(it)
            }
        }
        viewModel.getDeviceHardware(CommonRepository.getAddress())
        motherboard_btn.setActionListener {
            animateView(context!!, motherboard_layout, motherboard_btn)
        }
        cpu_btn.setActionListener {
            animateView(context!!, cpu_rv, cpu_btn)
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