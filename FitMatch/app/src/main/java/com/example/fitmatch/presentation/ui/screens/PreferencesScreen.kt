package com.example.fitmatch.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

// Enum para los diferentes tipos de pantalla
enum class PreferenceType {
    STYLES, COLORS, SIZES, CATEGORIES
}

// Data class para las opciones
data class PreferenceOption(
    val id: String,
    val title: String,
    val icon: ImageVector, // En una app real usarías imágenes
    val backgroundColor: Color = Color.White
)

@Preview(showBackground = true)
@Composable
fun PreferencesScreen(
    preferenceType: PreferenceType = PreferenceType.STYLES,
    onBackClick: () -> Unit = {},
    onSkipClick: () -> Unit = {},
    onContinueClick: (Set<String>) -> Unit = {}
) {
    val (title, subtitle, options) = when (preferenceType) {
        PreferenceType.STYLES -> Triple(
            "Cuéntanos qué te gusta",
            "Esto nos ayudará a recomendarte lo que más te interesa",
            getStyleOptions()
        )
        PreferenceType.COLORS -> Triple(
            "Cuéntanos qué te gusta",
            "Esto nos ayudará a recomendarte lo que más te interesa",
            getColorOptions()
        )
        PreferenceType.SIZES -> Triple(
            "Cuéntanos qué te gusta",
            "Esto nos ayudará a encontrarte las mejores ofertas",
            getSizeOptions()
        )
        PreferenceType.CATEGORIES -> Triple(
            "Cuéntanos qué te gusta",
            "Esto nos ayudará a encontrarte lo que más te interesa",
            getCategoryOptions()
        )
    }

    var selectedOptions by remember { mutableStateOf(setOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5DC))
            .padding(24.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
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

            TextButton(onClick = onSkipClick) {
                Text(
                    text = "Saltar",
                    color = Color(0xFF8B4513),
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Título y subtítulo
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B4513),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = subtitle,
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Grid de opciones
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(options) { option ->
                PreferenceOptionCard(
                    option = option,
                    isSelected = selectedOptions.contains(option.id),
                    onClick = {
                        selectedOptions = if (selectedOptions.contains(option.id)) {
                            selectedOptions - option.id
                        } else {
                            selectedOptions + option.id
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Continuar
        Button(
            onClick = { onContinueClick(selectedOptions) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8B4513)
            ),
            shape = RoundedCornerShape(25.dp),
            enabled = selectedOptions.isNotEmpty()
        ) {
            Text(
                text = "Continuar",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun PreferenceOptionCard(
    option: PreferenceOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() }
            .then(
                if (isSelected) {
                    Modifier.border(
                        3.dp,
                        Color(0xFF8B4513),
                        RoundedCornerShape(16.dp)
                    )
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                Color(0xFF8B4513)
            else
                option.backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 6.dp else 2.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = option.icon,
                contentDescription = option.title,
                tint = if (isSelected) Color.White else Color(0xFF8B4513),
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = option.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Color.White else Color.Black,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

// Opciones para estilos
private fun getStyleOptions(): List<PreferenceOption> {
    return listOf(
        PreferenceOption("streetwear", "Streetwear", Icons.Default.Style),
        PreferenceOption("vintage", "Vintage", Icons.Default.History),
        PreferenceOption("deportivo", "Deportivo", Icons.Default.SportsBaseball),
        PreferenceOption("casual", "Casual", Icons.Default.Person),
        PreferenceOption("elegante", "Elegante", Icons.Default.Star),
        PreferenceOption("minimalista", "Minimalista", Icons.Default.Remove)
    )
}

// Opciones para colores
private fun getColorOptions(): List<PreferenceOption> {
    return listOf(
        PreferenceOption("negro", "Negro", Icons.Default.Circle, Color(0xFF2E2E2E)),
        PreferenceOption("blanco", "Blanco", Icons.Default.Circle, Color(0xFFF5F5F5)),
        PreferenceOption("azul", "Azul", Icons.Default.Circle, Color(0xFF4A90E2)),
        PreferenceOption("rojo", "Rojo", Icons.Default.Circle, Color(0xFFE74C3C)),
        PreferenceOption("verde", "Verde", Icons.Default.Circle, Color(0xFF27AE60)),
        PreferenceOption("amarillo", "Amarillo", Icons.Default.Circle, Color(0xFFF39C12)),
        PreferenceOption("marron", "Marrón", Icons.Default.Circle, Color(0xFF8B4513)),
        PreferenceOption("cualquiera", "Cualquiera", Icons.Default.Palette)
    )
}

// Opciones para tallas
private fun getSizeOptions(): List<PreferenceOption> {
    return listOf(
        PreferenceOption("xxs", "XXS", Icons.Default.Straighten),
        PreferenceOption("xs", "XS", Icons.Default.Straighten),
        PreferenceOption("s", "S", Icons.Default.Straighten),
        PreferenceOption("m", "M", Icons.Default.Straighten),
        PreferenceOption("l", "L", Icons.Default.Straighten),
        PreferenceOption("xl", "XL", Icons.Default.Straighten),
        PreferenceOption("xxl", "XXL", Icons.Default.Straighten),
        PreferenceOption("xxxl", "XXXL", Icons.Default.Straighten)
    )
}

// Opciones para categorías
private fun getCategoryOptions(): List<PreferenceOption> {
    return listOf(
        PreferenceOption("cazadoras_abrigos", "Cazadoras y \nabrigos", Icons.Default.DryCleaning),
        PreferenceOption("sudaderas", "Sudaderas", Icons.Default.Checkroom),
        PreferenceOption("jerseis_chaquetas", "Jerseis y\nChaquetas", Icons.Default.Dry),
        PreferenceOption("camisas_camisetas", "Camisas y\ncamisetas", Icons.Default.ShoppingBag),
        PreferenceOption("vestidos", "Vestidos", Icons.Default.Woman),
        PreferenceOption("jeans", "Jeans", Icons.Default.HeartBroken),
        PreferenceOption("pantalones", "Pantalones", Icons.Default.InsertEmoticon),
        PreferenceOption("ropa_deportiva", "Ropa\ndeportiva", Icons.Default.FitnessCenter)
    )
}