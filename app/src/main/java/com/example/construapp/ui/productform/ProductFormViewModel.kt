package com.example.construapp.ui.productform

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.construapp.data.repository.ProductoRepository
import com.example.construapp.domain.model.Producto
import com.example.construapp.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    private val repository: ProductoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val productId: Long? = savedStateHandle.get<Long>(Screen.ProductEdit.ARG_PRODUCT_ID)
    val esEdicion: Boolean get() = productId != null && productId != 0L

    private val _uiState = MutableStateFlow(ProductFormUiState())
    val uiState: StateFlow<ProductFormUiState> = _uiState.asStateFlow()

    init {
        if (esEdicion) {
            viewModelScope.launch {
                repository.obtenerPorId(productId!!)?.let { producto ->
                    _uiState.update {
                        it.copy(
                            nombre = producto.nombre,
                            descripcion = producto.descripcion,
                            precio = producto.precio.toString(),
                            cantidad = producto.cantidad.toString()
                        )
                    }
                }
            }
        }
    }

    fun onNombreChange(v: String) = _uiState.update { it.copy(nombre = v, errorNombre = null) }
    fun onDescripcionChange(v: String) = _uiState.update { it.copy(descripcion = v) }
    fun onPrecioChange(v: String) = _uiState.update { it.copy(precio = v, errorPrecio = null) }
    fun onCantidadChange(v: String) = _uiState.update { it.copy(cantidad = v, errorCantidad = null) }

    fun guardar(onDone: () -> Unit) {
        val estado = _uiState.value
        val nombre = estado.nombre.trim()
        val precio = estado.precio.trim().toDoubleOrNull()
        val cantidad = estado.cantidad.trim().toIntOrNull()

        val errorNombre = if (nombre.isEmpty()) "El nombre es obligatorio" else null
        val errorPrecio = if (precio == null || precio <= 0.0) {
            "El precio debe ser un número positivo"
        } else null
        val errorCantidad = if (cantidad == null || cantidad < 0) {
            "La cantidad debe ser un número entero mayor o igual a 0"
        } else null

        if (errorNombre != null || errorPrecio != null || errorCantidad != null) {
            _uiState.update {
                it.copy(
                    errorNombre = errorNombre,
                    errorPrecio = errorPrecio,
                    errorCantidad = errorCantidad
                )
            }
            return
        }

        val producto = Producto(
            id = if (esEdicion) productId!! else 0L,
            nombre = nombre,
            descripcion = estado.descripcion.trim(),
            precio = precio!!,
            cantidad = cantidad!!
        )

        viewModelScope.launch {
            if (esEdicion) {
                repository.actualizar(producto)
            } else {
                repository.insertar(producto)
            }
            onDone()
        }
    }
}
