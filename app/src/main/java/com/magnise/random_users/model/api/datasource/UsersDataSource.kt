package com.magnise.random_users.model.api.datasource

import com.google.gson.GsonBuilder
import com.magnise.random_users.model.api.UserModel
import com.magnise.random_users.model.api.service.UsersApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UsersDataSource {

    private const val URL = "https://randomuser.me/api/"

    private val gson by lazy {
        GsonBuilder()
            .setLenient()
            .create()
    }

    private val okHttpClient by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val apiService: UsersApiService by lazy {
        retrofit.create(UsersApiService::class.java)
    }

    fun getUsers(): Call<List<UserModel>> {
        return apiService.getUsers()
    }
}