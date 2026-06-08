package com.example.construapp.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.construapp.data.repository.ProductoRepository
import com.example.construapp.domain.model.Producto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class ProductListUiState(
    val productos: List<Producto> = emptyList(),
    val busqueda: String = "",
    val cargando: Boolean = true
)

@HiltViewModel
class ProductListViewModel @Inject constructor(
    repository: ProductoRepository
) : ViewModel() {

    private val busqueda = MutableStateFlow("")

    val uiState: StateFlow<ProductListUiState> =
        combine(repository.observarProductos(), busqueda) { productos, filtro ->
            val filtrados = if (filtro.isBlank()) {
                productos
            } else {
                productos.filter { it.nombre.contains(filtro, ignoreCase = true) }
            }
            ProductListUiState(
                productos = filtrados,
                busqueda = filtro,
                cargando = false
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProductListUiState()
        )

    fun onBusquedaChange(valor: String) {
        busqueda.value = valor
    }
}
