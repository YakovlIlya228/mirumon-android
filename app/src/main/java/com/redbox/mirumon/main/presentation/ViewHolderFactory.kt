package com.redbox.mirumon.main.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.redbox.mirumon.R
import com.redbox.mirumon.main.domain.pojo.Hardware
import com.redbox.mirumon.main.domain.pojo.Software
import kotlinx.android.synthetic.main.cpu_list_item.view.*
import kotlinx.android.synthetic.main.gpu_list_item.view.*
import kotlinx.android.synthetic.main.network_list_item.view.*
import kotlinx.android.synthetic.main.software_list_item.view.*

object ViewHolderFactory {
    fun create(view: View, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.cpu_list_item -> CpuListViewHolder(view)
            R.layout.software_list_item -> SoftwareViewHolder(view)
            R.layout.gpu_list_item -> GpuListViewHolder(view)
            R.layout.network_list_item -> NetworkListViewHolder(view)
            else -> throw Exception("Wrong view type")
        }
    }

    class CpuListViewHolder(view: View) : RecyclerView.ViewHolder(view),
        GenericAdapter.Binder<Hardware.CPU> {
        override fun bind(data: Hardware.CPU) {
            itemView.apply {
                cpu_name_tv.text = data.name
                cpu_speed_tv.text = data.clockSpeed
                cores_tv.text = data.cores.toString()
                enabled_cores_tv.text = data.enabledCores.toString()
                logical_proc_tv.text = data.logicalProcessors.toString()
            }
        }
    }

    class SoftwareViewHolder(view: View) : RecyclerView.ViewHolder(view),
        GenericAdapter.Binder<Software> {
        override fun bind(data: Software) {
            itemView.apply {
                software_name_tv.text = data.name
                software_vendor_tv.text = data.vendor
                software_version_tv.text = data.version
            }
        }
    }

    class GpuListViewHolder(view: View) : RecyclerView.ViewHolder(view),
        GenericAdapter.Binder<Hardware.GPU> {
        override fun bind(data: Hardware.GPU) {
            itemView.apply {
                gpu_name_tv.text = data.name
                status_tv.text = data.status
                driver_version_tv.text = data.driverVersion
                driver_date_tv.text = data.driverDate
                vertical_resolution_tv.text = data.currentVerticalResolution
            }
        }
    }

    class NetworkListViewHolder(view: View) : RecyclerView.ViewHolder(view), GenericAdapter.Binder<Hardware.Network>{
        override fun bind(data: Hardware.Network) {
            itemView.apply {
                description_tv.text = data.description
                mac_adress_tv.text = data.macAddress
                data.ipAddresses.forEach {
                    ip_adresses_tv.append("$it\n")
                }
            }
        }
    }
}