package ni.edu.uam.inventarioacademicopractica.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ni.edu.uam.inventarioacademicopractica.data.repository.AuthRepository

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableSharedFlow<Boolean>()
    val loginResult = _loginResult.asSharedFlow()

    fun login(usuario: String, password: String) = viewModelScope.launch {
        val admin = repository.login(usuario, password)
        _loginResult.emit(admin != null)
    }
}
