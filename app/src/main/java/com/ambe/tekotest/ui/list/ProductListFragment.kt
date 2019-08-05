package com.ambe.tekotest.ui.list


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ambe.tekotest.BaseFragment

import com.ambe.tekotest.R


class ProductListFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }


    companion object {
        @JvmStatic
        fun getInstance() = ProductListFragment()
    }


}
