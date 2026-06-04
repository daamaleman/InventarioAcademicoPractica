package ni.edu.uam.inventarioacademicopractica.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Equipo
import ni.edu.uam.inventarioacademicopractica.data.repository.EquipoRepository

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class EquipoViewModel(private val repository: EquipoRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // Flujo de estado que expone la lista de equipos filtrada por búsqueda y categoría
    val equipos: StateFlow<List<Equipo>> = combine(_searchQuery, _selectedCategory) { query, category ->
        Pair(query, category)
    }
        .debounce(300)
        .flatMapLatest { (query, category) ->
            repository.searchAndFilterEquipos(query, category)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val equiposDisponibles: StateFlow<List<Equipo>> = repository.equiposDisponibles
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }

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
