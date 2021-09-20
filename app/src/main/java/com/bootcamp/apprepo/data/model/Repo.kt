package com.bootcamp.apprepo.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Repo (
    val id: Long,
    val name: String,
    val owner: Owner,
    @SerializedName("html_url")
    val htmlURL: String,
    val description: String,
    @SerializedName("stargazers_count")
    val stargazersCount: Long,
    val language: String
): Serializable

data class Owner (
    val login: String,
    @SerializedName("avatar_url")
    val avatarURL: String,
    @SerializedName("html_url")
    val htmlUrl: String
): Serializable
