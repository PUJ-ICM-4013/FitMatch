package com.example.fitmatch.presentation.ui.screens.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitmatch.data.auth.AuthRepository
import com.example.fitmatch.data.auth.FirebaseAuthRepository
import com.example.fitmatch.model.user.User
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.fitmatch.presentation.ui.screens.auth.state.CompleteProfileUiState

class CompleteProfileViewModel(
    private val authRepository: AuthRepository = FirebaseAuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(CompleteProfileUiState())
    val uiState: StateFlow<CompleteProfileUiState> = _uiState.asStateFlow()

    /**
     * Inicializa el ViewModel con los datos del usuario de Google
     */
    fun initializeWithUserId(userId: String) {
        viewModelScope.launch {
            try {
                val result = authRepository.getUserProfile(userId)
                result.onSuccess { user ->
                    user?.let {
                        _uiState.update { state ->
                            state.copy(
                                userId = it.id,
                                email = it.email,
                                fullName = it.fullName,
                                birthDate = it.birthDate,
                                city = it.city.ifBlank { "Bogotá, Colombia" },
                                phone = it.phone ?: "",
                                selectedRole = it.role.ifBlank { "Cliente" }
                            )
                        }
                        validateForm()
                    }
                }.onFailure { e ->
                    _uiState.update { it.copy(errorMessage = "Error: ${e.message}") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Error: ${e.message}") }
            }
        }
    }

    fun onBirthDateChanged(value: String) {
        _uiState.update { it.copy(birthDate = value, errorMessage = null) }
        validateForm()
    }

    fun onCityChanged(value: String) {
        _uiState.update { it.copy(city = value, errorMessage = null) }
        validateForm()
    }

    fun onPhoneChanged(value: String) {
        _uiState.update { it.copy(phone = value, errorMessage = null) }
    }

    fun onRoleSelected(role: String) {
        _uiState.update { it.copy(selectedRole = role, errorMessage = null) }
        validateForm()
    }

    private fun validateForm() {
        val state = _uiState.value
        val isValid = state.birthDate.isNotBlank() &&
                state.city.isNotBlank() &&
                state.selectedRole.isNotBlank()
        _uiState.update { it.copy(isFormValid = isValid) }
    }

    /**
     * Actualiza el perfil en Firestore con los datos completados
     */
    fun updateProfile(onSuccess: (role: String) -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value

            if (!state.isFormValid) {
                _uiState.update { it.copy(errorMessage = "Por favor completa todos los campos obligatorios") }
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val updatedUser = User(
                    id = state.userId,
                    email = state.email,
                    fullName = state.fullName,
                    birthDate = state.birthDate.trim(),
                    city = state.city.trim(),
                    gender = "", // Opcional, se puede completar después
                    role = state.selectedRole,
                    phone = state.phone.takeIf { it.isNotBlank() },
                    profileCompleted = true, // ✅ Marcar como completado
                    createdAt = Timestamp.now(), // Firestore lo mantiene
                    updatedAt = Timestamp.now()
                )

                val result = authRepository.createUserProfile(updatedUser)

                result.onSuccess {
                    _uiState.update { it.copy(isLoading = false) }
                    onSuccess(state.selectedRole)
                }.onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Error al actualizar perfil: ${e.message}"
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error inesperado: ${e.message}"
                    )
                }
            }
        }
    }
}