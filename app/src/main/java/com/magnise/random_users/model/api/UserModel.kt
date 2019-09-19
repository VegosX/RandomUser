package com.magnise.random_users.model.api

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("results")
    val results: Results,
    @SerializedName("info")
    val info: Infos
)

data class Infos (
    val seed: String,
    val results: Long,
    val page: Long,
    val version: String
)

data class Results (
    val name: Names,
    val picture: Pictures
)

data class Names (
    val title: String,
    val first: String,
    val last: String
)

data class Pictures (
    val large: String,
    val medium: String,
    val thumbnail: String
)