package com.redbox.mirumon.main.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.redbox.mirumon.BuildConfig
import com.redbox.mirumon.R
import kotlinx.android.synthetic.main.activity_main.main_fragments_vp
import kotlinx.android.synthetic.main.activity_main.main_tabs_tl

class MainActivity : AppCompatActivity() {

//    init {
//        AppCenter.start(
//            application, BuildConfig.APPCENTER_SECRET,
//            Analytics::class.java, Crashes::class.java
//        )
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_fragments_vp.adapter = MainViewPagerAdapter(supportFragmentManager)
        main_tabs_tl.setupWithViewPager(main_fragments_vp)

        main_fragments_vp.currentItem = 1
    }
}
