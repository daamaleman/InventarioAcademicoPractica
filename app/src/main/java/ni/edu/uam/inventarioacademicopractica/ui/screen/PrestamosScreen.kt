package ni.edu.uam.inventarioacademicopractica.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Equipo
import ni.edu.uam.inventarioacademicopractica.ui.viewmodel.EquipoViewModel
import ni.edu.uam.inventarioacademicopractica.ui.viewmodel.PrestamoViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrestamosScreen(
    equipoViewModel: EquipoViewModel,
    prestamoViewModel: PrestamoViewModel,
    onPrestamoRegistrado: () -> Unit
) {
    val equiposDisponibles by equipoViewModel.equiposDisponibles.collectAsState()
    
    var solicitante by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())) }
    var equipoSeleccionado by remember { mutableStateOf<Equipo?>(null) }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Registrar Préstamo") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Selección de Equipo
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = equipoSeleccionado?.nombre ?: "Seleccione un equipo",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Equipo") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    equiposDisponibles.forEach { equipo ->
                        DropdownMenuItem(
                            text = { Text("${equipo.nombre} (${equipo.numeroSerie})") },
                            onClick = {
                                equipoSeleccionado = equipo
                                expanded = false
                            }
                        )
                    }
                }
            }

            // Solicitante
            OutlinedTextField(
                value = solicitante,
                onValueChange = { solicitante = it },
                label = { Text("Nombre del Solicitante") },
                modifier = Modifier.fillMaxWidth()
            )

            // Fecha (Simple text field por ahora, o un DatePicker)
            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha (AAAA-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val equipo = equipoSeleccionado
                    if (equipo != null && solicitante.isNotBlank() && fecha.isNotBlank()) {
                        prestamoViewModel.registrarPrestamo(equipo, solicitante, fecha)
                        onPrestamoRegistrado()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = equipoSeleccionado != null && solicitante.isNotBlank()
            ) {
                Text("Confirmar Préstamo")
            }
        }
    }
}
