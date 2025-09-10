package com.example.fitmatch.presentation.ui.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview

data class Product(
    val name: String,
    val brand: String,
    val quantity: Int,
    val isChecked: Boolean = true
)

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StorePickupScreen() {
    val products = remember {
        listOf(
            Product("Camiseta Básica", "Nike", 1),
            Product("Jeans Slim Fit", "", 1),
            Product("Bolso de Mano", "", 1)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header card (centrado)
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
                    text = "Recogida en Tienda",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Confirma la recepción de los productos",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Address card (centrada)
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Cra 15 #93-47, Apto 302, Chapinero",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Pedido #MNX-24818",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // QR Code section
            Text(
                text = "Verificación del Handover",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        BorderStroke(2.dp, Color.Gray),
                        RoundedCornerShape(16.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    QRCodePlaceholder()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Escanea este código con el vendedor",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // 🔹 Divider entre handover y checklist
            Divider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )

            // Product checklist
            Text(
                text = "Checklist de Productos: Verifica que el pedido esté completo",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column {
                    products.forEachIndexed { index, product ->
                        ProductChecklistItem(
                            product = product,
                            showDivider = index < products.size - 1
                        )
                    }
                }
            }

            // 🔹 Divider entre checklist y notas
            Divider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )

            // Notes section
            Text(
                text = "Notas del pedido",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = "",
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* Handle confirm */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Confirmar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun QRCodePlaceholder() {
    Canvas(
        modifier = Modifier.size(80.dp)
    ) {
        val squareSize = size.width / 6
        val strokeWidth = 4.dp.toPx()

        // Draw QR code pattern
        // Top-left corner
        drawRect(
            color = Color.Black,
            topLeft = Offset(0f, 0f),
            size = androidx.compose.ui.geometry.Size(squareSize * 3, squareSize * 3),
            style = Stroke(width = strokeWidth)
        )
        drawRect(
            color = Color.Black,
            topLeft = Offset(squareSize, squareSize),
            size = androidx.compose.ui.geometry.Size(squareSize, squareSize)
        )

        // Top-right corner
        drawRect(
            color = Color.Black,
            topLeft = Offset(squareSize * 3.5f, 0f),
            size = androidx.compose.ui.geometry.Size(squareSize * 2.5f, squareSize * 3),
            style = Stroke(width = strokeWidth)
        )
        drawRect(
            color = Color.Black,
            topLeft = Offset(squareSize * 4.5f, squareSize),
            size = androidx.compose.ui.geometry.Size(squareSize, squareSize)
        )

        // Bottom-left corner
        drawRect(
            color = Color.Black,
            topLeft = Offset(0f, squareSize * 3.5f),
            size = androidx.compose.ui.geometry.Size(squareSize * 3, squareSize * 2.5f),
            style = Stroke(width = strokeWidth)
        )
        drawRect(
            color = Color.Black,
            topLeft = Offset(squareSize, squareSize * 4.5f),
            size = androidx.compose.ui.geometry.Size(squareSize, squareSize)
        )

        // Some random QR pattern squares
        drawRect(
            color = Color.Black,
            topLeft = Offset(squareSize * 4, squareSize * 4),
            size = androidx.compose.ui.geometry.Size(squareSize, squareSize)
        )
        drawRect(
            color = Color.Black,
            topLeft = Offset(squareSize * 5, squareSize * 5),
            size = androidx.compose.ui.geometry.Size(squareSize, squareSize)
        )
    }
}

@Composable
fun ProductChecklistItem(
    product: Product,
    showDivider: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product initial circle
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = product.name.first().toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Product info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (product.brand.isNotEmpty()) {
                    Text(
                        text = product.brand,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Quantity
            Text(
                text = "x${product.quantity}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Checkbox
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // Divider
        if (showDivider) {
            Divider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }
}

// Preview
@Composable
fun StorePickupScreenPreview() {
    MaterialTheme {
        StorePickupScreen()
    }
}