package ni.edu.uam.inventarioacademicopractica.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Equipo
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

    val equipos by viewModel.equipos.collectAsState()
    
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
            TopAppBar(title = { Text(if (equipoId == -1) "Registrar Equipo" else "Editar Equipo") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = marca,
                onValueChange = { marca = it },
                label = { Text("Marca") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = numeroSerie,
                onValueChange = { numeroSerie = it },
                label = { Text("Número de Serie") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (equipoId == -1) "Guardar Equipo" else "Actualizar Equipo")
            }
        }
    }
}
