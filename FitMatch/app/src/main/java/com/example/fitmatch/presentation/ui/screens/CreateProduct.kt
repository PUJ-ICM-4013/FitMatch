package com.example.fitmatch.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SizeOption(
    val id: String,
    val name: String,
    val isSelected: Boolean = false
)

data class ColorOption(
    val id: String,
    val name: String,
    val color: Color,
    val isSelected: Boolean = false
)

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProductScreen(
    onCloseClick: () -> Unit = {},
    onSaveDraftClick: () -> Unit = {},
    onPublishClick: () -> Unit = {}
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var sizeGuide by remember { mutableStateOf("") }
    var labels by remember { mutableStateOf("") }

    val sizeOptions = listOf(
        SizeOption("xs", "XS"),
        SizeOption("s", "S", true), // S seleccionado
        SizeOption("m", "M"),
        SizeOption("l", "L"),
        SizeOption("xl", "XL")
    )

    val colorOptions = listOf(
        ColorOption("negro", "Negro", Color(0xFF424242), true), // Negro seleccionado
        ColorOption("blanco", "Blanco", Color(0xFFF5F5F5)),
        ColorOption("rojo", "Rojo", Color(0xFFF44336)),
        ColorOption("beige", "Beige", Color(0xFFF5F5DC))
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5DC))
    ) {
        item {
            // Header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Crear publicación",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2196F3)
                    )

                    IconButton(onClick = onCloseClick) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = Color.Black
                        )
                    }
                }
            }
        }

        item {
            // Sección de imágenes
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    repeat(3) { index ->
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(
                                    Color.Gray.copy(alpha = 0.3f),
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { /* Handle image selection */ },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Agregar imagen",
                                tint = Color.Gray,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    // Botón agregar más imágenes
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF8B4513))
                            .clickable { /* Handle add more images */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar más",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Sugerencia: agrega hasta 10 fotos y/o videos (carrusel)",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item {
            // Campos del formulario
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Título
                Column {
                    Text(
                        text = "Título",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF8B4513),
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                            cursorColor = Color(0xFF8B4513)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                }

                // Descripción
                Column {
                    Text(
                        text = "Descripción",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF8B4513),
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                            cursorColor = Color(0xFF8B4513)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                }

                // Precio
                Column {
                    Text(
                        text = "Precio",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF8B4513),
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                            cursorColor = Color(0xFF8B4513)
                        ),
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                // Tallas
                Column {
                    Text(
                        text = "Tallas",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(sizeOptions) { size ->
                            SizeChip(
                                size = size,
                                onClick = { /* Handle size selection */ }
                            )
                        }
                    }
                }

                // Colores
                Column {
                    Text(
                        text = "Colores",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(colorOptions) { color ->
                            ColorChip(
                                color = color,
                                onClick = { /* Handle color selection */ }
                            )
                        }
                    }
                }

                // Guía de Tallas y Etiquetas (fila)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Guía de Tallas
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Guía de Tallas",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        OutlinedTextField(
                            value = sizeGuide,
                            onValueChange = { sizeGuide = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF8B4513),
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                                cursorColor = Color(0xFF8B4513)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }

                    // Etiquetas
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Etiquetas",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        OutlinedTextField(
                            value = labels,
                            onValueChange = { labels = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF8B4513),
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                                cursorColor = Color(0xFF8B4513)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botones de acción
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Guardar borrador
                    OutlinedButton(
                        onClick = onSaveDraftClick,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.Gray),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Text(
                            text = "Guardar borrador",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Publicar
                    Button(
                        onClick = onPublishClick,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF8B4513)
                        ),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Text(
                            text = "Publicar",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun SizeChip(
    size: SizeOption,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.size(40.dp),
        color = if (size.isSelected) Color(0xFF8B4513) else Color.White,
        shape = CircleShape,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (size.isSelected) Color(0xFF8B4513) else Color.Gray.copy(alpha = 0.5f)
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = size.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = if (size.isSelected) Color.White else Color.Black
            )
        }
    }
}

@Composable
private fun ColorChip(
    color: ColorOption,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .height(32.dp)
            .then(
                if (color.isSelected) {
                    Modifier.border(2.dp, Color(0xFF8B4513), RoundedCornerShape(16.dp))
                } else {
                    Modifier
                }
            ),
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        border = if (!color.isSelected) {
            androidx.compose.foundation.BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f))
        } else null
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Círculo de color
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(color.color)
                    .border(1.dp, Color.Gray.copy(alpha = 0.3f), CircleShape)
            )

            Text(
                text = color.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}