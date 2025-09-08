package com.example.fitmatch.presentation.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class ChatMessage(
    val text: String,
    val isFromUser: Boolean,
    val timestamp: String = ""
)

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    contactName: String = "Helena Hills",
    contactSubtitle: String = "En línea",
    onBackClick: () -> Unit = {},
    onCallClick: () -> Unit = {},
    onMoreClick: () -> Unit = {}
) {
    var messageText by remember { mutableStateOf("") }
    var messages by remember {
        mutableStateOf(listOf(
            ChatMessage(
                "Jean Azul Levis Talla S\n¡Has hecho match! Puedes chatear con el vendedor para contactar tu compra.",
                false
            ),
            ChatMessage("Ah, sí?", false),
            ChatMessage("Qué chulo", false),
            ChatMessage("Cómo funciona?", false),
            ChatMessage(
                "Solo tienes que editar cualquier texto para escribir la conversación que quieras mostrar y borrar las burbujas que no quieras utilizar",
                true
            ),
            ChatMessage("Mmm", false),
            ChatMessage("Creo que lo entiendo", false),
            ChatMessage("De todas formas míraré el Centro de ayuda si tengo más preguntas", false)
        ))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5DC))
    ) {
        // Header del chat
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color(0xFF8B4513)
                    )
                }

                // Avatar y nombre
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(alpha = 0.3f))
                    ) {
                        // Aquí iría la imagen del contacto
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            tint = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = contactName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        Text(
                            text = contactSubtitle,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }

                // Botones de acción
                IconButton(onClick = onCallClick) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Llamar",
                        tint = Color(0xFF8B4513)
                    )
                }

                IconButton(onClick = onMoreClick) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Más opciones",
                        tint = Color(0xFF8B4513)
                    )
                }
            }
        }

        // Mensaje de match
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFF3CD))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Esta es la pantalla principal de chat",
                fontSize = 12.sp,
                color = Color(0xFF856404),
                fontWeight = FontWeight.Medium
            )
        }

        // Lista de mensajes
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(messages) { message ->
                MessageBubble(
                    message = message,
                    isFromUser = message.isFromUser
                )
            }
        }

        // Campo de entrada de mensaje
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    placeholder = { Text("Mensaje...") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF8B4513),
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                    ),
                    shape = RoundedCornerShape(25.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Botones de acción
                IconButton(
                    onClick = { /* Acción del micrófono */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Mensaje de voz",
                        tint = Color(0xFF8B4513)
                    )
                }

                IconButton(
                    onClick = { /* Acción de emoji */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEmotions,
                        contentDescription = "Emojis",
                        tint = Color(0xFF8B4513)
                    )
                }

                IconButton(
                    onClick = { /* Acción de imagen */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = "Enviar imagen",
                        tint = Color(0xFF8B4513)
                    )
                }

                IconButton(
                    onClick = { /* Acción de ubicación */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Enviar ubicación",
                        tint = Color(0xFF8B4513)
                    )
                }
            }
        }
    }
}

@Composable
private fun MessageBubble(
    message: ChatMessage,
    isFromUser: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isFromUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isFromUser) {
            // Avatar del contacto
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.3f))
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
        }

        // Burbuja del mensaje
        Card(
            modifier = Modifier.widthIn(max = 280.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isFromUser)
                    Color(0xFF8B4513) else Color.White
            ),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isFromUser) 16.dp else 4.dp,
                bottomEnd = if (isFromUser) 4.dp else 16.dp
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(12.dp),
                color = if (isFromUser) Color.White else Color.Black,
                fontSize = 14.sp
            )
        }

        if (isFromUser) {
            Spacer(modifier = Modifier.width(8.dp))

            // Avatar del usuario
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF8B4513).copy(alpha = 0.3f))
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    tint = Color(0xFF8B4513)
                )
            }
        }
    }
}