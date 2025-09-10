package com.example.fitmatch.presentation.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
//import androidx.compose.material3.assistChipColors
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.FitMatchTheme



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CLienteDashboardScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onBookmarkClick: () -> Unit = {},
    onLikeClick: () -> Unit = {},
) {
    val colors = MaterialTheme.colorScheme

    var incognito by remember { mutableStateOf(true) }


    val pages = listOf(
        Color(0xFFBDBDBD), // gris claro
        Color(0xFF9E9E9E),
        Color(0xFF757575),
        Color(0xFFBDBDBD)
    )
    val pagerState = rememberPagerState(pageCount = { pages.size })

    Column(
        modifier
            .fillMaxSize()
            .background(colors.background)
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 35.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.List, contentDescription = "Menú")
            }
            Spacer(Modifier.weight(1f))
            FilterChip(
                selected = incognito,
                onClick = { incognito = !incognito },
                label = { Text("Modo incógnito") }
            )
        }


        Box(
            Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .height(600.dp)
        ) {
            // Carrusel
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.matchParentSize()
            ) { page ->
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(pages[page])
                )
            }


            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 12.dp, end = 12.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(pages.size) { index ->
                    val selected = pagerState.currentPage == index
                    Box(
                        Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(
                                if (selected) colors.primary
                                else colors.primary.copy(alpha = 0.25f)
                            )
                    )
                }
            }


            Column(
                Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(
                        // Deja una leve superposición para que el texto siempre se lea
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.3f to colors.surface.copy(alpha = 0.65f),
                            1f to colors.surface.copy(alpha = 0.90f)
                        )
                    )
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 24.dp)
            ) {
                Text(
                    text = "Pantalón Baggy Negro",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
                        color = colors.onSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "$300.000",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = colors.onSurface
                    )
                )
                Spacer(Modifier.height(8.dp))


                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TagChip("XS")
                    TagChip("Negro")
                    TagChip("StreetWear")
                }

                Spacer(Modifier.height(10.dp))

                // Distancia / ubicación
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Ubicación",
                        tint = colors.onSurface.copy(alpha = 0.9f),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "A 1,5 KM de tu ubicación",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = colors.onSurface
                        )
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))


        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BigCircleAction(
                icon = Icons.Outlined.BookmarkBorder,
                contentDesc = "Guardar",
                onClick = onBookmarkClick
            )
            BigCircleAction(
                icon = Icons.Default.Favorite,
                contentDesc = "Me gusta",
                onClick = onLikeClick
            )
        }

        // (Opcional) Empuja contenido para simular bottom bar propia si la pantalla lo requiere
        Spacer(Modifier.height(16.dp))
    }
}



@Composable
private fun TagChip(text: String) {
    val colors = MaterialTheme.colorScheme
    AssistChip(
        onClick = {  },
        label = { Text(text) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = colors.secondary.copy(alpha = 0.18f),
            labelColor = colors.onSurface
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colors.secondary
        )
    )
}

@Composable
private fun BigCircleAction(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDesc: String,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    Surface(
        modifier = Modifier.size(72.dp),
        shape = CircleShape,
        color = colors.surface,
        tonalElevation = 2.dp,
        shadowElevation = 4.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, colors.outline.copy(alpha = 0.25f))
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = contentDesc, tint = colors.onSurface, modifier = Modifier.size(36.dp))
        }
    }
}
@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "id:pixel_6"
)
@Composable
fun ClienteDashboardScreenPreview() {
    FitMatchTheme {
        CLienteDashboardScreen()
    }
}
