package com.example.fitmatch.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.FitMatchTheme

/* ---------------------------- Model / Mock data ---------------------------- */

data class CartItem(
    val id: String,
    val title: String,
    val shop: String,
    val price: Int,         // en pesos
    val size: String,
    val color: String,
)

private fun mockCart(): List<CartItem> = listOf(
    CartItem("1", "Blazer Premium en Lino", "@ateliernova", 189_900, "M", "Negro"),
    CartItem("2", "Pantalón Wide Leg", "@lunaurban", 129_900, "S", "Beige"),
)

/* --------------------------------- Screen --------------------------------- */

@Composable
fun CartScreen(
    onBack: () -> Unit = {},
    onMenu: () -> Unit = {},
    onCheckout: () -> Unit = {},
) {
    val colors = MaterialTheme.colorScheme
    var items by remember { mutableStateOf(mockCart()) }
    var qty by remember { mutableStateOf(mutableMapOf("1" to 1, "2" to 2)) }

    // Cálculos
    val subtotal = items.sumOf { it.price * (qty[it.id] ?: 1) }
    val envio = 9_900
    val descuento = 20_000
    val total = subtotal + envio - descuento


    Scaffold(containerColor = colors.background) { inner ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 16.dp)
        ) {
            /* ----------------------------- Top App Bar ---------------------------- */
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 8.dp)
            ) {
                IconButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                }
                Text(
                    "Cesta",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    ),
                    modifier = Modifier.align(Alignment.Center),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
                IconButton(onClick = onMenu, modifier = Modifier.align(Alignment.CenterEnd)) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "Menú")
                }
            }


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),                 // mitad superior
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(items, key = { it.id }) { item ->
                    CartItemCard(
                        item = item,
                        quantity = qty[item.id] ?: 1,
                        onMinus = {
                            val current = (qty[item.id] ?: 1)
                            if (current > 1) qty[item.id] = current - 1
                        },
                        onPlus = {
                            val current = (qty[item.id] ?: 1)
                            qty[item.id] = current + 1
                        }
                    )
                }
            }


            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),                // mitad inferior
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CouponCard(onApply = { /* TODO: lógica cupon */ })

                SummaryCard(
                    subtotal = subtotal,
                    envio = envio,
                    descuento = descuento,
                    total = total
                )


                Button(
                    onClick = onCheckout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("TRAMITAR PEDIDO", fontWeight = FontWeight.SemiBold)
                }

                // Pequeño respiro para no pegarlo a la barra inferior
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}



@Composable
private fun CartItemCard(
    item: CartItem,
    quantity: Int,
    onMinus: () -> Unit,
    onPlus: () -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.primary.copy(alpha = 0.08f) // lila tenue
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = CardDefaults.outlinedCardBorder(true)
    ) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "$${formatPrice(item.price)}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
            Text(
                text = item.shop,
                style = MaterialTheme.typography.bodyMedium.copy(color = colors.onSurface.copy(alpha = 0.7f))
            )
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Pill(text = item.size)
                Pill(text = item.color)
                Spacer(Modifier.weight(1f))
                QuantityControl(qty = quantity, onMinus = onMinus, onPlus = onPlus)
            }
        }
    }
}

@Composable
private fun Pill(text: String) {
    val colors = MaterialTheme.colorScheme
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = colors.surface,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        border = CardDefaults.outlinedCardBorder(true)
    ) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun QuantityControl(qty: Int, onMinus: () -> Unit, onPlus: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SmallSquareButton(text = "-") { onMinus() }
        Surface(
            modifier = Modifier.widthIn(min = 40.dp),
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
            shape = RoundedCornerShape(8.dp),
            color = colors.surface,
            border = CardDefaults.outlinedCardBorder(true)
        ) {
            Text(
                "$qty",
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge
            )
        }
        SmallSquareButton(text = "+") { onPlus() }
    }
}

@Composable
private fun SmallSquareButton(text: String, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        color = colors.surface,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        border = CardDefaults.outlinedCardBorder(true)
    ) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun CouponCard(
    onApply: (String) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    var code by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.surface)
            .padding(12.dp)   // padding general
    ) {
        // 🔹 Título "Cupón"
        Text(
            text = "Cupón",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            color = colors.onSurface
        )
        Spacer(Modifier.height(8.dp))

        // 🔹 Input + botón
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = code,
                onValueChange = { code = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Código") },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = { onApply(code) },
                modifier = Modifier.height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Aplicar", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}


@Composable
private fun SummaryCard(
    subtotal: Int,
    envio: Int,
    descuento: Int,
    total: Int
) {
    val colors = MaterialTheme.colorScheme
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.primary.copy(alpha = 0.08f)
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Text("Resumen", style = MaterialTheme.typography.titleMedium, color = colors.onSurface)
            Spacer(Modifier.height(12.dp))
            RowLine("Subtotal", "$${formatPrice(subtotal)}")
            RowLine("Envío (estimado)", "$${formatPrice(envio)}")
            RowLine("Descuento", "-$${formatPrice(descuento)}")
            Divider(Modifier.padding(vertical = 8.dp), color = colors.outline.copy(alpha = 0.3f))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Total",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    "$${formatPrice(total)}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = colors.primary
                    )
                )
            }
        }
    }
}

@Composable
private fun RowLine(label: String, value: String) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}

private fun formatPrice(value: Int): String =
    "%,d".format(value).replace(',', '.')

/* --------------------------------- Preview -------------------------------- */

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_6")
@Composable
private fun CartScreenPreview() {
    FitMatchTheme {
        CartScreen()
    }
}