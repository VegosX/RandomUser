package com.magnise.random_users.model.api.model

import com.google.gson.annotations.SerializedName

data class Model(
    @SerializedName("results")
    val results: List<UserModel>,
    @SerializedName("info")
    val info: Info
)

data class Info (
    @SerializedName("seed")
    val seed: String,
    @SerializedName("results")
    val results: Long,
    @SerializedName("page")
    val page: Long,
    @SerializedName("version")
    val version: String
)

data class UserModel (
    @SerializedName("name")
    val name: Name,
    @SerializedName("picture")
    val picture: Picture,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("dob")
    val dob: Dob
)

data class Name (
    @SerializedName("title")
    val title: String,
    @SerializedName("first")
    val first: String,
    @SerializedName("last")
    val last: String
)

data class Picture (
    @SerializedName("large")
    val large: String,
    @SerializedName("medium")
    val medium: String
)

data class Dob (
    @SerializedName("age")
    val age: Int
)