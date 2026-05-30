package com.example.construapp.ui.productform

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.construapp.data.repository.ProductoRepository
import com.example.construapp.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ProductFormUiState(
    val nombre: String = "",
    val descripcion: String = "",
    val precio: String = "",
    val cantidad: String = "",
    val errorNombre: String? = null,
    val errorPrecio: String? = null,
    val errorCantidad: String? = null
)

@HiltViewModel
class ProductFormViewModel @Inject constructor(
    @Suppress("unused") private val repository: ProductoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val productId: Long? = savedStateHandle.get<Long>(Screen.ProductEdit.ARG_PRODUCT_ID)
    val esEdicion: Boolean get() = productId != null && productId != 0L

    private val _uiState = MutableStateFlow(ProductFormUiState())
    val uiState: StateFlow<ProductFormUiState> = _uiState.asStateFlow()

    fun onNombreChange(v: String) = _uiState.update { it.copy(nombre = v, errorNombre = null) }
    fun onDescripcionChange(v: String) = _uiState.update { it.copy(descripcion = v) }
    fun onPrecioChange(v: String) = _uiState.update { it.copy(precio = v, errorPrecio = null) }
    fun onCantidadChange(v: String) = _uiState.update { it.copy(cantidad = v, errorCantidad = null) }

    fun guardar(onDone: () -> Unit) {
        // Validaciones e integración con repositorio: unidad posterior.
        onDone()
    }
}
