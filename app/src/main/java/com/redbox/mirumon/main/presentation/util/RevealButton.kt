package com.redbox.mirumon.main.presentation.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.redbox.mirumon.R
import kotlinx.android.synthetic.main.reveal_button.view.*

class RevealButton constructor(
    context: Context,
    attributeSet: AttributeSet
) : ConstraintLayout(context, attributeSet) {

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