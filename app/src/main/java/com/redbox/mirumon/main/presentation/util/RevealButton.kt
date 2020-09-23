package com.redbox.mirumon.main.presentation.util

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.redbox.mirumon.R
import kotlinx.android.synthetic.main.reveal_button.view.*
import java.nio.file.Path

class RevealButton constructor(
    context: Context,
    attributeSet: AttributeSet
) : ConstraintLayout(context, attributeSet) {

    companion object{
        fun animateView(context: Context,view: View,btn: RevealButton){
            val slideUpAnim = AnimationUtils.loadAnimation(context,R.anim.slide_up)
            val slideDownAnim = AnimationUtils.loadAnimation(context, R.anim.slide_down)
            when(btn.stateOpened){
                true -> {
                    view.startAnimation(slideDownAnim)
                    view.isVisible = btn.stateOpened
                }
                else -> {
                    view.startAnimation(slideUpAnim)
                    android.os.Handler().postDelayed({
                        view.isVisible = btn.stateOpened
                    },275)
                }
            }
        }
    }

    var buttonIcon: ImageView? = null
    var buttonText: TextView? = null
    var stateOpened: Boolean = false

    init {
        val inflater =
            getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.reveal_button, this)
        buttonIcon = icon_iv
        buttonText = btn_txt_tv
        setAttrs(attributeSet)
    }

    fun setActionListener(listener: () -> Unit) {
        reveal_layout.setOnClickListener {
            listener()
            openList()
        }
    }
//    fun animateView(context: Context,view: View,btn: RevealButton,lowerView: View){
//        val slideUpAnim = AnimationUtils.loadAnimation(context,R.anim.slide_up)
//        val slideDownAnim = AnimationUtils.loadAnimation(context, R.anim.slide_down)
//        when(btn.stateOpened){
//            true -> {
//                view.startAnimation(slideDownAnim)
//                view.isVisible = true
//            }
//            else -> {
//                view.startAnimation(slideUpAnim)
//                ObjectAnimator.ofFloat(lowerView,"translationY",-view.height.toFloat()).apply{
//                    duration = 300
//                    start()
//                }
//                view.isVisible = false
//            }
//        }
//    }


    private fun setAttrs(attributeSet: AttributeSet?) {
        val attributes = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.RevealButton,
            0,
            0
        )
        setButtonText(attributes.getString(R.styleable.RevealButton_revealButtonText))
        setIcon(attributes.getDrawable(R.styleable.RevealButton_revealButtonIcon))
    }

    private fun setButtonText(text: String?) {
        buttonText?.text = text
    }

    private fun setIcon(drawable: Drawable?) {
        buttonIcon?.setImageDrawable(drawable)
    }

    private fun openList() {
        if (!stateOpened) {
            open_iv.rotation = 180F
            stateOpened = true
        } else {
            open_iv.rotation = 0F
            stateOpened = false
        }
    }
}