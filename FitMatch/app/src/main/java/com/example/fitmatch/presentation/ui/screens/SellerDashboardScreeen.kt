package com.example.fitmatch.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DashboardMetric(
    val title: String,
    val value: String,
    val subtitle: String,
    val icon: ImageVector,
    val iconColor: Color = Color(0xFF8B4513),
    val changeIndicator: String = "",
    val isPositive: Boolean = true
)

data class QuickAction(
    val title: String,
    val icon: ImageVector,
    val backgroundColor: Color = Color.White,
    val iconColor: Color = Color(0xFF8B4513)
)

@Preview(showBackground = true)
@Composable
fun SellerDashboardScreen(
    onActionClick: (String) -> Unit = {}
) {
    val greeting = "¡Hola, María!"
    val date = "Lunes, 1 Septiembre 2025"

    // Métricas principales
    val mainMetrics = listOf(
        DashboardMetric(
            title = "Ganancias esta semana",
            value = "$200.000 COP",
            subtitle = "",
            icon = Icons.Default.AttachMoney,
            iconColor = Color(0xFF4CAF50)
        ),
        DashboardMetric(
            title = "Calificación",
            value = "4.8★",
            subtitle = "",
            icon = Icons.Default.Star,
            iconColor = Color(0xFFFFC107)
        )
    )

    // Indicadores principales
    val keyIndicators = listOf(
        DashboardMetric(
            title = "Ganancias del mes",
            value = "$500.000 COP",
            subtitle = "+18% vs mes anterior",
            icon = Icons.AutoMirrored.Filled.TrendingUp,
            changeIndicator = "+18%",
            isPositive = true
        ),
        DashboardMetric(
            title = "Tasa de conversión",
            value = "12.5%",
            subtitle = "+2.3% esta semana",
            icon = Icons.AutoMirrored.Filled.ShowChart,
            changeIndicator = "+2.3%",
            isPositive = true
        ),
        DashboardMetric(
            title = "Productos activos",
            value = "15",
            subtitle = "+8 nuevos esta semana",
            icon = Icons.Default.Inventory,
            changeIndicator = "+8",
            isPositive = true
        ),
        DashboardMetric(
            title = "Stock total bajo",
            value = "20",
            subtitle = "Requiere atención",
            icon = Icons.Default.Warning,
            iconColor = Color(0xFFFF9800),
            changeIndicator = "⚠️",
            isPositive = false
        )
    )

    // Acciones rápidas (modificado según tu solicitud)
    val quickActions = listOf(
        QuickAction("Agregar\nProducto", Icons.Default.Add),
        QuickAction("Mostrar\nProductos", Icons.AutoMirrored.Filled.ViewList),
        QuickAction("Mis\nPedidos", Icons.Default.LocalShipping),
        QuickAction("Comentarios", Icons.Default.Star),
        QuickAction("Envíos", Icons.Default.LocalShipping),
        QuickAction("Comentarios", Icons.Default.RateReview)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Saludo y fecha
            Column {
                Text(
                    text = greeting,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B4513)
                )
                Text(
                    text = date,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        item {
            // Métricas principales en fila
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                mainMetrics.forEach { metric ->
                    MainMetricCard(
                        metric = metric,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        item {
            // Alerta de stock
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF3E0)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Alerta",
                        tint = Color(0xFFFF9800),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Stock Bajo detectado",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFE65100)
                        )
                        Text(
                            text = "5 Productos está por debajo de 5 unidades",
                            fontSize = 12.sp,
                            color = Color(0xFFE65100).copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        item {
            // Título indicadores principales
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Assessment,
                    contentDescription = "Indicadores",
                    tint = Color(0xFF8B4513),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Indicadores Principales",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        item {
            // Grid de indicadores 2x2
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Primera fila
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IndicatorCard(
                        metric = keyIndicators[0],
                        modifier = Modifier.weight(1f)
                    )
                    IndicatorCard(
                        metric = keyIndicators[1],
                        modifier = Modifier.weight(1f)
                    )
                }

                // Segunda fila
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IndicatorCard(
                        metric = keyIndicators[2],
                        modifier = Modifier.weight(1f)
                    )
                    IndicatorCard(
                        metric = keyIndicators[3],
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        item {
            // Título acciones rápidas
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.FlashOn,
                    contentDescription = "Acciones",
                    tint = Color(0xFF8B4513),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Acciones Rápidas",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        item {
            // Grid de acciones rápidas 2x3
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Primera fila
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionCard(
                        action = quickActions[0],
                        modifier = Modifier.weight(1f),
                        onClick = { onActionClick("add_product") }
                    )
                    QuickActionCard(
                        action = quickActions[1],
                        modifier = Modifier.weight(1f),
                        onClick = { onActionClick("show_products") }
                    )
                }

                // Segunda fila
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionCard(
                        action = quickActions[2],
                        modifier = Modifier.weight(1f),
                        onClick = { onActionClick("my_orders") }
                    )
                    QuickActionCard(
                        action = quickActions[3],
                        modifier = Modifier.weight(1f),
                        onClick = { onActionClick("comments") }
                    )
                }

                // Tercera fila
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionCard(
                        action = quickActions[4],
                        modifier = Modifier.weight(1f),
                        onClick = { onActionClick("shipments") }
                    )
                    QuickActionCard(
                        action = quickActions[5],
                        modifier = Modifier.weight(1f),
                        onClick = { onActionClick("reviews") }
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun MainMetricCard(
    metric: DashboardMetric,
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
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = metric.icon,
                contentDescription = null,
                tint = metric.iconColor,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = metric.value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = metric.title,
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun IndicatorCard(
    metric: DashboardMetric,
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
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = metric.icon,
                    contentDescription = null,
                    tint = metric.iconColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = metric.value,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = metric.title,
                fontSize = 12.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )

            if (metric.subtitle.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = metric.subtitle,
                    fontSize = 10.sp,
                    color = if (metric.isPositive) Color(0xFF4CAF50) else Color(0xFFFF9800)
                )
            }
        }
    }
}

@Composable
private fun QuickActionCard(
    action: QuickAction,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = action.backgroundColor
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = action.title,
                tint = action.iconColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = action.title,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 12.sp
            )
        }
    }
}