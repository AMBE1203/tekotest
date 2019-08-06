package com.ambe.tekotest.ui.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.ambe.tekotest.datasource.ProductsDataSource
import com.ambe.tekotest.datasource.ProductsDataSourceFactory
import com.ambe.tekotest.helper.State
import com.ambe.tekotest.interfaces.NetworkService
import com.ambe.tekotest.model.Products
import io.reactivex.disposables.CompositeDisposable

/**
 *  Created by AMBE on 6/8/2019 at 14:13 PM.
 */
class ProductsListViewModel : ViewModel() {

    private val networkService = NetworkService.getService()

    var productsList: LiveData<PagedList<Products>>

    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 10
    private val productsDataSourceFactory: ProductsDataSourceFactory

    init {
        productsDataSourceFactory = ProductsDataSourceFactory(compositeDisposable, networkService)

        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2) // thiet lap gia tri so item tai trong lan dau tien
            .setEnablePlaceholders(false) //để là true nếu muốn PagedList hiển thị cả những item null, không được load đầy đủ.
            .build()

        productsList = LivePagedListBuilder<Int, Products>(productsDataSourceFactory, config).build()
    }

    fun getState(): LiveData<State> = Transformations.switchMap<ProductsDataSource,
            State>(productsDataSourceFactory.productsDataSourceLiveData, ProductsDataSource::state)

    fun retry() {
        productsDataSourceFactory.productsDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return productsList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}