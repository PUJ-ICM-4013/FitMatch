package com.example.fitmatch.model.user

data class User(
    val id: String,                    // UID de Firebase Auth
    val email: String,
    val fullName: String,
    val birthDate: String,
    val city: String,
    val gender: String?,
    val role: String,                  // "Cliente" | "Vendedor"
    val avatarEmoji: String,
    val phone: String
)
