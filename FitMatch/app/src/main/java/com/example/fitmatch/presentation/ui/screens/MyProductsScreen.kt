package com.example.fitmatch.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ProductStatus(
    val id: String,
    val name: String,
    val isSelected: Boolean = false
)

data class MyProduct(
    val id: String,
    val title: String,
    val subtitle: String,
    val stock: Int,
    val price: String,
    val soldQuantity: Int,
    val status: String,
    val imageSize: String? = null,
    val hasSpecialBorder: Boolean = false
)

data class ProductAction(
    val name: String,
    val backgroundColor: Color,
    val textColor: Color = Color.White
)

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProductsScreen(
    onBackClick: () -> Unit = {},
    onAddProductClick: () -> Unit = {},
    onProductActionClick: (String, String) -> Unit = { _, _ -> }
) {
    var selectedStatus by remember { mutableStateOf("todos") }

    val statusFilters = listOf(
        ProductStatus("todos", "Todos", selectedStatus == "todos"),
        ProductStatus("publicados", "Publicados", selectedStatus == "publicados"),
        ProductStatus("borradores", "Borradores", selectedStatus == "borradores"),)

    val products = listOf(
        MyProduct(
            id = "1",
            title = "Pantalón Baggy Negro",
            subtitle = "Pantalón - Denim - 32",
            stock = 12,
            price = "$15.000 COP",
            soldQuantity = 12,
            status = "Publicado",
            hasSpecialBorder = true
        ),
        MyProduct(
            id = "2",
            title = "Pantalón Baggy Negro",
            subtitle = "Pantalón - Denim - 32",
            stock = 12,
            price = "$15.000 COP",
            soldQuantity = 12,
            status = "Publicado",
            imageSize = "371 × 338"
        )
    )

    val productActions = listOf(
        ProductAction("Editar", Color(0xFF8B4513)),
        ProductAction("Pausar", Color(0xFF8B4513)),
        ProductAction("Duplicar", Color(0xFF8B4513)),
        ProductAction("Variantes", Color(0xFF8B4513)),
        ProductAction("Eliminar", Color(0xFF8B4513))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5DC))
    ) {
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
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.Black
                    )
                }

                Text(
                    text = "Mis Productos",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                IconButton(onClick = onAddProductClick) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar producto",
                        tint = Color.Black
                    )
                }
            }
        }

        // Filtros de estado
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
        ) {
            items(statusFilters) { status ->
                StatusFilterChip(
                    status = status,
                    onClick = { selectedStatus = status.id }
                )
            }
        }

        // Lista de productos
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    actions = productActions,
                    onActionClick = { action ->
                        onProductActionClick(product.id, action)
                    }
                )
            }
        }
    }
}

@Composable
private fun StatusFilterChip(
    status: ProductStatus,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.height(36.dp),
        color = if (status.isSelected) Color(0xFF8B4513) else Color.White,
        shape = RoundedCornerShape(18.dp),
        border = if (!status.isSelected) {
            androidx.compose.foundation.BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f))
        } else null
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Text(
                text = status.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (status.isSelected) Color.White else Color.Black
            )
        }
    }
}

@Composable
private fun ProductCard(
    product: MyProduct,
    actions: List<ProductAction>,
    onActionClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (product.hasSpecialBorder) {
                    Modifier.border(
                        2.dp,
                        Color(0xFF2196F3),
                        RoundedCornerShape(16.dp)
                    )
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Imagen placeholder
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            Color.Gray.copy(alpha = 0.2f),
                            RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (product.imageSize != null) {
                        // Mostrar dimensiones de imagen
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Image,
                                contentDescription = "Imagen",
                                tint = Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = product.imageSize,
                                fontSize = 8.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = "Imagen del producto",
                            tint = Color.Gray,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Información del producto
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = product.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = product.subtitle,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Badge de estado
                    Surface(
                        color = Color(0xFF8B4513),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        Text(
                            text = product.status,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Métricas del producto
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = product.stock.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Stock",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = product.price,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Precio",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = product.soldQuantity.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Vendidos",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones de acciones
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Primera fila de botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ActionButton(
                        action = actions[0], // Editar
                        modifier = Modifier.weight(1f),
                        onClick = { onActionClick(actions[0].name) }
                    )
                    ActionButton(
                        action = actions[1], // Pausar
                        modifier = Modifier.weight(1f),
                        onClick = { onActionClick(actions[1].name) }
                    )
                    ActionButton(
                        action = actions[2], // Duplicar
                        modifier = Modifier.weight(1f),
                        onClick = { onActionClick(actions[2].name) }
                    )
                }

                // Segunda fila de botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ActionButton(
                        action = actions[3], // Variantes
                        modifier = Modifier.weight(1f),
                        onClick = { onActionClick(actions[3].name) }
                    )
                    ActionButton(
                        action = actions[4], // Eliminar
                        modifier = Modifier.weight(1f),
                        onClick = { onActionClick(actions[4].name) }
                    )
                    // Spacer para mantener el layout
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun ActionButton(
    action: ProductAction,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(32.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = action.backgroundColor
        ),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = action.name,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = action.textColor
        )
    }
}