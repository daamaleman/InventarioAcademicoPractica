package ni.edu.uam.inventarioacademicopractica.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Equipo
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Prestamo
import ni.edu.uam.inventarioacademicopractica.data.repository.EquipoRepository
import ni.edu.uam.inventarioacademicopractica.data.repository.PrestamoRepository

class PrestamoViewModel(
    private val repository: PrestamoRepository,
    private val equipoRepository: EquipoRepository
) : ViewModel() {

    // Flujo de estado que expone el historial de préstamos
    val prestamos: StateFlow<List<Prestamo>> = repository.allPrestamos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun registrarPrestamo(equipo: Equipo, solicitante: String, fecha: String) = viewModelScope.launch {
        // Crear el registro de préstamo
        val prestamo = Prestamo(
            equipoId = equipo.id,
            solicitante = solicitante,
            fechaPrestamo = fecha,
            fechaDevolucion = null
        )
        repository.insert(prestamo)
        
        // Actualizar la disponibilidad del equipo
        equipoRepository.update(equipo.copy(disponible = false))
    }

    fun registrarDevolucion(prestamo: Prestamo, fecha: String) = viewModelScope.launch {
        // Actualizar fecha de devolución en el préstamo
        repository.update(prestamo.copy(fechaDevolucion = fecha))
        
        // Volver a poner el equipo como disponible
        val equipo = equipoRepository.getEquipoById(prestamo.equipoId)
        equipo?.let {
            equipoRepository.update(it.copy(disponible = true))
        }
    }
}
