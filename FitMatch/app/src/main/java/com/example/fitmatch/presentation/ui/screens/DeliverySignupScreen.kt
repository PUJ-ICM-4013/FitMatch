package com.example.fitmatch.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliverySignupScreen() {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Banner en un cuadro diferenciado - ESTILO CORREGIDO
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = RoundedCornerShape(0.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Únete como Repartidor",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Completa tu perfil para comenzar a entregar",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        // Secciones de documentos
        DocumentSection(
            icon = Icons.Default.Description,
            title = "Documentos Personales",
            subtitle = "Documento de identidad (MÁX 5MB)",
            actionText = "Subir Documentos",
            actionIcon = Icons.Default.Upload
        )

        Spacer(modifier = Modifier.height(16.dp))

        DocumentSection(
            icon = Icons.Default.DirectionsCar,
            title = "Información del Vehículo",
            subtitle = "SOAT, Licencia de tránsito, foto (MÁX 5MB)",
            actionText = "Subir Documentos",
            actionIcon = Icons.Default.Upload
        )

        Spacer(modifier = Modifier.height(16.dp))

        DocumentSection(
            icon = Icons.Default.Security,
            title = "Verificación de Identidad",
            subtitle = "Recuerda estar en lugar con luz clara",
            actionText = "Selfie de Verificación",
            actionIcon = Icons.Default.CameraAlt
        )

        Spacer(modifier = Modifier.height(16.dp))

        DocumentSection(
            icon = Icons.Default.AccountBalance,
            title = "Cuenta Bancaria",
            subtitle = "",
            actionText = "Agregar Cuenta Bancaria",
            actionIcon = Icons.Default.Add
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Botón de envío con MaterialTheme
        Button(
            onClick = { /* Handle submit */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.primary
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Subir",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun DocumentSection(
    icon: ImageVector,
    title: String,
    subtitle: String,
    actionText: String,
    actionIcon: ImageVector
) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle click */ },
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorScheme.onSurface
                )
            }

            if (subtitle.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorScheme.surfaceVariant)
                    .clickable { /* Handle action */ },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = null,
                        tint = colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = actionText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorScheme.onSurface
                    )
                }
            }
        }
    }
}
