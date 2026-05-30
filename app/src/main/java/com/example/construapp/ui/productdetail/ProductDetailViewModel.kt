package com.example.construapp.ui.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.construapp.data.repository.ProductoRepository
import com.example.construapp.domain.model.Producto
import com.example.construapp.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ProductDetailUiState(
    val producto: Producto? = null,
    val cargando: Boolean = false
)

// Mock para visualizar el diseño antes del CRUD real.
private fun productoMock(id: Long): Producto = Producto(
    id = id,
    nombre = "Tornillos 1/4\"",
    descripcion = "Tornillos para madera de 1/4\" — caja con 100u",
    precio = 8900.0,
    cantidad = 3
)

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    @Suppress("unused") private val repository: ProductoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val productId: Long = savedStateHandle.get<Long>(Screen.ProductDetail.ARG_PRODUCT_ID) ?: 0L

    private val _uiState = MutableStateFlow(
        ProductDetailUiState(producto = productoMock(productId))
    )
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun eliminar(onDone: () -> Unit) {
        // Implementación en unidad posterior.
        onDone()
    }
}
