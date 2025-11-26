package com.example.fitmatch.model.product

import kotlinx.serialization.Serializable

@Serializable
data class WearProduct(
    val id: String,
    val title: String,
    val brand: String,
    val price: Int,
    val imageUrl: String,
    val category: String,
    val color: String,
    val size: String = ""
)