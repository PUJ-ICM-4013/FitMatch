package com.example.fitmatch.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.FitMatchTheme



enum class OrderStatus { TODOS, PENDIENTE, PREPARANDO, ENVIADO }

data class OrderUi(
    val id: String,           // #MNX-24819
    val dateTime: String,     // "24 Ago, 09:15"
    val buyerName: String,    // "Laura P."
    val buyerHandle: String,  // "@laura_p"
    val itemsText: String,    // "2 artículos"
    val price: String,        // "$439.600"
    val status: OrderStatus
)

private fun sampleOrders() = listOf(
    OrderUi("#MNX-24819", "24 Ago, 09:15", "Laura P.",  "@laura_p",   "2 artículos", "$439.600", OrderStatus.PREPARANDO),
    OrderUi("#MNX-24818", "24 Ago, 08:27", "Carlos D.", "@carlos_d",  "1 artículo",  "$189.900", OrderStatus.PENDIENTE),
    OrderUi("#MNX-24810", "23 Ago, 17:02", "María G.",  "@maria_g",   "1 artículo",  "$129.900", OrderStatus.ENVIADO),
)

/* --------------------------------- Screen --------------------------------- */

@Composable
fun OrdersScreen(
    onMenuClick: () -> Unit = {},
    onBack: () -> Unit = {},
    onOrderClick: (OrderUi) -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme
    var query by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf(OrderStatus.TODOS) }
    val orders = remember { sampleOrders() }

    Scaffold(
        topBar = {
            // Top bar anclada con título centrado y overflow a la derecha
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(colors.background)
                    .padding(top = 12.dp, bottom = 8.dp)
            ) {
                // (Opcional) Back a la izquierda: descomenta si lo necesitas
                // IconButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
                //     Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                // }
                Text(
                    "Mis Pedidos",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp
                    )
                )
                IconButton(onClick = onMenuClick, modifier = Modifier.align(Alignment.CenterEnd)) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "Más")
                }
            }
        }
    ) { inner ->
        Column(
            Modifier
                .fillMaxSize()
                .background(colors.background)
                .padding(inner)
                .padding(horizontal = 16.dp)
        ) {
            // ───── Buscador
            SearchField(
                value = query,
                onValueChange = { query = it },
                placeholder = "Buscar pedido, comprador..."
            )

            Spacer(Modifier.height(12.dp))

            // ───── Filtros
            FilterRow(
                selected = selected,
                onSelected = { selected = it }
            )

            Spacer(Modifier.height(12.dp))

            // ───── Lista scrolleable
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Filtro mock básico
                val filtered = orders.filter { o ->
                    (selected == OrderStatus.TODOS || o.status == selected) &&
                            (query.isBlank() || o.id.contains(query, true) ||
                                    o.buyerName.contains(query, true) || o.buyerHandle.contains(query, true))
                }
                items(filtered, key = { it.id }) { order ->
                    OrderCard(order = order, onClick = { onOrderClick(order) })
                }
            }
        }
    }
}



@Composable
private fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    val colors = MaterialTheme.colorScheme
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        placeholder = { Text(placeholder) },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = colors.surfaceVariant.copy(alpha = 0.35f),
            focusedContainerColor = colors.surfaceVariant.copy(alpha = 0.35f),
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent
        )
    )
}

@Composable
private fun FilterRow(
    selected: OrderStatus,
    onSelected: (OrderStatus) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterPill(
            text = "Todos",
            selected = selected == OrderStatus.TODOS,
            onClick = { onSelected(OrderStatus.TODOS) },
            colors = colors
        )
        FilterPill(
            text = "Pendientes",
            selected = selected == OrderStatus.PENDIENTE,
            onClick = { onSelected(OrderStatus.PENDIENTE) },
            colors = colors
        )
        FilterPill(
            text = "Preparando",
            selected = selected == OrderStatus.PREPARANDO,
            onClick = { onSelected(OrderStatus.PREPARANDO) },
            colors = colors
        )
        FilterPill(
            text = "Enviado",
            selected = selected == OrderStatus.ENVIADO,
            onClick = { onSelected(OrderStatus.ENVIADO) },
            colors = colors
        )
    }
}

@Composable
private fun FilterPill(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    colors: ColorScheme
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = if (selected) colors.primary.copy(alpha = 0.16f) else colors.surface,
        border = if (selected)
            BorderStroke(1.dp, colors.primary)
        else
            BorderStroke(1.dp, colors.outline.copy(alpha = 0.8f))
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
            ),
            color = if (selected) colors.onSurface else colors.onSurface
        )
    }
}

@Composable
private fun OrderCard(
    order: OrderUi,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val statusChip = when (order.status) {
        OrderStatus.PENDIENTE   -> "Pendiente" to colors.secondary
        OrderStatus.PREPARANDO  -> "Preparando" to colors.primary
        OrderStatus.ENVIADO     -> "Enviado" to colors.tertiary
        OrderStatus.TODOS       -> "" to colors.outline
    }

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, colors.outline.copy(alpha = 0.35f))
    ) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {

            // Primera línea: ID + fecha/hora + precio a la derecha
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${order.id} · ${order.dateTime}",
                    style = MaterialTheme.typography.labelLarge.copy(color = colors.onSurface.copy(alpha = 0.8f))
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = order.price,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = colors.primary
                    )
                )
            }

            Spacer(Modifier.height(6.dp))

            // Nombre comprador
            Text(
                text = order.buyerName,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
                text = "${order.buyerHandle} — ${order.itemsText}",
                style = MaterialTheme.typography.bodyMedium.copy(color = colors.onSurface.copy(alpha = 0.7f))
            )

            Spacer(Modifier.height(12.dp))

            // Estado + "Ver detalle"
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                if (statusChip.first.isNotEmpty()) {
                    StatusPill(text = statusChip.first, color = statusChip.second)
                }
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Ver detalle →",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = colors.onSurface
                )
            }
        }
    }
}

@Composable
private fun StatusPill(text: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = color.copy(alpha = 0.16f),
        border = BorderStroke(1.dp, color)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}

/* --------------------------------- Preview -------------------------------- */

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_6")
@Composable
private fun OrdersScreenPreview() {
    FitMatchTheme {
        OrdersScreen()
    }
}
