package com.ambe.tekotest.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.ambe.tekotest.interfaces.NetworkService
import com.ambe.tekotest.model.Products
import io.reactivex.disposables.CompositeDisposable

/**
 *  Created by AMBE on 6/8/2019 at 14:02 PM.
 */
class ProductsDataSourceFactory(

    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService
) : DataSource.Factory<Int, Products>() {

     val productsDataSourceLiveData = MutableLiveData<ProductsDataSource>()

    override fun create(): DataSource<Int, Products> {
        val newsDataSource = ProductsDataSource(networkService, compositeDisposable)
        productsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}