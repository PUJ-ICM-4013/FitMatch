package com.example.fitmatch.model

data class Address(
    val id: String,
    val userId: String,
    val label: String,
    val street: String,
    val city: String,
    val country: String,
    val isPrimary: Boolean
)
