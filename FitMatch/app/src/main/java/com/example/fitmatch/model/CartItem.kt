package com.example.fitmatch.model

data class CartItem(
    val id: String,
    val userId: String,
    val productId: String,
    val quantity: Int,
    val size: String,
    val color: String,
    val addedAt: Timestamp
)