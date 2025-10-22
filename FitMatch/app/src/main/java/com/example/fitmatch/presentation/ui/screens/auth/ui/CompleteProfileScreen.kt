package com.example.fitmatch.presentation.ui.screens.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.FitMatchTheme
import com.example.fitmatch.presentation.ui.screens.auth.viewmodel.CompleteProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompleteProfileScreen(
    userId: String,
    onBackClick: () -> Unit = {},
    onContinue: (role: String) -> Unit = {},
    viewModel: CompleteProfileViewModel = viewModel()
) {
    val colors = MaterialTheme.colorScheme
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Inicializar con el userId cuando se monta el composable
    LaunchedEffect(userId) {
        viewModel.initializeWithUserId(userId)
    }

    Scaffold(
        containerColor = colors.background,
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = colors.surface,
                tonalElevation = 1.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = colors.onSurface
                        )
                    }

                    Text(
                        text = "Completa tu perfil",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 22.sp
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Hola ${uiState.fullName.split(" ").firstOrNull() ?: ""}! 👋",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = colors.primary
            )

            Text(
                "Solo faltan algunos datos para empezar...",
                style = MaterialTheme.typography.bodyLarge,
                color = colors.onSurfaceVariant
            )

            Spacer(Modifier.height(8.dp))

            // Email (readonly)
            OutlinedTextField(
                value = uiState.email,
                onValueChange = {},
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = colors.outline,
                    disabledTextColor = colors.onSurface,
                    disabledLabelColor = colors.onSurfaceVariant
                )
            )

            // Nombre completo (readonly)
            OutlinedTextField(
                value = uiState.fullName,
                onValueChange = {},
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = colors.outline,
                    disabledTextColor = colors.onSurface,
                    disabledLabelColor = colors.onSurfaceVariant
                )
            )

            // Fecha de nacimiento
            OutlinedTextField(
                value = uiState.birthDate,
                onValueChange = { viewModel.onBirthDateChanged(it) },
                label = { Text("Fecha de Nacimiento *") },
                placeholder = { Text("dd/mm/aaaa") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colors.primary,
                    unfocusedBorderColor = colors.outline
                )
            )

            // Ciudad
            OutlinedTextField(
                value = uiState.city,
                onValueChange = { viewModel.onCityChanged(it) },
                label = { Text("Ciudad *") },
                placeholder = { Text("Bogotá") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colors.primary,
                    unfocusedBorderColor = colors.outline
                )
            )

            // Teléfono (opcional)
            OutlinedTextField(
                value = uiState.phone,
                onValueChange = { viewModel.onPhoneChanged(it) },
                label = { Text("Teléfono (Opcional)") },
                placeholder = { Text("+57 300 123 4567") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colors.primary,
                    unfocusedBorderColor = colors.outline
                )
            )

            Spacer(Modifier.height(8.dp))

            // Rol
            Text(
                "¿Cómo usarás la app? *",
                fontWeight = FontWeight.Medium,
                color = colors.primary
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = uiState.selectedRole == "Cliente",
                    onClick = {
                        if (!uiState.isLoading) {
                            viewModel.onRoleSelected("Cliente")
                        }
                    },
                    label = { Text("Comprador") },
                    enabled = !uiState.isLoading
                )
                FilterChip(
                    selected = uiState.selectedRole == "Vendedor",
                    onClick = {
                        if (!uiState.isLoading) {
                            viewModel.onRoleSelected("Vendedor")
                        }
                    },
                    label = { Text("Vendedor") },
                    enabled = !uiState.isLoading
                )
            }

            // Mensaje de error
            if (uiState.errorMessage != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = colors.errorContainer
                    )
                ) {
                    Text(
                        text = uiState.errorMessage!!,
                        color = colors.onErrorContainer,
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            // Botón continuar
            Button(
                onClick = {
                    viewModel.updateProfile { role ->
                        onContinue(role)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = uiState.isFormValid && !uiState.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    disabledContainerColor = colors.surfaceVariant
                )
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = colors.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "Continuar",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }
            }

            Text(
                "* Campos obligatorios",
                style = MaterialTheme.typography.bodySmall,
                color = colors.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompleteProfileScreenPreview() {
    FitMatchTheme {
        CompleteProfileScreen(
            userId = "123",
            onBackClick = {},
            onContinue = {}
        )
    }
}