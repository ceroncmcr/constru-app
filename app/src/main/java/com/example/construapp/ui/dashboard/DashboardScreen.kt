package com.example.construapp.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.construapp.ui.components.ConstruAppTopBar
import com.example.construapp.ui.theme.DangerRed
import com.example.construapp.ui.theme.SuccessGreen
import com.example.construapp.ui.theme.WarningAmber

@Composable
fun DashboardScreen(
    onVerProductos: () -> Unit,
    onAgregarProducto: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { ConstruAppTopBar(title = "ConstruApp") }
    ) { padding ->
        DashboardContent(
            paddingValues = padding,
            state = state,
            onVerProductos = onVerProductos,
            onAgregarProducto = onAgregarProducto
        )
    }
}

@Composable
private fun DashboardContent(
    paddingValues: PaddingValues,
    state: DashboardUiState,
    onVerProductos: () -> Unit,
    onAgregarProducto: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Hola 👋",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "¿Qué deseas hacer hoy?",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(20.dp))

        OpcionCard(
            titulo = "Ver productos",
            subtitulo = "Consulta y gestiona el inventario actual",
            icono = Icons.Default.Inventory2,
            iconBg = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
            iconTint = MaterialTheme.colorScheme.primary,
            onClick = onVerProductos
        )
        Spacer(Modifier.height(14.dp))

        OpcionCard(
            titulo = "Agregar producto",
            subtitulo = "Registra un nuevo producto en el inventario",
            icono = Icons.Default.Add,
            iconBg = SuccessGreen.copy(alpha = 0.15f),
            iconTint = SuccessGreen,
            onClick = onAgregarProducto
        )

        Spacer(Modifier.height(24.dp))
        StatsBanner(state = state)
    }
}

@Composable
private fun OpcionCard(
    titulo: String,
    subtitulo: String,
    icono: ImageVector,
    iconBg: Color,
    iconTint: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitulo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun StatsBanner(state: DashboardUiState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Resumen de inventario",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(state.total.toString(), "Productos", MaterialTheme.colorScheme.primary)
                StatItem(state.stockBajo.toString(), "Stock bajo", WarningAmber)
                StatItem(state.sinStock.toString(), "Sin stock", DangerRed)
            }
        }
    }
}

@Composable
private fun StatItem(numero: String, label: String, color: Color) {
    Column {
        Text(
            text = numero,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
