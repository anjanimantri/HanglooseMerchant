package com.vungle.hangloosemerchant.network

import android.content.Context
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.vungle.hangloosemerchant.model.LoginRequest
import com.vungle.hangloosemerchant.model.LoginResponse
import com.vungle.hangloosemerchant.model.RestaurantList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

class RetrofitHelper(val context: Context) {
    fun create(): ApiInf {
        val gsonBuilder = GsonBuilder()
            .setLenient()
            .create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.hangloose.in/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(ApiInf::class.java)
    }
}

interface ApiInf {
    @POST("users/login")
    fun login(@Body loginRequestBody: LoginRequest): Call<LoginResponse>

    @GET("restaurants/{restaurantId}")
    fun getRestaurantById(@Path("restaurantId") restaurantId: String): Call<RestaurantList>
}