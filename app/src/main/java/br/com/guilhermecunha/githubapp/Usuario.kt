package br.com.guilhermecunha.githubapp

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("name") val nome: String,
    @SerializedName("avatar_url") val avatarUrl: String
)