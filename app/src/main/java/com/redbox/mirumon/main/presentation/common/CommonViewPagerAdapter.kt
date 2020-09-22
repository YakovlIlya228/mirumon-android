package com.redbox.mirumon.main.presentation.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.redbox.mirumon.main.presentation.common.hardware.HardwareFragment
import com.redbox.mirumon.main.presentation.common.overview.OverviewFragment
import com.redbox.mirumon.main.presentation.common.software.SoftwareFragment

class CommonViewPagerAdapter(fm: FragmentManager, private val tabCount: Int = 3) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> SoftwareFragment()
            2 -> HardwareFragment()
            else -> OverviewFragment()
        }
    }

    override fun getCount() = tabCount
}