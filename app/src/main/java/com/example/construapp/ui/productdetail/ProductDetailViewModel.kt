package com.example.construapp.ui.productdetail

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

data class ProductDetailUiState(
    val producto: Producto? = null,
    val cargando: Boolean = true
)

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val productId: Long = savedStateHandle.get<Long>(Screen.ProductDetail.ARG_PRODUCT_ID) ?: 0L

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    init {
        // Observación reactiva: tras editar y volver, el detalle se actualiza solo (HU06 CA2).
        viewModelScope.launch {
            repository.observarPorId(productId).collect { producto ->
                _uiState.update { it.copy(producto = producto, cargando = false) }
            }
        }
    }

    fun eliminar(onDone: () -> Unit) {
        val producto = _uiState.value.producto ?: return
        viewModelScope.launch {
            repository.eliminar(producto)
            onDone()
        }
    }
}
