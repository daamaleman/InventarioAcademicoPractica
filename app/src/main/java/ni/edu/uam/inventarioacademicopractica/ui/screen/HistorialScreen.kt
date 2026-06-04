package ni.edu.uam.inventarioacademicopractica.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Equipo
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Prestamo
import ni.edu.uam.inventarioacademicopractica.ui.viewmodel.EquipoViewModel
import ni.edu.uam.inventarioacademicopractica.ui.viewmodel.PrestamoViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialScreen(
    prestamoViewModel: PrestamoViewModel,
    equipoViewModel: EquipoViewModel
) {
    val prestamos by prestamoViewModel.prestamos.collectAsState()
    val equipos by equipoViewModel.equipos.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Historial de Préstamos") })
        }
    ) { padding ->
        if (prestamos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("No hay registros de préstamos")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(prestamos) { prestamo ->
                    val equipo = equipos.find { it.id == prestamo.equipoId }
                    PrestamoItem(
                        prestamo = prestamo,
                        equipoNombre = equipo?.nombre ?: "ID: ${prestamo.equipoId}",
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
fun PrestamoItem(
    prestamo: Prestamo,
    equipoNombre: String,
    onDevolverClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Equipo: $equipoNombre", style = MaterialTheme.typography.titleLarge)
            Text(text = "Solicitante: ${prestamo.solicitante}", style = MaterialTheme.typography.titleMedium)
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(text = "Fecha Préstamo: ${prestamo.fechaPrestamo}", style = MaterialTheme.typography.bodyMedium)
            
            if (prestamo.fechaDevolucion != null) {
                Text(
                    text = "Fecha Devolución: ${prestamo.fechaDevolucion}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4CAF50)
                )
            } else {
                Text(
                    text = "Estado: Pendiente de devolución",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFF44336)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onDevolverClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar Devolución")
                }
            }
        }
    }
}
