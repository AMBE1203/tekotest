package com.ambe.tekotest.interfaces

import android.content.Context
import com.ambe.tekotest.BuildConfig
import com.ambe.tekotest.helper.Const
import com.ambe.tekotest.helper.Utils
import com.ambe.tekotest.model.Response
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.Single
import okhttp3.Cache
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
        fun getService(context: Context): NetworkService {
            val cacheSize = (5 * 1024 * 1024).toLong()
            val myCache = Cache(context.cacheDir, cacheSize)

            val okHttpClient = OkHttpClient.Builder()
                .cache(myCache)
                .addInterceptor { chain ->
                    var request = chain.request()
                    request = if (Utils.checkInternetConnection(context))
                        request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                    else
                        request.newBuilder().header(
                            "Cache-Control",
                            "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                        ).build()
                    chain.proceed(request)
                }
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(NetworkService::class.java)
        }
    }
}