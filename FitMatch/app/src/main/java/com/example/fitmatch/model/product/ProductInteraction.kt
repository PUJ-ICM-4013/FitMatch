package com.example.fitmatch.model

data class ProductInteraction(
    val id: String,
    val userId: String,                // Cliente
    val productId: String,
    val action: String,                // "LIKE" | "PASS" | "SAVE"
    val timestamp: Timestamp
)