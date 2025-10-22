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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.FitMatchTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompleteProfileScreen(
    userId: String,
    onBackClick: () -> Unit = {},
    onContinue: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme

    var birthDate by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("Cliente") }

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
                "Solo faltan algunos datos...",
                style = MaterialTheme.typography.bodyLarge
            )

            // Fecha de nacimiento
            OutlinedTextField(
                value = birthDate,
                onValueChange = { birthDate = it },
                label = { Text("Fecha de Nacimiento") },
                placeholder = { Text("dd/mm/aaaa") },
                modifier = Modifier.fillMaxWidth()
            )

            // Ciudad
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Ciudad") },
                placeholder = { Text("Bogotá") },
                modifier = Modifier.fillMaxWidth()
            )

            // Teléfono (opcional)
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Teléfono (Opcional)") },
                placeholder = { Text("+57 300 123 4567") },
                modifier = Modifier.fillMaxWidth()
            )

            // Rol
            Text("¿Cómo usarás la app?", fontWeight = FontWeight.Medium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = selectedRole == "Cliente",
                    onClick = { selectedRole = "Cliente" },
                    label = { Text("Comprador") }
                )
                FilterChip(
                    selected = selectedRole == "Vendedor",
                    onClick = { selectedRole = "Vendedor" },
                    label = { Text("Vendedor") }
                )
            }

            Spacer(Modifier.weight(1f))

            // Botón continuar
            Button(
                onClick = {
                    // TODO: Actualizar perfil en Firestore
                    // firestore.collection("users").document(userId).update(...)
                    onContinue()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = birthDate.isNotBlank() && city.isNotBlank()
            ) {
                Text("Continuar", fontWeight = FontWeight.Medium)
            }
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