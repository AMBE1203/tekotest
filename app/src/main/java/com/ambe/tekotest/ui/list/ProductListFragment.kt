package com.ambe.tekotest.ui.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ambe.tekotest.BaseFragment

import com.ambe.tekotest.R
import android.view.WindowManager
import android.os.Build
import android.app.Activity
import android.annotation.TargetApi
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.ambe.tekotest.helper.Const
import com.ambe.tekotest.helper.State
import com.ambe.tekotest.helper.Utils
import com.ambe.tekotest.helper.Utils.setStatusBarGradient
import com.ambe.tekotest.model.Products
import com.ambe.tekotest.ui.detail.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product_list.*


class ProductListFragment : BaseFragment() {

    private val TAG = ProductListFragment::class.java.simpleName

    private lateinit var viewModel: ProductsListViewModel
    private lateinit var adapter: ProductsListAdapter
    private lateinit var productViewModel: ProductViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setStatusBarGradient(this, R.drawable.bg_tb_product_list)
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductsListViewModel::class.java)
        productViewModel = ViewModelProviders.of(activity!!).get(ProductViewModel::class.java)
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

        adapter.setListener(object : ProductsListAdapter.IProductsListAdapterListner {
            override fun clickItem(products: Products?) {
                productViewModel.setProductSelected(products!!)
                navController.navigate(R.id.action_productListFragment_to_productDetailFragment)
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


}
