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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.FitMatchTheme

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
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val isExpandable: Boolean = false,
    val content: String = ""
)

// ⚠️ Los previews van al final envueltos en FitMatchTheme
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    onBackClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    onBuyClick: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme

    val productDetail = ProductDetail(
        title = "Pantalón Blanco",
        originalPrice = "$380,000",
        currentPrice = "$300,000",
        isOnSale = true,
        brand = "Levi's",
        size = "XS / 30 US / 42 EU",
        category = "Jeans",
        condition = "En perfecto estado",
        color = "Blanco",
        mascotMessage = "Tito cree que esta prenda es perfecta para tu estilo ✨",
        sellerName = "Planeta Vintage"
    )

    val detailSections = listOf(
        DetailSection("Descripción y detalles", Icons.Default.Description),
        DetailSection("Talla", Icons.Default.Straighten),
        DetailSection("Categoría", Icons.Default.Category),
        DetailSection("Estado", Icons.Default.Star),
        DetailSection("Color", Icons.Default.Palette),
        DetailSection("Guía de Tallas", Icons.Default.Straighten, isExpandable = true),
        DetailSection("Materiales", Icons.Default.Texture, isExpandable = true),
        DetailSection("Estimación de envío", Icons.Default.LocalShipping, isExpandable = true),
        DetailSection("Políticas de Protección", Icons.Default.Security, isExpandable = true)
    )

    // Nota estudiante: si después metemos galería con pager, mover a Scaffold y usar TopAppBar/BottomAppBar del tema
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        item {
            // Header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = colors.surface,
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
                            tint = colors.onSurface
                        )
                    }

                    Text(
                        text = "Detalles",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.onSurface
                    )

                    Row {
                        // botoncito compartir (útil para viralidad)
                        IconButton(onClick = onMoreClick /* TODO: share */) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Compartir",
                                tint = colors.onSurface
                            )
                        }
                        IconButton(onClick = onMoreClick) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Más opciones",
                                tint = colors.onSurface
                            )
                        }
                    }
                }
            }
        }

        item {
            // Placeholder imagen del producto
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(colors.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Imagen del Producto",
                    color = colors.onSurfaceVariant,
                    fontSize = 16.sp
                )
                // Nota estudiante: aquí va un pager con zoom y miniaturas abajo (UX top)
            }
        }

        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    text = productDetail.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onSurface
                )
                // Nota estudiante: añadir chipcitos de estado (Nuevo/Usado) y categoría, usando secondaryContainer
            }
        }

        item {
            // Vendedor + precio + mensajito de Tito
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = colors.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Colección René Risco",
                        fontSize = 12.sp,
                        color = colors.onSurfaceVariant
                    )
                    Text(
                        text = productDetail.sellerName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.onSurface
                    )
                    Text(
                        text = "Cerca: 9.6k • ${productDetail.condition}",
                        fontSize = 12.sp,
                        color = colors.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Precios (mostrar tachado si hay oferta)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = productDetail.currentPrice,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.onSurface
                        )
                        if (productDetail.isOnSale) {
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = productDetail.originalPrice,
                                fontSize = 14.sp,
                                color = colors.onSurfaceVariant,
                                textDecoration = TextDecoration.LineThrough
                            )
                            // Nota estudiante: poner badge -% con error/onError si quieres más punch
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Mensaje de Tito
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(colors.primary.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🐶", fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = productDetail.mascotMessage,
                            fontSize = 14.sp,
                            color = colors.primary,
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
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                colors = CardDefaults.cardColors(containerColor = colors.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    detailSections.forEachIndexed { index, section ->
                        DetailSectionItem(
                            section = section,
                            productDetail = productDetail
                        )
                        if (index < detailSections.size - 1) {
                            HorizontalDivider(
                                color = colors.outlineVariant,
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(100.dp)) } // espacio para el botón flotante
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
            color = colors.surface.copy(alpha = 0f) // transparente
        ) {
            Button(
                onClick = onBuyClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    text = "Comprar",
                    color = colors.onPrimary,
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
    val colors = MaterialTheme.colorScheme

    val content = when (section.title) {
        "Descripción y detalles" -> "Prenda original, sin manchas ni roturas. Corte recto, tiro medio."
        "Talla" -> productDetail.size
        "Categoría" -> productDetail.category
        "Estado" -> productDetail.condition
        "Color" -> productDetail.color
        else -> ""
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = section.isExpandable) { /* TODO: expandir/cerrar */ }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = section.icon,
            contentDescription = null,
            tint = colors.primary, // iconito de marca
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = section.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = colors.onSurface
            )

            if (content.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = content,
                    fontSize = 12.sp,
                    color = colors.onSurfaceVariant
                )
            }
        }

        if (section.isExpandable) {
            // botoncito para desplegar (luego cambiar por animación + contenido)
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Expandir",
                tint = colors.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

// ==========================
// Previews con tu tema (dynamicColor = false)
// ==========================
@Preview(showBackground = true, name = "Product Detail – Light (Brand)")
@Composable
private fun ProductDetailPreviewLight() {
    FitMatchTheme(darkTheme = false, dynamicColor = false) {
        ProductDetailScreen()
    }
}

@Preview(showBackground = true, name = "Product Detail – Dark (Brand)")
@Composable
private fun ProductDetailPreviewDark() {
    FitMatchTheme(darkTheme = true, dynamicColor = false) {
        ProductDetailScreen()
    }
}
