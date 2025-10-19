package com.example.fitmatch.presentation.ui.screens.common.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.FitMatchTheme
import com.example.fitmatch.presentation.ui.screens.common.state.DeliveryPickupUiState
import com.example.fitmatch.presentation.ui.screens.common.state.OrderDeliveryInfo
import com.example.fitmatch.presentation.ui.screens.common.state.TripStep
import com.example.fitmatch.presentation.ui.screens.common.viewmodel.DeliveryEvent
import com.example.fitmatch.presentation.ui.screens.common.viewmodel.DeliveryPickupViewModel


@Composable
fun DeliveryPickupScreen(
    vm: DeliveryPickupViewModel = viewModel(),
    onDial: (String) -> Unit = {},
    onOpenChat: (String) -> Unit = {},
    //onNavigate: (lat: Double, lng: Double, address: String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val uiState by vm.uiState.collectAsState()
    val snackbarHost = remember { SnackbarHostState() }

    // Mostrar mensajes del estado (error/success)
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { snackbarHost.showSnackbar(it) }
    }
    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let { snackbarHost.showSnackbar(it) }
    }

    DeliveryPickupScreen(
        uiState = uiState,
        snackbarHost = snackbarHost,
        onBackClick = onBackClick,
        onCall = vm::onCall,
        onChat = vm::onChat,
        onNavigateClick = vm::onNavigate,
        onPrimaryCta = vm::onMarkStepComplete,
        onDismissMessage = vm::onDismissMessage
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryPickupScreen(
    uiState: DeliveryPickupUiState,
    snackbarHost: SnackbarHostState = remember { SnackbarHostState() },
    onBackClick: () -> Unit = {},
    onCall: () -> Unit = {},
    onChat: () -> Unit = {},
    onPrimaryCta: () -> Unit = {},
    onNavigateClick: () -> Unit = {},
    onDismissMessage: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme

    Scaffold(
        containerColor = colors.background,
        snackbarHost = { SnackbarHost(hostState = snackbarHost) },
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = colors.surface,
                tonalElevation = 1.dp,
                shadowElevation = 1.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart)) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = colors.onSurface)
                    }
                    Text(
                        text = "Entrega / Recogida",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold, color = colors.onSurface),
                        maxLines = 1,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    Row(modifier = Modifier.align(Alignment.CenterEnd), verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { /* overflow */ }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Más opciones", tint = colors.onSurface)
                        }
                    }
                }
            }
        }
    ) { inner ->
        Column(modifier = Modifier.fillMaxSize().padding(inner)) {
            /* ───────────── Header de estado ───────────── */
            HeaderCard(
                eta = uiState.estimatedTime,
                orderNumber = uiState.order?.orderNumber ?: "—",
                title = if (uiState.isPickupStep) "En camino a recogida" else "En camino a entrega"
            )

            //mapa
            Box(
                modifier = Modifier.fillMaxWidth().height(300.dp).background(colors.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.LocationOn, contentDescription = "Mapa", tint = colors.onPrimaryContainer)
                    Spacer(Modifier.height(8.dp))
                    Text("Mapa de ubicación", style = MaterialTheme.typography.titleMedium, color = colors.onPrimaryContainer)
                }
            }

            //detalles
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = colors.surface),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                border = BorderStroke(1.dp, colors.outline.copy(alpha = 0.12f))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Box(
                        modifier = Modifier.width(40.dp).height(4.dp).background(colors.onSurfaceVariant.copy(alpha = 0.35f), RoundedCornerShape(2.dp)).align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = colors.onSurface)
                        Spacer(Modifier.width(8.dp))
                        Text("Pasos del viaje", style = MaterialTheme.typography.titleMedium)
                    }

                    Spacer(Modifier.height(12.dp))

                    // Cliente
                    CustomerNoteCard(
                        initials = uiState.order?.customerInitials ?: "—",
                        nameWithAge = "${uiState.order?.customerName ?: "Cliente"} · ${uiState.order?.createdDaysAgo ?: 0}d",
                        note = uiState.order?.customerNote ?: ""
                    )

                    Spacer(Modifier.height(12.dp))

                    // Pasos
                    Column(Modifier.fillMaxWidth()) {
                        uiState.tripSteps.forEachIndexed { index, step ->
                            StepContainer {
                                TripStepRow(
                                    step = step,
                                    showConnector = index < uiState.tripSteps.lastIndex
                                )
                            }
                            if (index < uiState.tripSteps.lastIndex) Spacer(Modifier.height(0.dp))
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    // Acciones
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(onClick = onNavigateClick, modifier = Modifier.weight(1f), colors = ButtonDefaults.outlinedButtonColors(contentColor = colors.onSurface), border = BorderStroke(1.dp, colors.onSurface.copy(alpha = 0.12f)), shape = RoundedCornerShape(24.dp)) { Text("Navegar") }
                        OutlinedButton(onClick = onCall, modifier = Modifier.weight(1f), colors = ButtonDefaults.outlinedButtonColors(contentColor = colors.onSurface), border = BorderStroke(1.dp, colors.onSurface.copy(alpha = 0.12f)), shape = RoundedCornerShape(24.dp)) { Text("Llamar") }
                        OutlinedButton(onClick = onChat, modifier = Modifier.weight(1f), colors = ButtonDefaults.outlinedButtonColors(contentColor = colors.onSurface), border = BorderStroke(1.dp, colors.onSurface.copy(alpha = 0.12f)), shape = RoundedCornerShape(24.dp)) { Text("Chatear") }
                    }

                    Spacer(Modifier.height(12.dp))

                    val ctaLabel = when {
                        uiState.isPickupStep -> "Marcar como recogido"
                        uiState.isDeliveryStep -> "Marcar como entregado"
                        else -> "Continuar"
                    }

                    Button(
                        onClick = onPrimaryCta,
                        enabled = uiState.canMarkComplete && !uiState.isMarkingComplete,
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        if (uiState.isMarkingComplete) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                        } else {
                            Text(ctaLabel, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun HeaderCard(eta: String, orderNumber: String, title: String) {
    val colors = MaterialTheme.colorScheme
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colors.surface),
        shape = RoundedCornerShape(0.dp),
        border = BorderStroke(1.dp, colors.outline.copy(alpha = 0.12f))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocalShipping, contentDescription = "Estado", tint = colors.onSurface)
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(title, style = MaterialTheme.typography.titleMedium)
                    Text(orderNumber, style = MaterialTheme.typography.labelMedium, color = colors.onSurfaceVariant)
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(eta, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text("ETA", style = MaterialTheme.typography.labelMedium, color = colors.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun CustomerNoteCard(initials: String, nameWithAge: String, note: String) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = Modifier.fillMaxWidth().border(1.dp, colors.outline, RoundedCornerShape(12.dp)).padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(colors.primary), contentAlignment = Alignment.Center) {
                Text(initials, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = colors.onPrimary)
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(nameWithAge, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Medium)
                if (note.isNotBlank()) {
                    Text("“$note”", style = MaterialTheme.typography.bodySmall, color = colors.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
private fun StepContainer(content: @Composable () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Box(modifier = Modifier.fillMaxWidth().border(1.dp, colors.outline, RoundedCornerShape(12.dp)).padding(12.dp)) { content() }
}

//el viaje/recorrido en el mapa
@Composable
private fun TripStepRow(step: TripStep, showConnector: Boolean) {
    val colors = MaterialTheme.colorScheme
    Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(40.dp)) {
            Box(
                modifier = Modifier.size(28.dp).clip(CircleShape).border(width = 2.dp, color = if (step.isActive) colors.primary else colors.outline, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(step.stepNumber.toString(), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = if (step.isActive) colors.primary else colors.onSurfaceVariant)
            }

            if (showConnector) {
                Spacer(Modifier.height(6.dp))
                Box(modifier = Modifier.width(2.dp).height(40.dp)) {
                    Box(modifier = Modifier.fillMaxSize().background(colors.outline.copy(alpha = 0.8f)))
                }
            }
        }
        Spacer(Modifier.width(8.dp))
        Column(modifier = Modifier.padding(top = 2.dp)) {
            Text(text = step.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(4.dp))
            Text(text = step.address, style = MaterialTheme.typography.bodyMedium, color = colors.onSurfaceVariant)
            Spacer(Modifier.height(2.dp))
            Text(text = step.timeWindow, style = MaterialTheme.typography.labelMedium, color = colors.onSurfaceVariant)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, name = "DeliveryPickup–Preview")
@Composable
private fun DeliveryPickupPreview() {
    val mockState = DeliveryPickupUiState(
        order = OrderDeliveryInfo(
            orderId = "MIX-24816",
            orderNumber = "#MIX-24816",
            customerName = "María González",
            customerInitials = "MG",
            customerNote = "Entregar en recepción del edificio",
            createdDaysAgo = 1
        ),
        tripSteps = listOf(
            TripStep(1, "Recogida en Tortini", "CC Centro Mayor, local 201", "2:00 – 3:00 p. m.", isActive = true),
            TripStep(2, "Entrega al cliente", "Cra 15 #93-47, Apto 302, Chapinero", "3:30 – 4:00 p. m.", isActive = false)
        ),
        currentStepIndex = 0,
        estimatedTime = "12 min"
    )
    FitMatchTheme { DeliveryPickupScreen(uiState = mockState) }
}