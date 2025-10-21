package com.example.fitmatch.model

data class VendedorProfile(
    val userId: String,
    val storeName: String,
    val storeDescription: String,
    val rating: Float,
    val followers: Int,
    val following: Int
)
