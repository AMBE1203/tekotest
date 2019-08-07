package com.ambe.tekotest.ui.detail


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ambe.tekotest.BaseFragment
import com.ambe.tekotest.R
import com.ambe.tekotest.helper.Utils.setStatusBarGradient
import com.ambe.tekotest.model.Products
import kotlinx.android.synthetic.main.fragment_product_detail.*
import java.lang.Exception


class ProductDetailFragment : BaseFragment() {

    private val TAG = ProductDetailFragment::class.java.simpleName

    private lateinit var productViewModel: ProductViewModel
    private lateinit var previewAdapter: PreviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setStatusBarGradient(this, R.drawable.bg_fr_product_detail)
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productViewModel = ViewModelProviders.of(activity!!).get(ProductViewModel::class.java)


        productViewModel.getProductSelected().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                setupUI(it)
            }
        })
    }

    private fun setupUI(products: Products) {
        if (products.displayName != "") {
            txt_name_detail.text = products.displayName
            txt_title_detail.text = products.displayName
        } else {
            txt_name_detail.text = products.name
            txt_title_detail.text = products.name

        }

        txt_ma_sp.text = products.sku

        previewAdapter = PreviewAdapter(context!!, products.images)
        viewPager.adapter = previewAdapter
        try {
            indicator.setViewPager(viewPager)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        @JvmStatic
        fun getInstance() = ProductDetailFragment()
    }


}
