package com.redbox.mirumon.main.extensions

import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.redbox.mirumon.R

fun Fragment.applyLoadingState(
    rv: RecyclerView
) {
    rv.adapter = null
}

fun Fragment.applySuccessState(
    loader: ProgressBar
) {
    loader.isVisible = false
}

fun Fragment.applyLoadingState(
    loader: ProgressBar
){
    loader.isVisible = true
}

fun Fragment.applyTextLoadingState(vararg textViews: TextView) {
    val anim = AnimationUtils.loadAnimation(context, R.anim.blink)
    for (t in textViews) {
        t.animation = anim
    }
}

fun Fragment.applyTextSuccessState(vararg textViews: TextView) {
    for (t in textViews) {
        t.animation = null
    }
}

fun Fragment.applyErrorState() {
    android.widget.Toast
        .makeText(
            context,
            getText(com.redbox.mirumon.R.string.error_message),
            android.widget.Toast.LENGTH_LONG
        )
        .show()
}
