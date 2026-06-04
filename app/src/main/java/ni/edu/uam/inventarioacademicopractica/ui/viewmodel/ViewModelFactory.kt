package ni.edu.uam.inventarioacademicopractica.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ni.edu.uam.inventarioacademicopractica.data.repository.AuthRepository
import ni.edu.uam.inventarioacademicopractica.data.repository.EquipoRepository
import ni.edu.uam.inventarioacademicopractica.data.repository.PrestamoRepository

class ViewModelFactory(
    private val equipoRepository: EquipoRepository,
    private val prestamoRepository: PrestamoRepository,
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EquipoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EquipoViewModel(equipoRepository) as T
        }
        if (modelClass.isAssignableFrom(PrestamoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PrestamoViewModel(prestamoRepository, equipoRepository) as T
        }
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(equipoRepository, prestamoRepository) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
