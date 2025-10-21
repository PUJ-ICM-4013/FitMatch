package com.example.fitmatch.model

data class OrderItem(
    val productId: String,
    val title: String,
    val price: Int,
    val quantity: Int,
    val size: String,
    val color: String
)
