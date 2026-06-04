package ni.edu.uam.inventarioacademicopractica.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ni.edu.uam.inventarioacademicopractica.data.repository.EquipoRepository
import ni.edu.uam.inventarioacademicopractica.data.repository.PrestamoRepository

data class DashboardState(
    val totalEquipos: Int = 0,
    val equiposDisponibles: Int = 0,
    val equiposPrestados: Int = 0
)

class DashboardViewModel(
    equipoRepository: EquipoRepository,
    private val prestamoRepository: PrestamoRepository
) : ViewModel() {

    // Estado del dashboard calculado a partir del flujo de equipos
    val uiState: StateFlow<DashboardState> = equipoRepository.allEquipos
        .map { equipos ->
            DashboardState(
                totalEquipos = equipos.size,
                equiposDisponibles = equipos.count { it.disponible },
                equiposPrestados = equipos.count { !it.disponible }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DashboardState()
        )
}
