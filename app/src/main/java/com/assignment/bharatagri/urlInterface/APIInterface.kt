package com.assignment.bharatagri.urlInterface

import com.assignment.bharatagri.model.Result
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET("popular")
    fun getMovies(@Query("api_key") api_key: String): Single<Result>
}


