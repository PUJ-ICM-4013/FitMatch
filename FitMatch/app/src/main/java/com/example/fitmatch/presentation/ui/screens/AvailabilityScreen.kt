package com.example.fitmatch.presentation.ui.screens

import android.R.attr.layout
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitmatch.presentation.ui.theme.FitMatchTheme

@Composable
fun AvailabilityScreen(
    onSave: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme

    // Estado mock
    var available by remember { mutableStateOf(true) }
    var quickSlot by remember { mutableStateOf<QuickSlot?>(null) }
    var startTime by remember { mutableStateOf("08:00 a. m.") }
    var endTime by remember { mutableStateOf("08:00 p. m.") }




    Scaffold(
        containerColor = colors.background
    ) { inner ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(inner)           // respeta la altura de la bottom bar global
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(12.dp))


            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(if (available) Color(0xFF2ECC71) else colors.outline)
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = if (available) "¡Estás Disponible!" else "No estás disponible",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
            }

            Divider(
                Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth(),
                color = colors.outline.copy(alpha = 0.3f)
            )


            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = colors.surface),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Estado de Disponibilidad",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(Modifier.weight(1f))
                    Switch(
                        checked = available,
                        onCheckedChange = { available = it },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = colors.primary,
                            checkedThumbColor = Color.White,
                            uncheckedThumbColor = Color.White
                        )
                    )
                }
            }

            Spacer(Modifier.height(16.dp))


            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = colors.primary.copy(alpha = 0.10f)),
                elevation = CardDefaults.cardElevation(0.dp),
                border = CardDefaults.outlinedCardBorder(true)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Ganancias de Hoy",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "$45.000 COP",
                        style = MaterialTheme.typography.bodyLarge.copy(color = colors.onSurface.copy(alpha = 0.8f))
                    )
                }
            }

            Spacer(Modifier.height(20.dp))


            Text(
                "Horarios Preferidos",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Horarios Rápidos",
                style = MaterialTheme.typography.bodySmall.copy(color = colors.onSurface.copy(alpha = 0.7f))
            )
            Spacer(Modifier.height(10.dp))


            FlowRowWrap(horizontalGap = 10.dp, verticalGap = 10.dp) {
                QuickSlot.entries.forEach { slot ->
                    SelectablePill(
                        text = slot.label,
                        selected = quickSlot == slot,
                        onClick = { quickSlot = if (quickSlot == slot) null else slot }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))


            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = colors.surface),
                elevation = CardDefaults.cardElevation(0.dp),
                border = CardDefaults.outlinedCardBorder(true)
            ) {
                Column(Modifier.fillMaxWidth().padding(16.dp)) {
                    Text(
                        "Horario Personalizado",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TimePill(text = startTime, onClick = { /* TODO */ })
                        Spacer(Modifier.width(8.dp))
                        Text("—", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.width(8.dp))
                        TimePill(text = endTime, onClick = { /* TODO */ })
                    }
                }
            }


            Spacer(Modifier.height(20.dp))
            Button(
                onClick = onSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    "Guardar Horarios",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                )
            }


            Spacer(Modifier.height(12.dp))
        }
    }
}



private enum class QuickSlot(val label: String) {
    MANANA("Mañana (6-12)"),
    TARDE("Tarde (12-18)"),
    NOCHE("Noche (18-00)"),
    TODO_DIA("Todo el día");
}

@Composable
private fun SelectablePill(text: String, selected: Boolean, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        color = if (selected) colors.primary.copy(alpha = 0.16f) else colors.surface,
        border = if (selected)
            BorderStroke(1.dp, colors.primary)
        else
            BorderStroke(1.dp, colors.outline.copy(alpha = 0.7f))
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
            )
        )
    }
}

@Composable
private fun TimePill(text: String, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = colors.primary.copy(alpha = 0.10f),
        border = BorderStroke(1.dp, colors.outline.copy(alpha = 0.6f))
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}


@Composable
private fun FlowRowWrap(
    horizontalGap: Dp = 8.dp,
    verticalGap: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    Layout(content = content) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }
        val maxWidth = constraints.maxWidth
        var rowWidth = 0
        var rowHeight = 0
        var x = 0
        var y = 0
        val positions = mutableListOf<Pair<Int, Int>>()

        placeables.forEach { p ->
            if (x + p.width > maxWidth) {
                x = 0
                y += rowHeight + verticalGap.roundToPx()
                rowHeight = 0
            }
            positions.add(x to y)
            x += p.width + horizontalGap.roundToPx()
            rowHeight = maxOf(rowHeight, p.height)
            rowWidth = maxOf(rowWidth, x)
        }

        val totalHeight = y + rowHeight
        layout(width = maxWidth, height = totalHeight) {
            placeables.forEachIndexed { index, p ->
                val (px, py) = positions[index]
                p.place(px, py)
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_6")
@Composable
private fun AvailabilityPreview() {
    FitMatchTheme {
        AvailabilityScreen()
    }
}
