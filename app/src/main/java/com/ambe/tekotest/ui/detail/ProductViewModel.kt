package com.ambe.tekotest.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ambe.tekotest.model.Products

/**
 *  Created by AMBE on 7/8/2019 at 10:08 AM.
 */
class ProductViewModel : ViewModel() {

    private val productSelected = MutableLiveData<Products>()

    fun setProductSelected(products: Products) {
        productSelected.postValue(products)
    }

    fun getProductSelected(): MutableLiveData<Products> {
        return productSelected
    }
}