package ni.edu.uam.inventarioacademicopractica.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Prestamo
import ni.edu.uam.inventarioacademicopractica.data.repository.PrestamoRepository

class PrestamoViewModel(private val repository: PrestamoRepository) : ViewModel() {

    // Flujo de estado que expone el historial de préstamos
    val prestamos: StateFlow<List<Prestamo>> = repository.allPrestamos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun registrarPrestamo(prestamo: Prestamo) = viewModelScope.launch {
        repository.insert(prestamo)
    }

    fun registrarDevolucion(prestamo: Prestamo) = viewModelScope.launch {
        repository.update(prestamo)
    }
}
