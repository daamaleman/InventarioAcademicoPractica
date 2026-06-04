package ni.edu.uam.inventarioacademicopractica.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.widget.Toast
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Equipo
import ni.edu.uam.inventarioacademicopractica.ui.viewmodel.EquipoViewModel
import ni.edu.uam.inventarioacademicopractica.util.CsvExporter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaEquiposScreen(
    viewModel: EquipoViewModel,
    onAgregarClick: () -> Unit,
    onEditarClick: (Int) -> Unit
) {
    val equipos by viewModel.equipos.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    var equipoAEliminar by remember { mutableStateOf<Equipo?>(null) }
    val context = LocalContext.current
    
    val categorias = listOf("Laptop", "Monitor", "Impresora", "Router")

    if (equipoAEliminar != null) {
        AlertDialog(
            onDismissRequest = { equipoAEliminar = null },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar este equipo? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        equipoAEliminar?.let { viewModel.delete(it) }
                        equipoAEliminar = null
                    }
                ) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { equipoAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inventario de Equipos") },
                actions = {
                    IconButton(onClick = {
                        val path = CsvExporter.exportEquiposToCsv(context, equipos)
                        if (path != null) {
                            Toast.makeText(context, "Exportado a: $path", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "Error al exportar", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Exportar CSV")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregarClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Equipo")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Barra de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Buscar por nombre o serie") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )

            // Filtros de categoría
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Opción para quitar filtro
                FilterChip(
                    selected = selectedCategory == "",
                    onClick = { viewModel.onCategorySelected("") },
                    label = { Text("Todos") }
                )
                
                categorias.forEach { categoria ->
                    FilterChip(
                        selected = selectedCategory == categoria,
                        onClick = { viewModel.onCategorySelected(categoria) },
                        label = { Text(categoria) }
                    )
                }
            }

            if (equipos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(if (searchQuery.isEmpty()) "No hay equipos registrados" else "No se encontraron resultados")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(equipos) { equipo ->
                        EquipoItem(
                            equipo = equipo,
                            onEdit = { onEditarClick(equipo.id) },
                            onDelete = { equipoAEliminar = equipo }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EquipoItem(
    equipo: Equipo,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = equipo.nombre, style = MaterialTheme.typography.titleLarge)
                Text(text = "Marca: ${equipo.marca}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "S/N: ${equipo.numeroSerie}", style = MaterialTheme.typography.bodyMedium)
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Surface(
                    color = if (equipo.disponible) Color(0xFF4CAF50) else Color(0xFFF44336),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = if (equipo.disponible) "Disponible" else "Prestado",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar Equipo")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar Equipo", tint = Color.Red)
                }
            }
        }
    }
}
