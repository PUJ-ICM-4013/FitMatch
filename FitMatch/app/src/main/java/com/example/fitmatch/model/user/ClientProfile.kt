package com.example.fitmatch.model.user

data class ClientProfile(
    val userId: String,
    val preferredSizes: Set<String>,
    val preferredStyles: Set<String>,
    val preferredColors: Set<String>,
    val preferredCategories: Set<String>,
    val language: String = "Español"
)