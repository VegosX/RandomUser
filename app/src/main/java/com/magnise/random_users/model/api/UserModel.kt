package com.magnise.random_users.model.api

import com.google.gson.annotations.SerializedName

//This model is for getting fields from server to UserDataSource ->

data class UserModel(
    @SerializedName("results")
    val results: List<Results>,
    @SerializedName("info")
    val info: Infos
)

data class Infos (
    @SerializedName("seed")
    val seed: String,
    @SerializedName("results")
    val results: Long,
    @SerializedName("page")
    val page: Long,
    @SerializedName("version")
    val version: String
)

data class Results (
    @SerializedName("name")
    val name: Names,
    @SerializedName("picture")
    val picture: Pictures
)

data class Names (
    @SerializedName("title")
    val title: String,
    @SerializedName("first")
    val first: String,
    @SerializedName("last")
    val last: String
)

data class Pictures (
    @SerializedName("large")
    val large: String
)