package ni.edu.uam.inventarioacademicopractica.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Equipo
import ni.edu.uam.inventarioacademicopractica.ui.component.StyledTextField
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
            TopAppBar(
                title = { Text("Registrar Préstamo", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = onPrestamoRegistrado) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Detalles del Préstamo",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            // Selección de Equipo con estilo mejorado
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = equipoSeleccionado?.nombre ?: "Seleccione un equipo",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Equipo Disponible") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    leadingIcon = { Icon(Icons.Default.Devices, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray)
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    if (equiposDisponibles.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("No hay equipos disponibles") },
                            onClick = { expanded = false }
                        )
                    } else {
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
            }

            StyledTextField(
                value = solicitante,
                onValueChange = { solicitante = it },
                label = "Nombre del Solicitante",
                icon = Icons.Default.Person
            )

            StyledTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = "Fecha (AAAA-MM-DD)",
                icon = Icons.Default.Event
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val equipo = equipoSeleccionado
                    if (equipo != null && solicitante.isNotBlank() && fecha.isNotBlank()) {
                        prestamoViewModel.registrarPrestamo(equipo, solicitante, fecha)
                        onPrestamoRegistrado()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = equipoSeleccionado != null && solicitante.isNotBlank(),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text("CONFIRMAR PRÉSTAMO", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            }
        }
    }
}
