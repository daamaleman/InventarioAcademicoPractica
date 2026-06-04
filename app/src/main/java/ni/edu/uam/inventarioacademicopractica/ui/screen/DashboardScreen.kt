package ni.edu.uam.inventarioacademicopractica.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ni.edu.uam.inventarioacademicopractica.ui.component.BarChartComponent
import ni.edu.uam.inventarioacademicopractica.ui.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onVerEquiposClick: () -> Unit,
    onVerHistorialClick: () -> Unit,
    onRegistrarPrestamoClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Inventario Académico") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Resumen de Inventario",
                style = MaterialTheme.typography.headlineMedium
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                DashboardCard(
                    title = "Total",
                    value = uiState.totalEquipos.toString(),
                    modifier = Modifier.weight(1f)
                )
                DashboardCard(
                    title = "Disponibles",
                    value = uiState.equiposDisponibles.toString(),
                    modifier = Modifier.weight(1f)
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                DashboardCard(
                    title = "Prestados",
                    value = uiState.equiposPrestados.toString(),
                    modifier = Modifier.weight(1f)
                )
                DashboardCard(
                    title = "Categoría Dominante",
                    value = uiState.categoriaDominante,
                    modifier = Modifier.weight(1f)
                )
            }

            if (uiState.equiposPorCategoria.isNotEmpty()) {
                Text(
                    text = "Equipos por Categoría",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
                BarChartComponent(data = uiState.equiposPorCategoria)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onVerEquiposClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Lista de Equipos")
            }

            Button(
                onClick = onRegistrarPrestamoClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Nuevo Préstamo")
            }

            Button(
                onClick = onVerHistorialClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Historial de Préstamos")
            }
        }
    }
}

@Composable
fun DashboardCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(text = value, style = MaterialTheme.typography.headlineLarge)
        }
    }
}
