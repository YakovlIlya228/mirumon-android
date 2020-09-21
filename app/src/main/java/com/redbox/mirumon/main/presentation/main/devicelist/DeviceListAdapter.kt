package com.redbox.mirumon.main.presentation.main.devicelist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.redbox.mirumon.R
import com.redbox.mirumon.main.domain.common.CommonRepository
import com.redbox.mirumon.main.domain.pojo.Device
import com.redbox.mirumon.main.domain.pojo.DeviceListItem
import com.redbox.mirumon.main.presentation.device.DeviceActivity
import kotlinx.android.synthetic.main.device_list_item.view.common_arch_tv
import kotlinx.android.synthetic.main.device_list_item.view.common_os_tv
import kotlinx.android.synthetic.main.device_list_item.view.device_foreground_cl
import kotlinx.android.synthetic.main.device_list_item.view.device_indicator_iv
import kotlinx.android.synthetic.main.device_list_item.view.device_name_tv
import kotlinx.android.synthetic.main.device_list_item.view.device_power_btn

class DeviceListAdapter:
    RecyclerView.Adapter<DeviceListAdapter.DeviceViewHolder>() {

    private var deviceList = ArrayList<Device>()
    private lateinit var viewAnim: Animation
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        return DeviceViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.device_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = deviceList.size

    fun setList(list: ArrayList<Device>){
        deviceList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.apply {
            nameTv.text = deviceList[position].name
            userTv.text = deviceList[position].lastUser.name
            domainTv.text = deviceList[position].domain
            id = deviceList[position].id
            val anim = AnimationUtils.loadAnimation(context, R.anim.blink)
            anim.startOffset = (100).toLong()
            indicatorIv.animation = anim
            viewAnim = AnimationUtils.loadAnimation(context,R.anim.blink)
            layout.setOnClickListener {
                layout.startAnimation(viewAnim)
                val computerIntent = Intent(this.context,DeviceActivity::class.java).apply {
                    putExtra("DEVICE_ID",id)
                }
                context.startActivity(computerIntent)
                layout.clearAnimation()
            }

//            powerButton.setOnClickListener {
//                listener(deviceList[position].macAddress)
//
//                notifyItemR-0emoved(position)
//            }
        }
    }

    class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val powerButton: ImageButton = view.device_power_btn
        lateinit var id: String
        val layout: ConstraintLayout = view.device_foreground_cl
        val context: Context = view.context
        val nameTv: TextView = view.device_name_tv
        val userTv: TextView = view.common_arch_tv
        val domainTv: TextView = view.common_os_tv
        val indicatorIv: ImageView = view.device_indicator_iv
    }
}