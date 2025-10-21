package com.example.fitmatch.model.user

data class VendedorProfile(
    val userId: String = "",
    val storeName: String = "",
    val storeDescription: String = "",
    val rating: Float = 0f,
    val followers: Int = 0,
    val following: Int = 0
)
