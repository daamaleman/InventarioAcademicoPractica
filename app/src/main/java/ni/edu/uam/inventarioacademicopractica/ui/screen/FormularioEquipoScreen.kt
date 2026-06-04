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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioEquipoScreen(
    equipoId: Int = -1,
    onEquipoGuardado: () -> Unit,
    viewModel: EquipoViewModel
) {
    var nombre by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var numeroSerie by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val equipos by viewModel.equipos.collectAsState()
    val categorias = listOf("Laptop", "Monitor", "Impresora", "Router")
    
    LaunchedEffect(equipoId) {
        if (equipoId != -1) {
            val equipo = equipos.find { it.id == equipoId }
            equipo?.let {
                nombre = it.nombre
                categoria = it.categoria
                marca = it.marca
                numeroSerie = it.numeroSerie
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        if (equipoId == -1) "Nuevo Equipo" else "Editar Equipo",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onEquipoGuardado) {
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
            StyledTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = "Nombre del Equipo",
                icon = Icons.Default.Badge
            )

            // Categoría Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = categoria,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    leadingIcon = { Icon(Icons.Default.Category, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray)
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categorias.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                categoria = item
                                expanded = false
                            }
                        )
                    }
                }
            }

            StyledTextField(
                value = marca,
                onValueChange = { marca = it },
                label = "Marca / Fabricante",
                icon = Icons.Default.Business
            )

            StyledTextField(
                value = numeroSerie,
                onValueChange = { numeroSerie = it },
                label = "Número de Serie",
                icon = Icons.Default.Fingerprint
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (nombre.isNotBlank() && categoria.isNotBlank() && marca.isNotBlank() && numeroSerie.isNotBlank()) {
                        val equipo = Equipo(
                            id = if (equipoId == -1) 0 else equipoId,
                            nombre = nombre,
                            categoria = categoria,
                            marca = marca,
                            numeroSerie = numeroSerie,
                            disponible = if (equipoId == -1) true else equipos.find { it.id == equipoId }?.disponible ?: true
                        )
                        if (equipoId == -1) {
                            viewModel.insert(equipo)
                        } else {
                            viewModel.update(equipo)
                        }
                        onEquipoGuardado()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    if (equipoId == -1) "REGISTRAR EQUIPO" else "GUARDAR CAMBIOS",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}
