package com.example.fitmatch.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.FitMatchTheme

@Composable
fun WelcomeScreen(
    onCreateAccount: () -> Unit = {},
    onContinueWithEmail: () -> Unit = {},
    onContinueWithGoogle: () -> Unit = {},
    onContinueWithApple: () -> Unit = {}
) {

    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background) // Fondo del tema
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Título de bienvenida
            Text(
                text = "¡Bienvenido/a a FitMatch!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground, // del tema
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Personaje "Tito" (placeholder)
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(
                        colors.primary.copy(alpha = 0.12f), // tinte del color principal
                        RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "👋\nHola,\nsoy Tito",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = colors.primary // texto con color principal del tema
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botones de acción
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Botón Crear Cuenta
                Button(
                    onClick = onCreateAccount,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary,
                        contentColor = colors.onPrimary
                    ),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text(
                        text = "Crear Cuenta",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Botón Continuar con Email
                Button(
                    onClick = onContinueWithEmail,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary,
                        contentColor = colors.onPrimary
                    ),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Continuar con Email o Teléfono",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Botón Continuar con Google
                Button(
                    onClick = onContinueWithGoogle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary,
                        contentColor = colors.onPrimary
                    ),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "G",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Continuar con Google",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Botón Continuar con Apple
                Button(
                    onClick = onContinueWithApple,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary,
                        contentColor = colors.onPrimary
                    ),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // (Ícono Apple si luego lo agregas)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Continuar con Apple",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Términos y condiciones
            Text(
                text = "Al hacer clic en continuar, aceptas nuestros Términos de Servicio y nuestra Política de Privacidad",
                fontSize = 12.sp,
                color = colors.onSurfaceVariant, // gris del tema
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}


@Preview(showBackground = true, showSystemUi = true, name = "welcome – Light")
@Composable
private fun CreateProductPreviewLight() {
    FitMatchTheme(darkTheme = false, dynamicColor = false) {
        WelcomeScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "welcome – Dark")
@Composable
private fun CreateProductPreviewDark() {
    FitMatchTheme(darkTheme = true, dynamicColor = false) {
        WelcomeScreen()
    }
}