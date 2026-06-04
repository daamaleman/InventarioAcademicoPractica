package ni.edu.uam.inventarioacademicopractica.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Prestamo
import ni.edu.uam.inventarioacademicopractica.ui.viewmodel.EquipoViewModel
import ni.edu.uam.inventarioacademicopractica.ui.viewmodel.PrestamoViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialScreen(
    prestamoViewModel: PrestamoViewModel,
    equipoViewModel: EquipoViewModel,
    onBack: () -> Unit
) {
    val prestamos by prestamoViewModel.prestamos.collectAsState()
    val equipos by equipoViewModel.equipos.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->
        if (prestamos.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("No hay historial de préstamos", color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(prestamos) { prestamo ->
                    val equipo = equipos.find { it.id == prestamo.equipoId }
                    HistoryItem(
                        prestamo = prestamo,
                        equipoNombre = equipo?.nombre ?: "Equipo (ID: ${prestamo.equipoId})",
                        onDevolverClick = {
                            val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                            prestamoViewModel.registrarDevolucion(prestamo, fechaActual)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryItem(
    prestamo: Prestamo,
    equipoNombre: String,
    onDevolverClick: () -> Unit
) {
    val isDevuelto = prestamo.fechaDevolucion != null

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            if (isDevuelto) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isDevuelto) Icons.Default.Check else Icons.Default.PendingActions,
                        contentDescription = null,
                        tint = if (isDevuelto) Color(0xFF2E7D32) else Color(0xFFC62828),
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = equipoNombre, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text(text = "Solicitante: ${prestamo.solicitante}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), thickness = 0.5.dp, color = Color.LightGray)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("PRÉSTAMO", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(prestamo.fechaPrestamo, style = MaterialTheme.typography.bodyMedium)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("DEVOLUCIÓN", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(prestamo.fechaDevolucion ?: "Pendiente", style = MaterialTheme.typography.bodyMedium, color = if (!isDevuelto) Color(0xFFC62828) else MaterialTheme.colorScheme.onSurface)
                }
            }

            if (!isDevuelto) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onDevolverClick,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(Icons.Default.KeyboardReturn, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Registrar Devolución")
                }
            }
        }
    }
}
