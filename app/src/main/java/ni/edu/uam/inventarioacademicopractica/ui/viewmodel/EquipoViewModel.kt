package ni.edu.uam.inventarioacademicopractica.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Equipo
import ni.edu.uam.inventarioacademicopractica.data.repository.EquipoRepository

class EquipoViewModel(private val repository: EquipoRepository) : ViewModel() {

    // Flujo de estado que expone la lista de todos los equipos
    val equipos: StateFlow<List<Equipo>> = repository.allEquipos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insert(equipo: Equipo) = viewModelScope.launch {
        repository.insert(equipo)
    }

    fun update(equipo: Equipo) = viewModelScope.launch {
        repository.update(equipo)
    }

    fun delete(equipo: Equipo) = viewModelScope.launch {
        repository.delete(equipo)
    }
}
