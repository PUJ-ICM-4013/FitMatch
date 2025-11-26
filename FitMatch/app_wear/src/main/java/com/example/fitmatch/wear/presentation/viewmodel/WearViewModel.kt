package com.example.fitmatch.wear.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitmatch.wear.data.WearDataLayerManager
import com.example.fitmatch.wear.model.WearProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

data class WearUiState(
    val currentProduct: WearProduct? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isConnected: Boolean = false
)

class WearViewModel(
    private val context: Context
) : ViewModel() {

    private val dataLayerManager = WearDataLayerManager(context)

    private val _uiState = MutableStateFlow(WearUiState())
    val uiState: StateFlow<WearUiState> = _uiState.asStateFlow()

    /**
     * Paso 2: Usuario abre app → solicitar siguiente prenda
     */
    fun requestNextProduct() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                dataLayerManager.requestNextProduct()
                // Esperar a que el móvil envíe la prenda
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "No hay conexión con el móvil"
                    )
                }
            }
        }
    }

    /**
     * Paso 4: Mostrar prenda en el reloj
     */
    fun receiveProduct(productJson: String) {
        try {
            val product = Json.decodeFromString<WearProduct>(productJson)
            _uiState.update {
                it.copy(
                    currentProduct = product,
                    isLoading = false,
                    errorMessage = null
                )
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Error al procesar prenda"
                )
            }
        }
    }

    /**
     * Paso 5a: Usuario toca ✔ (LIKE)
     */
    fun onLike() {
        val productId = _uiState.value.currentProduct?.id ?: return
        viewModelScope.launch {
            try {
                dataLayerManager.sendLike(productId)
                // Solicitar siguiente prenda automáticamente
                requestNextProduct()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Error al enviar like")
                }
            }
        }
    }

    /**
     * 🟩 Paso 5b: Usuario toca ✖ (PASS)
     */
    fun onPass() {
        val productId = _uiState.value.currentProduct?.id ?: return
        viewModelScope.launch {
            try {
                dataLayerManager.sendPass(productId)
                // Solicitar siguiente prenda automáticamente
                requestNextProduct()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Error al enviar pass")
                }
            }
        }
    }
}