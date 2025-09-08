package com.example.fitmatch.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class FilterCategory(
    val id: String,
    val name: String,
    val isSelected: Boolean = false
)

data class FilterChip(
    val id: String,
    val name: String,
    val color: Color,
    val textColor: Color = Color.White
)

data class PriceRange(
    val min: String,
    val max: String
)

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackClick: () -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }

    // Categorías principales
    val categories = listOf(
        FilterCategory("todos", "Todos", true),
        FilterCategory("tiendas", "Tiendas"),
        FilterCategory("productos", "Productos"),
        FilterCategory("hashtags", "Hashtags")
    )

    // Filtros avanzados - Estilos
    val styleFilters = listOf(
        FilterChip("casual", "Casual", Color(0xFF8B4513)),
        FilterChip("elegante", "Elegante", Color(0xFF8B4513)),
        FilterChip("deportivo", "Deportivo", Color(0xFF8B4513))
    )

    // Filtros - Tipo de prenda
    val clothingFilters = listOf(
        FilterChip("abrigos", "Abrigos", Color(0xFF8B4513)),
        FilterChip("vestidos", "Vestidos", Color(0xFF8B4513)),
        FilterChip("zapatos", "Zapatos", Color(0xFF8B4513)),
        FilterChip("jeans", "Jeans", Color(0xFF8B4513))
    )

    // Filtros - Colores
    val colorFilters = listOf(
        FilterChip("azul", "Azul", Color(0xFF2196F3)),
        FilterChip("negro", "Negro", Color(0xFF424242)),
        FilterChip("rojo", "Rojo", Color(0xFFF44336)),
        FilterChip("blanco", "Blanco", Color(0xFFF5F5F5), Color.Black)
    )

    // Rangos de precio
    val priceRanges = listOf(
        PriceRange("Hasta $20.000", ""),
        PriceRange("Hasta $40.000", ""),
        PriceRange("Hasta $70.000", ""),
        PriceRange("Hasta $100.000", ""),
        PriceRange("Hasta $150.000", ""),
        PriceRange("Hasta $300.000", "")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            // Header con búsqueda
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Barra de búsqueda
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.Black
                        )
                    }

                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = { Text("Escribe aquí...", color = Color.Gray) },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                            cursorColor = Color(0xFF8B4513)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar",
                                tint = Color.Gray
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = { /* Handle add */ },
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                Color(0xFFE8E8E8),
                                RoundedCornerShape(12.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Añadir",
                            tint = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Categorías principales
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(categories) { category ->
                        FilterCategoryChip(
                            category = category,
                            onClick = { /* Handle category selection */ }
                        )
                    }
                }
            }
        }

        item {
            // Filtros Avanzados
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Filtros Avanzados",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Estilos
                Text(
                    text = "Estilos",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    items(styleFilters) { filter ->
                        FilterChipItem(
                            filter = filter,
                            onClick = { /* Handle filter */ }
                        )
                    }
                }

                // Tipo de prenda
                Text(
                    text = "Tipo de prenda",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    items(clothingFilters) { filter ->
                        FilterChipItem(
                            filter = filter,
                            onClick = { /* Handle filter */ }
                        )
                    }
                }

                // Colores
                Text(
                    text = "Colores",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    items(colorFilters) { filter ->
                        FilterChipItem(
                            filter = filter,
                            onClick = { /* Handle filter */ }
                        )
                    }
                }
            }
        }

        item {
            // Rangos de precio
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Rangos de precio",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Grid de precios (2 columnas)
                for (i in priceRanges.indices step 2) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Primera columna
                        PriceRangeChip(
                            priceRange = priceRanges[i],
                            modifier = Modifier.weight(1f),
                            onClick = { /* Handle price selection */ }
                        )

                        // Segunda columna (si existe)
                        if (i + 1 < priceRanges.size) {
                            PriceRangeChip(
                                priceRange = priceRanges[i + 1],
                                modifier = Modifier.weight(1f),
                                onClick = { /* Handle price selection */ }
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        item {
            // Ubicación
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Ubicación",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Placeholder para mapa
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE8F5E8)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Ubicación",
                                tint = Color(0xFF4CAF50),
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Mapa de ubicación",
                                fontSize = 12.sp,
                                color = Color(0xFF4CAF50)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Bogotá DC, Colombia",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
private fun FilterCategoryChip(
    category: FilterCategory,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.height(40.dp),
        color = if (category.isSelected) Color(0xFF8B4513) else Color(0xFFF5F5F5),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Text(
                text = category.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (category.isSelected) Color.White else Color.Black
            )
        }
    }
}

@Composable
private fun FilterChipItem(
    filter: FilterChip,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.height(32.dp),
        color = filter.color,
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = filter.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = filter.textColor
            )
        }
    }
}

@Composable
private fun PriceRangeChip(
    priceRange: PriceRange,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(36.dp),
        color = Color(0xFFF5F5F5),
        shape = RoundedCornerShape(18.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = priceRange.min,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}