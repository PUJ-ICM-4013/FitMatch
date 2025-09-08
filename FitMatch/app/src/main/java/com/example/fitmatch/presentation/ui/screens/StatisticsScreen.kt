package com.example.fitmatch.presentation.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TimeFilter(
    val id: String,
    val name: String,
    val isSelected: Boolean = false
)

data class StatMetric(
    val title: String,
    val value: String,
    val change: String,
    val isPositive: Boolean,
    val subtitle: String = ""
)

data class CategoryStats(
    val name: String,
    val percentage: Float,
    val value: String,
    val color: Color
)

data class ProductStats(
    val name: String,
    val sales: String,
    val revenue: String
)

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    onBackClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    onExportClick: () -> Unit = {}
) {
    var selectedFilter by remember { mutableStateOf("ultimos_7d") }

    val timeFilters = listOf(
        TimeFilter("ultimos_7d", "Últimos 7d", selectedFilter == "ultimos_7d"),
        TimeFilter("ultimos_30d", "Últimos 30d", selectedFilter == "ultimos_30d"),
        TimeFilter("90_dias", "90 días", selectedFilter == "90_dias"),
        TimeFilter("personal", "Personal", selectedFilter == "personal")
    )

    val metrics = listOf(
        StatMetric("Ingresos", "$6.8M", "10% 7d", true),
        StatMetric("Pedidos", "54", "8% 7d", true),
        StatMetric("Ticket promedio", "$126k", "2.1 items", true),
        StatMetric("Conversión", "3.4%", "8% 7d", true),
        StatMetric("Seguidores", "2,184", "+85", true),
        StatMetric("Visitantes", "7,930", "8%", true)
    )

    val categoryStats = listOf(
        CategoryStats("Sustentable", 0.88f, "88", Color(0xFF8B4513)),
        CategoryStats("Minimal", 0.76f, "76", Color(0xFF8B4513)),
        CategoryStats("Vintage", 0.89f, "140", Color(0xFF8B4513))
    )

    val topProducts = listOf(
        ProductStats("Blazer Premium", "94", "$15.9M"),
        ProductStats("Wide Leg", "62", "$8.1M"),
        ProductStats("Cropped Top", "48", "$4.6M")
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
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.Black
                        )
                    }

                    Text(
                        text = "Estadísticas",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    IconButton(onClick = onMoreClick) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Más opciones",
                            tint = Color.Black
                        )
                    }
                }
            }
        }

        item {
            // Filtros de tiempo
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
            ) {
                items(timeFilters) { filter ->
                    TimeFilterChip(
                        filter = filter,
                        onClick = { selectedFilter = filter.id }
                    )
                }
            }
        }

        item {
            // Métricas principales (grid 2x3)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Primera fila
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricCard(
                        metric = metrics[0], // Ingresos
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        metric = metrics[1], // Pedidos
                        modifier = Modifier.weight(1f)
                    )
                }

                // Segunda fila
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricCard(
                        metric = metrics[2], // Ticket promedio
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        metric = metrics[3], // Conversión
                        modifier = Modifier.weight(1f)
                    )
                }

                // Tercera fila
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricCard(
                        metric = metrics[4], // Seguidores
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        metric = metrics[5], // Visitantes
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        item {
            // Sección Ventas con gráfica
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Ventas",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "últimos 30 días",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Gráfica simple
                    SalesChart(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    )
                }
            }
        }

        item {
            // Top categorías
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Top categorías",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    categoryStats.forEach { category ->
                        CategoryProgressBar(
                            category = category,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }
                }
            }
        }

        item {
            // Top productos
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Top productos",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        TextButton(onClick = onExportClick) {
                            Text(
                                text = "Exportar CSV",
                                fontSize = 12.sp,
                                color = Color(0xFF8B4513)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Headers
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Productos",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
                            modifier = Modifier.weight(2f)
                        )
                        Text(
                            text = "Ventas",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Ingresos",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
                            textAlign = TextAlign.End,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    topProducts.forEach { product ->
                        ProductStatsRow(
                            product = product,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun TimeFilterChip(
    filter: TimeFilter,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.height(32.dp),
        color = if (filter.isSelected) Color(0xFF8B4513) else Color.White,
        shape = RoundedCornerShape(16.dp),
        border = if (!filter.isSelected) {
            androidx.compose.foundation.BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f))
        } else null
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = filter.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = if (filter.isSelected) Color.White else Color.Black
            )
        }
    }
}

@Composable
private fun MetricCard(
    metric: StatMetric,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = metric.title,
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = metric.value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (metric.isPositive) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = if (metric.isPositive) Color(0xFF4CAF50) else Color(0xFFF44336),
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = metric.change,
                    fontSize = 10.sp,
                    color = if (metric.isPositive) Color(0xFF4CAF50) else Color(0xFFF44336)
                )
            }

            if (metric.subtitle.isNotEmpty()) {
                Text(
                    text = metric.subtitle,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
private fun SalesChart(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
    ) {
        val width = size.width
        val height = size.height
        val points = listOf(
            Offset(0f, height * 0.8f),
            Offset(width * 0.1f, height * 0.7f),
            Offset(width * 0.25f, height * 0.6f),
            Offset(width * 0.4f, height * 0.5f),
            Offset(width * 0.55f, height * 0.45f),
            Offset(width * 0.7f, height * 0.3f),
            Offset(width * 0.85f, height * 0.25f),
            Offset(width, height * 0.2f)
        )

        val path = Path()
        path.moveTo(points.first().x, points.first().y)
        for (i in 1 until points.size) {
            path.lineTo(points[i].x, points[i].y)
        }

        drawPath(
            path = path,
            color = Color(0xFF8B4513),
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun CategoryProgressBar(
    category: CategoryStats,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.name,
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                text = category.value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(Color.Gray.copy(alpha = 0.2f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(category.percentage)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(3.dp))
                    .background(category.color)
            )
        }
    }
}

@Composable
private fun ProductStatsRow(
    product: ProductStats,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = product.name,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.weight(2f)
        )

        Text(
            text = product.sales,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = product.revenue,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}