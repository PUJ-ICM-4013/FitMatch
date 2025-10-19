package com.example.fitmatch.presentation.ui.screens.common.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitmatch.presentation.ui.screens.common.state.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow


class DeliveryPickupViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DeliveryPickupUiState())
    val uiState: StateFlow<DeliveryPickupUiState> = _uiState.asStateFlow()

    private val _events = Channel<DeliveryEvent>()
    val events = _events.receiveAsFlow()

    init {
        // Obtener orderId de los argumentos de navegación
        val orderId = savedStateHandle.get<String>("orderId") ?: "MIX-24816"
        loadOrderDetails(orderId)
        startRealtimeTracking()
    }


    //marcar el "paso actual" completado
    fun onMarkStepComplete() {
        viewModelScope.launch {
            val currentStep = _uiState.value.currentStep ?: return@launch

            _uiState.update { it.copy(isMarkingComplete = true, errorMessage = null) }

            // TODO: Llamar al repositorio para actualizar estado
            delay(1000)

            // Actualizar paso a completado
            _uiState.update { currentState ->
                val updatedSteps = currentState.tripSteps.mapIndexed { index, step ->
                    if (index == currentState.currentStepIndex) {
                        step.copy(isCompleted = true, isActive = false)
                    } else step
                }

                val nextStepIndex = currentState.currentStepIndex + 1
                val hasNextStep = nextStepIndex < currentState.tripSteps.size

                // Activar siguiente paso si existe
                val finalSteps = if (hasNextStep) {
                    updatedSteps.mapIndexed { index, step ->
                        if (index == nextStepIndex) step.copy(isActive = true)
                        else step
                    }
                } else updatedSteps

                currentState.copy(
                    tripSteps = finalSteps,
                    currentStepIndex = if (hasNextStep) nextStepIndex else currentState.currentStepIndex,
                    isMarkingComplete = false,
                    successMessage = if (hasNextStep)
                        "✓ Recogida completada"
                    else
                        "✓ Entrega completada"
                )
            }

            // Enviar evento si completó todo
            if (_uiState.value.currentStepIndex >= _uiState.value.tripSteps.size) {
                _events.send(DeliveryEvent.OrderCompleted)
            }

            // Limpiar mensaje después de 2 segundos
            delay(2000)
            _uiState.update { it.copy(successMessage = null) }
        }
    }

    //llamar a la tienda o al cliente
    fun onCall() {
        viewModelScope.launch {
            val phoneNumber = if (_uiState.value.isPickupStep) {
                // Número de la tienda
                "+57 300 111 2222"
            } else {
                // Número del cliente
                "+57 300 123 4567"
            }
            _events.send(DeliveryEvent.MakeCall(phoneNumber))
        }
    }

    //abrir chat
    fun onChat() {
        viewModelScope.launch {
            val chatId = _uiState.value.order?.orderId ?: return@launch
            _events.send(DeliveryEvent.OpenChat(chatId))
        }
    }

    // navegación a la ubicación que ponga en el mapa
    fun onNavigate() {
        viewModelScope.launch {
            val step = _uiState.value.currentStep ?: return@launch

            if (step.latitude != null && step.longitude != null) {
                _events.send(
                    DeliveryEvent.NavigateToLocation(
                        lat = step.latitude,
                        lng = step.longitude,
                        address = step.address
                    )
                )
            } else {
                _uiState.update {
                    it.copy(errorMessage = "Ubicación no disponible")
                }
            }
        }
    }

    //actualización ubicación a tiempo real
    fun onLocationUpdate(lat: Double, lng: Double) {
        // TODO: Enviar ubicación al backend para tracking
        calculateETA(lat, lng)
    }

    // ========== LÓGICA PRIVADA ==========

    private fun loadOrderDetails(orderId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // TODO: Llamar al repositorio
            delay(500)

            val mockOrder = OrderDeliveryInfo(
                orderId = orderId,
                orderNumber = "#MIX-24816",
                customerName = "María González",
                customerInitials = "MG",
                customerNote = "Entregar en recepción del edificio",
                createdDaysAgo = 1
            )

            val mockSteps = listOf(
                TripStep(
                    stepNumber = 1,
                    title = "Recogida en Tortini",
                    address = "CC Centro Mayor, local 201",
                    timeWindow = "2:00 – 3:00 p. m.",
                    isActive = true,
                    latitude = 4.6097,
                    longitude = -74.0817
                ),
                TripStep(
                    stepNumber = 2,
                    title = "Entrega al cliente",
                    address = "Cra 15 #93-47, Apto 302, Chapinero",
                    timeWindow = "3:30 – 4:00 p. m.",
                    isActive = false,
                    latitude = 4.6751,
                    longitude = -74.0570
                )
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    order = mockOrder,
                    tripSteps = mockSteps,
                    estimatedTime = "12 min"
                )
            }
        }
    }

    private fun startRealtimeTracking() {
        viewModelScope.launch {
            // TODO: Conectar a Firebase para tracking en tiempo real
            // Por ahora simulamos actualizaciones cada 30 segundos
            while (true) {
                delay(30_000)
                updateEstimatedTime()
            }
        }
    }

    private fun updateEstimatedTime() {
        // Simulación de actualización de ETA
        val currentEta = _uiState.value.estimatedTime.split(" ")[0].toIntOrNull() ?: 0
        if (currentEta > 0) {
            _uiState.update {
                it.copy(estimatedTime = "${currentEta - 1} min")
            }
        }
    }

    private fun calculateETA(currentLat: Double, currentLng: Double) {
        val destination = _uiState.value.currentStep ?: return

        // TODO: Usar API de Mapa para hacer el calculo y si no tiene para hacerlo, usen esta, pero no dará la distancia real
        // Por ahora calculamos distancia euclidiana simple
        val destLat = destination.latitude ?: return
        val destLng = destination.longitude ?: return
        //no sé hacer elevados a y no encontré la libreria JAJAJAJ
        //TODO: Busquenla ustedes jajaj
        val distance = 30//kotlin.math.sqrt(
//            kotlin.math.pow(destLat - currentLat, 2.0) +
//                    kotlin.math.pow(destLng - currentLng, 2.0)
//        )

        // Estimar tiempo (muy aproximado: grado= 111km, velocidad promedio 30km/h)
        //val estimatedMinutes = (distance * 111 * 60 / 30).toInt()

        _uiState.update {
            it.copy(estimatedTime = "tiempo estimado...") //"$estimatedMinutes min")
        }
    }

    fun onDismissMessage() {
        _uiState.update { it.copy(errorMessage = null, successMessage = null) }
    }
}

//el contacto con el de la entrega y sobre la entrega
sealed class DeliveryEvent {
    data class MakeCall(val phoneNumber: String) : DeliveryEvent()
    data class OpenChat(val chatId: String) : DeliveryEvent()
    data class NavigateToLocation(
        val lat: Double,
        val lng: Double,
        val address: String
    ) : DeliveryEvent()
    object OrderCompleted : DeliveryEvent()
}