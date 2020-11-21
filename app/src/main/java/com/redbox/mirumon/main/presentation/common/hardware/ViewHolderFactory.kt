package com.redbox.mirumon.main.presentation.common.hardware

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.redbox.mirumon.R
import com.redbox.mirumon.main.domain.pojo.Hardware
import com.redbox.mirumon.main.domain.pojo.Software
import kotlinx.android.synthetic.main.cpu_list_item.view.*
import kotlinx.android.synthetic.main.software_list_item.view.*

object ViewHolderFactory {
    fun create(view: View, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.cpu_list_item -> CpuListViewHolder(view)
            R.layout.software_list_item -> SoftwareViewHolder(view)
            else -> throw Exception("Wrong view type")
        }
    }

    class CpuListViewHolder(view: View): RecyclerView.ViewHolder(view),GenericAdapter.Binder<Hardware.CPU>{
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

    class SoftwareViewHolder(view: View): RecyclerView.ViewHolder(view), GenericAdapter.Binder<Software>{
        override fun bind(data: Software) {
            itemView.apply {
                software_name_tv.text = data.name
                software_vendor_tv.text = data.vendor
                software_version_tv.text = data.version
            }
        }
    }
}