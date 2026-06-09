package com.example.construapp.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.construapp.data.repository.ProductoRepository
import com.example.construapp.domain.model.EstadoStock
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class DashboardUiState(
    val total: Int = 0,
    val stockBajo: Int = 0,
    val sinStock: Int = 0
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    repository: ProductoRepository
) : ViewModel() {

    val uiState: StateFlow<DashboardUiState> =
        repository.observarProductos().map { productos ->
            DashboardUiState(
                total = productos.size,
                stockBajo = productos.count { it.estadoStock == EstadoStock.STOCK_BAJO },
                sinStock = productos.count { it.estadoStock == EstadoStock.SIN_STOCK }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DashboardUiState()
        )
}
