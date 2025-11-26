package com.example.fitmatch.wear.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.*
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitmatch.wear.model.WearProduct
import com.example.fitmatch.wear.presentation.viewmodel.WearViewModel

@Composable
fun WearMainScreen(viewModel: WearViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        when {
            uiState.isLoading -> {
                LoadingState()
            }
            uiState.currentProduct != null -> {
                ProductCardScreen(
                    product = uiState.currentProduct!!,
                    onLike = { viewModel.onLike() },
                    onPass = { viewModel.onPass() }
                )
            }
            uiState.errorMessage != null -> {
                ErrorState(
                    message = uiState.errorMessage!!,
                    onRetry = { viewModel.requestNextProduct() }
                )
            }
            else -> {
                StartScreen(onStart = { viewModel.requestNextProduct() })
            }
        }
    }
}

@Composable
private fun StartScreen(onStart: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "FitMatch",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = onStart,
                modifier = Modifier.size(60.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Iniciar",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
private fun ProductCardScreen(
    product: WearProduct,
    onLike: () -> Unit,
    onPass: () -> Unit
) {
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp)
    ) {
        item {
            // Imagen del producto (simulada)
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "📦",
                    fontSize = 48.sp
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            // 🏷Marca
            Text(
                text = product.brand,
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }

        item {
            // Título
            Text(
                text = product.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(4.dp)
            )
        }

        item {
            // Precio
            Text(
                text = "$${product.price}",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Green,
                modifier = Modifier.padding(4.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            // Botones (Like/Pass)
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // PASS
                Button(
                    onClick = onPass,
                    modifier = Modifier.size(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFD32F2F)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Pass",
                        modifier = Modifier.size(24.dp)
                    )
                }

                // LIKE
                Button(
                    onClick = onLike,
                    modifier = Modifier.size(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF1976D2)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Like",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorState(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Error",
                fontSize = 16.sp,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = message,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = onRetry,
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Text("Reintentar")
            }
        }
    }
}