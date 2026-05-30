package com.example.construapp.ui.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class LoginUiState(
    val usuario: String = "",
    val errorUsuario: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUsuarioChange(nuevo: String) {
        _uiState.update { it.copy(usuario = nuevo, errorUsuario = null) }
    }

    fun onLoginClick(onSuccess: () -> Unit) {
        val usuario = _uiState.value.usuario.trim()
        if (usuario.isEmpty()) {
            _uiState.update { it.copy(errorUsuario = "El usuario es obligatorio") }
            return
        }
        onSuccess()
    }
}
