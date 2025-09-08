package com.example.fitmatch.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.ui.tooling.preview.Preview

data class NotificationItem(
    val id: String,
    val userName: String,
    val message: String,
    val timeAgo: String,
    val profileImageUrl: String? = null,
    val type: NotificationType,
    val productImageUrl: String? = null
)

enum class NotificationType {
    MATCH, ORDER, MESSAGE, CONFIRMATION
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Todo", "Matches", "Pedidos", "Mensajes")

    val notifications = listOf(
        NotificationItem(
            "1",
            "fashionista_ana",
            "¡Match! Le gustó chaqueta de cuero negra",
            "1d",
            type = NotificationType.MATCH
        ),
        NotificationItem(
            "2",
            "carlos_vintage",
            "Tu pedido está en camino - Jeans vintage azul",
            "1d",
            type = NotificationType.ORDER
        ),
        NotificationItem(
            "3",
            "sofia_style",
            "Te envió un mensaje sobre vestido floral",
            "2d",
            type = NotificationType.MESSAGE
        ),
        NotificationItem(
            "4",
            "delivery_fast",
            "Pedido entregado - Zapatillas Nike Air Max",
            "3d",
            type = NotificationType.ORDER
        ),
        NotificationItem(
            "5",
            "shadowlynx",
            "Confirmó la compra de tu camisa vintage",
            "4d",
            type = NotificationType.CONFIRMATION
        ),
        NotificationItem(
            "6",
            "minimalist_wardrobe",
            "Te envió un mensaje sobre Blazer negro",
            "5d",
            type = NotificationType.MESSAGE
        ),
        NotificationItem(
            "7",
            "lunavoyager",
            "¡Match! Le gustó Zapatillas Nike",
            "5d",
            type = NotificationType.MATCH
        )
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
            shadowElevation = 2.dp
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Notificaciones",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B4513)
                    )
                }

                // Tabs
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.White,
                    contentColor = Color(0xFF8B4513),
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = Color(0xFF8B4513)
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 14.sp,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            selectedContentColor = Color(0xFF8B4513),
                            unselectedContentColor = Color.Gray
                        )
                    }
                }
            }
        }

        // Lista de notificaciones
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(notifications) { notification ->
                NotificationCard(
                    notification = notification,
                    onClick = { /* Handle notification click */ }
                )
            }
        }
    }
}

@Composable
private fun NotificationCard(
    notification: NotificationItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar del usuario
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                if (notification.profileImageUrl != null) {
                    AsyncImage(
                        model = notification.profileImageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Contenido de la notificación
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = notification.userName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = notification.timeAgo,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = notification.message,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 2
                )
            }

            // Imagen del producto (si aplica)
            if (notification.productImageUrl != null) {
                Spacer(modifier = Modifier.width(12.dp))

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = notification.productImageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // Indicador de tipo de notificación
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(
                        when (notification.type) {
                            NotificationType.MATCH -> Color(0xFF8B4513)
                            NotificationType.ORDER -> Color(0xFF4CAF50)
                            NotificationType.MESSAGE -> Color(0xFF2196F3)
                            NotificationType.CONFIRMATION -> Color(0xFFFF9800)
                        }
                    )
            )
        }
    }
}