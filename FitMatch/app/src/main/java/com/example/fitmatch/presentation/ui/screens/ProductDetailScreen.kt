package com.example.fitmatch.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ProductDetail(
    val title: String,
    val originalPrice: String,
    val currentPrice: String,
    val isOnSale: Boolean,
    val brand: String,
    val size: String,
    val category: String,
    val condition: String,
    val color: String,
    val mascotMessage: String,
    val sellerName: String,
    val sellerImage: String? = null
)

data class DetailSection(
    val title: String,
    val icon: ImageVector,
    val isExpandable: Boolean = false,
    val content: String = ""
)

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    onBackClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    onBuyClick: () -> Unit = {}
) {
    val productDetail = ProductDetail(
        title = "Pantalón Blanco",
        originalPrice = "$300,000",
        currentPrice = "$300,000",
        isOnSale = false,
        brand = "Jeans",
        size = "XS 30 US 42 EU",
        category = "Jeans",
        condition = "En perfecto estado",
        color = "Negro",
        mascotMessage = "Tito cree que esta prenda es apropiada para ti!",
        sellerName = "Planeta vintage"
    )

    val detailSections = listOf(
        DetailSection("Descripción y detalles", Icons.Default.Description),
        DetailSection("Talla", Icons.Default.Straighten),
        DetailSection("Categoría", Icons.Default.Category),
        DetailSection("Estado", Icons.Default.Star),
        DetailSection("Color", Icons.Default.Palette),
        DetailSection("Guía de Tallas", Icons.Default.Straighten, true),
        DetailSection("Materiales", Icons.Default.Texture, true),
        DetailSection("Estimación de envío", Icons.Default.LocalShipping, true),
        DetailSection("Políticas de Protección", Icons.Default.Security, true)
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
                shadowElevation = 2.dp
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
                            tint = Color(0xFF8B4513)
                        )
                    }

                    Text(
                        text = "Detalles",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    IconButton(onClick = onMoreClick) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Más opciones",
                            tint = Color(0xFF8B4513)
                        )
                    }
                }
            }
        }

        item {
            // Espacio para imagen del producto (placeholder)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.Gray.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Imagen del Producto",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        }

        item {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                Text(
                    text = productDetail.title,
                    fontSize = 20.sp, 
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        item {
            // Información del vendedor y precio
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Información del vendedor
                    Text(
                        text = "Colección René Risco",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = productDetail.sellerName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Text(
                        text = "Cerca: 9.6k - En perfecto estado",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Precio
                    Text(
                        text = productDetail.currentPrice,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Mascota mensaje
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Espacio para mascota FitMatch
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF8B4513).copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🐶",
                                fontSize = 20.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = productDetail.mascotMessage,
                            fontSize = 14.sp,
                            color = Color(0xFF8B4513),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        item {
            // Secciones de detalles
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    detailSections.forEachIndexed { index, section ->
                        DetailSectionItem(
                            section = section,
                            productDetail = productDetail
                        )

                        if (index < detailSections.size - 1) {
                            HorizontalDivider(
                                color = Color.Gray.copy(alpha = 0.2f),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(100.dp)) // Espacio para el botón flotante
        }
    }

    // Botón de compra flotante
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = Color.Transparent
        ) {
            Button(
                onClick = onBuyClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8B4513)
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    text = "Comprar",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun DetailSectionItem(
    section: DetailSection,
    productDetail: ProductDetail
) {
    val content = when (section.title) {
        "Descripción y detalles" -> ""
        "Talla" -> productDetail.size
        "Categoría" -> productDetail.category
        "Estado" -> productDetail.condition
        "Color" -> productDetail.color
        else -> ""
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = section.isExpandable) { /* Handle click */ }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono
        Icon(
            imageVector = section.icon,
            contentDescription = null,
            tint = Color(0xFF8B4513),
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Contenido
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = section.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            if (content.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = content,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        // Icono de expansión para secciones expandibles
        if (section.isExpandable) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Expandir",
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}