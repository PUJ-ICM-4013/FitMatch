package com.example.fitmatch.presentation.ui.screens.auth.state

data class CompleteProfileUiState(
    val userId: String = "",
    val email: String = "",
    val fullName: String = "",
    val birthDate: String = "",
    val city: String = "Bogotá, Colombia",
    val phone: String = "",
    val selectedRole: String = "Cliente",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isFormValid: Boolean = false
)