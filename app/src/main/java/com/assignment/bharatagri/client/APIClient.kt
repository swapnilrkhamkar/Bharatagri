package com.assignment.bharatagri.client

import com.assignment.bharatagri.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {

    /**
     * @return a retrofit client to call all transaction related apis
     */

    fun getClient(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }
}
