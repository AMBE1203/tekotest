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
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.content.ContextCompat
import android.opengl.ETC1.getWidth
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MotionEvent
import android.widget.RelativeLayout
import android.widget.PopupWindow
import com.ambe.tekotest.helper.Const
import com.ambe.tekotest.helper.State
import com.ambe.tekotest.helper.Utils
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


class ProductListFragment : BaseFragment() {

    private lateinit var viewModel: ProductsListViewModel
    private lateinit var adapter: ProductsListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setStatusBarGradiant(activity!! as Activity)
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductsListViewModel::class.java)
        setupUI()
        initState()
    }

    private fun setupUI() {

        if (!Utils.checkInternetConnection(context!!)) {
            Snackbar.make(
                activity!!.findViewById(android.R.id.content),
                Const.CHECK_NETWORK_ERROR,
                Snackbar.LENGTH_SHORT
            )
                .show()
        }


        adapter = ProductsListAdapter { viewModel.retry() }
        rcv_product.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rcv_product.adapter = adapter
        viewModel.productsList.observe(viewLifecycleOwner, Observer {
            if (it != null) {

                adapter.submitList(it)
            }
        })
    }

    private fun initState() {
        txt_error.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            progress_bar.visibility =
                if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility =
                if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                adapter.setState(state ?: State.DONE)
            }
        })
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
