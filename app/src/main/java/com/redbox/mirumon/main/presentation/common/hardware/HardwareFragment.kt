package com.redbox.mirumon.main.presentation.common.hardware

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.redbox.mirumon.R
import com.redbox.mirumon.main.domain.common.CommonRepository
import com.redbox.mirumon.main.domain.pojo.Hardware
import com.redbox.mirumon.main.presentation.GenericAdapter
import com.redbox.mirumon.main.presentation.main.MainViewModel
import com.redbox.mirumon.main.presentation.util.RevealButton.Companion.animateView
import kotlinx.android.synthetic.main.fragment_hardware.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named


class HardwareFragment : Fragment() {

    private val viewModel: MainViewModel by sharedViewModel(named("AuthViewModel"))
    lateinit var cpuAdapter: GenericAdapter<Hardware.CPU>
    lateinit var gpuAdapter: GenericAdapter<Hardware.GPU>
    lateinit var networkAdapter: GenericAdapter<Hardware.Network>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cpu_rv.layoutManager = LinearLayoutManager(requireContext())
        gpu_rv.layoutManager = LinearLayoutManager(requireContext())
        network_rv.layoutManager = LinearLayoutManager(requireContext())
        cpuAdapter = object : GenericAdapter<Hardware.CPU>() {
            override fun getLayoutId(position: Int, obj: Hardware.CPU): Int = R.layout.cpu_list_item
        }
        gpuAdapter = object : GenericAdapter<Hardware.GPU>() {
            override fun getLayoutId(position: Int, obj: Hardware.GPU): Int = R.layout.gpu_list_item
        }
        networkAdapter = object : GenericAdapter<Hardware.Network>(){
            override fun getLayoutId(position: Int, obj: Hardware.Network): Int = R.layout.network_list_item
        }
        cpu_rv.adapter = cpuAdapter
        gpu_rv.adapter = gpuAdapter
        network_rv.adapter = networkAdapter
        with(viewModel) {
            motherboard.observe(viewLifecycleOwner) {
                when (it) {
                    is HardwareState.SuccessMB -> {
                        motherboard_name_tv.text = it.data.name
                        hardware_product_tv.text = it.data.product
                        hardware_serial_tv.text = it.data.serial_number
                    }
                    is HardwareState.Error -> Toast.makeText(
                        requireContext(),
                        it.errorMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            cpu.observe(viewLifecycleOwner) {
                when (it) {
                    is HardwareState.SuccessCpu -> cpuAdapter.update(it.data)
                    is HardwareState.Error -> Toast.makeText(
                        requireContext(),
                        it.errorMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            gpu.observe(viewLifecycleOwner) {
                when (it) {
                    is HardwareState.SuccessGpu -> gpuAdapter.update(it.data)
                    is HardwareState.Error -> Toast.makeText(
                        requireContext(),
                        it.errorMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            network.observe(viewLifecycleOwner){
                when(it){
                    is HardwareState.SuccessNet -> networkAdapter.update(it.data)
                    is HardwareState.Error -> Toast.makeText(
                        requireContext(),
                        it.errorMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        viewModel.getDeviceHardware(CommonRepository.getAddress())
        motherboard_btn.setActionListener {
            animateView(requireContext(), motherboard_layout, motherboard_btn)
        }
        cpu_btn.setActionListener {
            animateView(requireContext(), cpu_rv, cpu_btn)
        }
        gpu_btn.setActionListener {
            animateView(requireContext(), gpu_rv, gpu_btn)
        }
        network_btn.setActionListener {
            animateView(requireContext(), network_rv, network_btn)
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