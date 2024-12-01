package com.dicoding.ping.ui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.ping.R

class ButtonSubmit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.buttonStyle
) : AppCompatButton(context, attrs, defStyleAttr) {

    init {
        background = ContextCompat.getDrawable(context, R.drawable.register_button_selector)
        setTextColor(ContextCompat.getColor(context, android.R.color.white))
        textSize = 20f
        gravity = Gravity.CENTER
        isAllCaps = false
    }
}