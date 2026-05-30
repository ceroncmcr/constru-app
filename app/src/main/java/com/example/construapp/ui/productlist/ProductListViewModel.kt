package com.example.construapp.ui.productlist

import androidx.lifecycle.ViewModel
import com.example.construapp.data.repository.ProductoRepository
import com.example.construapp.domain.model.Producto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ProductListUiState(
    val productos: List<Producto> = productosMock,
    val busqueda: String = "",
    val cargando: Boolean = false
)

// Datos de ejemplo para visualizar el diseño antes del CRUD real (Unidad 4).
private val productosMock = listOf(
    Producto(id = 1, nombre = "Martillo de uña 16oz", descripcion = "Martillo carpintero", precio = 12500.0, cantidad = 14),
    Producto(id = 2, nombre = "Tornillos 1/4\" (caja x100)", descripcion = "Tornillos para madera", precio = 8900.0, cantidad = 3),
    Producto(id = 3, nombre = "Cinta métrica 5m", descripcion = "Cinta métrica metálica", precio = 9200.0, cantidad = 8),
    Producto(id = 4, nombre = "Taladro inalámbrico 18V", descripcion = "Taladro a batería", precio = 189900.0, cantidad = 0),
    Producto(id = 5, nombre = "Cemento gris 25kg", descripcion = "Cemento Portland tipo 1", precio = 18700.0, cantidad = 22)
)

@HiltViewModel
class ProductListViewModel @Inject constructor(
    @Suppress("unused") private val repository: ProductoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    fun onBusquedaChange(valor: String) {
        _uiState.value = _uiState.value.copy(busqueda = valor)
    }

    // En unidades posteriores: collect repository.observarProductos() y exponerlo aquí.
}
