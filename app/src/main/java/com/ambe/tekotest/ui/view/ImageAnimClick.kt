package com.ambe.tekotest.ui.view

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.ambe.tekotest.R

/**
 *  Created by AMBE on 23/5/2019 at 17:18 PM.
 */
class ImageAnimClick: AppCompatImageView {
    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.ImageClick, 0, 0)
        val clickAble = array.getBoolean(R.styleable.ImageClick_clickAble, true)
        array.recycle()
        isClickable = clickAble
        if (clickAble) {
            setBackgroundResource(R.drawable.ripple_click_img)
        }
    }
}