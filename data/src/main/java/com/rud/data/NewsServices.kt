package com.rud.data

import com.rud.data.model.NewsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * newsapi key 4b4df2ea3a154950852b6fda536cfb7f
 */
interface NewsServices {

    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String
    ): Single<NewsResponse>

    @GET("everything")
    fun getEverything(
        @Query("q") query: String,
        @Query("sortBy") sortBy: String
    ): Single<NewsResponse>
}