package com.dicoding.ping.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.dicoding.ping.R

@SuppressLint("CustomViewStyleable")
class BtnSave @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val textView: TextView
    private val progressBar: ProgressBar

    init {
        LayoutInflater.from(context).inflate(R.layout.view_my_button, this, true)
        textView = findViewById(R.id.buttonText)
        progressBar = findViewById(R.id.buttonProgress)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.MyButton, 0, 0)
            val buttonText = typedArray.getString(R.styleable.MyButton_text)
            if (!buttonText.isNullOrEmpty()) {
                textView.text = buttonText
            } else {
                textView.text = context.getString(R.string.save)
            }

            val buttonBackground =
                typedArray.getDrawable(R.styleable.MyButton_android_background)
                    ?: ContextCompat.getDrawable(context, R.drawable.register_button_selector)

            background = buttonBackground
            typedArray.recycle()
        }
    }

    fun showLoading(show: Boolean) {
        textView.visibility = if (show) View.GONE else View.VISIBLE
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }
}
