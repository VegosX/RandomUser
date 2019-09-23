package com.magnise.random_users.model.api.service

import com.magnise.random_users.model.api.UserModel
import retrofit2.Call
import retrofit2.http.GET

interface UsersApiService {

    @GET("?results=100")
    fun getUsers(): Call<UserModel>
}