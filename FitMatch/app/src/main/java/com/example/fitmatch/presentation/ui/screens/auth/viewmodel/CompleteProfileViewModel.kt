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
                                selectedGender = it.gender,
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
        validateForm()
    }

    fun onGenderSelected(gender: String) {
        _uiState.update {
            it.copy(
                selectedGender = gender,
                isGenderDropdownExpanded = false
            )
        }
        validateForm()
    }

    fun onGenderDropdownToggle() {
        _uiState.update {
            it.copy(isGenderDropdownExpanded = !it.isGenderDropdownExpanded)
        }
    }

    fun onRoleSelected(role: String) {
        _uiState.update { it.copy(selectedRole = role, errorMessage = null) }
        validateForm()
    }

    private fun validateForm(): String?{
        val state = _uiState.value
        // Fecha de nacimiento vacía
        if (state.birthDate.isBlank()) {
            return "La fecha de nacimiento es obligatoria"
        }

        // Validar formato de fecha (básico)
        if (!isValidDateFormat(state.birthDate)) {
            return "Formato de fecha inválido. Usa: dd/mm/aaaa"
        }

        // Rol no seleccionado
        if (state.selectedRole.isBlank()) {
            return "Selecciona cómo quieres usar la app"
        }

        // Ciudad vacía
        if (state.city.isBlank()) {
            return "La ciudad es obligatoria"
        }

        if (state.phone.isBlank()) {
            return "El numero de telefono es obligatorio"
        }

        if (!isValidPhone(state.phone)) {
            return "El numero de telefono debe tener 10 dígitos"
        }

        if (state.selectedGender.isBlank()) {
            return "Selecciona tu genero"
        }

        return null // Formulario válido
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
                    gender = state.selectedGender,
                    role = state.selectedRole,
                    phone = state.phone,
                    profileCompleted = true, // Marcar como completado
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

    /**
     * Valida formato básico de fecha: dd/mm/aaaa o dd-mm-aaaa
     */
    private fun isValidDateFormat(date: String): Boolean {
        // Acepta dd/mm/aaaa o dd-mm-aaaa
        val dateRegex = Regex("^\\d{1,2}[/-]\\d{1,2}[/-]\\d{4}$")
        return dateRegex.matches(date.trim())
    }

    /**
     * Verifica que un número de teléfono tenga 10 dígitos.
     * @param phone Número de teléfono a verificar.
     * @return true si el número tiene 10 dígitos, false en caso contrario.
     */
    private fun isValidPhone(phone: String): Boolean {
        // Acepta números con 10 dígitos
        val phoneRegex = Regex("^\\d{10}$")
        return phoneRegex.matches(phone.trim())
    }
}