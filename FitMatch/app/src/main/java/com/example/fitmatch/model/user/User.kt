package com.example.fitmatch.model.user

import com.google.firebase.Timestamp

data class User(
    val id: String = "",
    val email: String = "",
    val fullName: String = "",
    val birthDate: String = "",
    val city: String = "",
    val gender: String? = null,
    val role: String, // "Cliente" | "Vendedor"
    val avatarEmoji: String,
    val phone: String? = null,
    val createdAt: Timestamp,
    val updatedAt: Timestamp
)
