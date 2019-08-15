package com.ambe.tekotest.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.ambe.tekotest.helper.State
import com.ambe.tekotest.interfaces.NetworkService
import com.ambe.tekotest.model.Products
import com.ambe.tekotest.model.Result
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

/**
 *  Created by AMBE on 6/8/2019 at 13:47 PM.
 */
class ProductsDataSource(
    private val networkService: NetworkService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Products>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null

    // https://github.com/DongHien0896/Traning-Kotlin

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Products>
    ) {
        updateState(State.LOADING)
        compositeDisposable.add(networkService.getProducts(1, params.requestedLoadSize)
            .subscribe(
                { response ->


                    updateState(State.DONE)
                    callback.onResult(
                        response.result.products,
                        null,
                        2
                    )
                },
                {

                    Log.e("AAAAAAAAAAA", it.toString())
                    updateState(State.ERROR)
                    setRetry(Action { loadInitial(params, callback) })

                }
            ))
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Products>) {

        updateState(State.LOADING)
        compositeDisposable.add(
            networkService.getProducts(params.key, params.requestedLoadSize)
                .subscribe(
                    { response ->


                        updateState(State.DONE)
                        callback.onResult(
                            response.result.products,
                            params.key + 1
                        )
                    },
                    {
                        updateState(State.ERROR)
                        setRetry(Action { loadAfter(params, callback) })

                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Products>) {
    }
}