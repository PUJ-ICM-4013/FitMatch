package com.example.fitmatch.wear.presentation.viewmodel

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.fitmatch.wear.data.WearDataLayerListenerService
import com.example.fitmatch.wear.data.WearDataLayerManager
import com.example.fitmatch.wear.model.WearProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.json.JSONObject

data class WearUiState(
    val currentProduct: WearProduct? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isConnected: Boolean = false,
    val matches: Int = 0,
    val messages: Int = 0
)

class WearViewModel(application: Application) : AndroidViewModel(application) {

    private val dataLayerManager = WearDataLayerManager(getApplication())

    private val _uiState = MutableStateFlow(WearUiState())
    val uiState: StateFlow<WearUiState> = _uiState.asStateFlow()

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.getStringExtra(WearDataLayerListenerService.EXTRA_MESSAGE_DATA)?.let { data ->
                try {
                    val json = JSONObject(data)
                    _uiState.update { currentState ->
                        currentState.copy(
                            matches = json.optInt("matches", currentState.matches),
                            messages = json.optInt("messages", currentState.messages)
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    init {
        LocalBroadcastManager.getInstance(getApplication()).registerReceiver(
            broadcastReceiver,
            IntentFilter(WearDataLayerListenerService.ACTION_DATA_UPDATED)
        )
    }

    override fun onCleared() {
        super.onCleared()
        LocalBroadcastManager.getInstance(getApplication()).unregisterReceiver(broadcastReceiver)
    }

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
     * Paso 5b: Usuario toca ✖ (PASS)
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