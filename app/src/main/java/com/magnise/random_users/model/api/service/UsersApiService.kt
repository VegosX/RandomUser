package com.magnise.random_users.model.api.service

import com.magnise.random_users.model.api.model.Model
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApiService {

    @GET(".")
    fun getUsers(
        @Query("page") page: Int,
        @Query("results") numOfUsers: Int,
        @Query("seed") seed: String
    ): Observable<Model>
}