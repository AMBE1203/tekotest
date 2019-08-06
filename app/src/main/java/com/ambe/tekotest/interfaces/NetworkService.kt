package com.ambe.tekotest.interfaces

import com.ambe.tekotest.BuildConfig
import com.ambe.tekotest.helper.Const
import com.ambe.tekotest.model.Response
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor



/**
 *  Created by AMBE on 6/8/2019 at 13:41 PM.
 */
interface NetworkService {

    @GET("?channel=pv_online&visitorId=&q=&terminal=CP01")
    fun getProducts(@Query("_page") page: Int, @Query("_limit") pageSize: Int): Single<Response>

    companion object {
        fun getService(): NetworkService {
//            val client = OkHttpClient.Builder()
//            if (BuildConfig.DEBUG) {
//                val interceptor = HttpLoggingInterceptor()
//                interceptor.level = HttpLoggingInterceptor.Level.BODY
//                client.addInterceptor(interceptor)
//            }

            val retrofit = Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(NetworkService::class.java)
        }
    }
}