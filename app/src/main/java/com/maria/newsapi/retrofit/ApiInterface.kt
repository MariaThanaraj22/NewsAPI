package com.maria.newsapi.retrofit

import com.maria.newsapi.model.KotlinMainResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
    @GET("top-headlines?country=us&category=business&apiKey=cdce29ae962d4f48af90747cd6b8a885")
    fun getheadlines(): Call<KotlinMainResponse>

    @GET("everything?q=bitcoin&from=2020-04-12&sortBy=publishedAt&apiKey=cdce29ae962d4f48af90747cd6b8a885")
    fun getBitcoin(): Call<KotlinMainResponse>

    @GET("everything?q=apple&from=2020-05-11&to=2020-05-11&sortBy=popularity&apiKey=cdce29ae962d4f48af90747cd6b8a885")
    fun getmentioningApple(): Call<KotlinMainResponse>

    @GET("top-headlines?sources=techcrunch&apiKey=cdce29ae962d4f48af90747cd6b8a885")
    fun getTechCrunch(): Call<KotlinMainResponse>

    @GET("everything?domains=wsj.com&apiKey=cdce29ae962d4f48af90747cd6b8a885")
    fun getWallStreetJournal(): Call<KotlinMainResponse>

    companion object {
        operator fun invoke(): ApiInterface {
            return Retrofit.Builder()
                .baseUrl(" http://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }
}
