package com.ambe.tekotest.ui.list


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ambe.tekotest.BaseFragment

import com.ambe.tekotest.R
import android.view.WindowManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.app.Activity
import android.annotation.TargetApi
import android.support.v4.content.ContextCompat


class ProductListFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setStatusBarGradiant(activity!!)
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }


    companion object {
        @JvmStatic
        fun getInstance() = ProductListFragment()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarGradiant(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            val background = ContextCompat.getDrawable(context!!, R.drawable.bg_tb_product_list)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(context!!, android.R.color.transparent)
            //   window.navigationBarColor = activity.resources.getColor(android.R.color.transparent)
            window.setBackgroundDrawable(background)
        }
    }


}
