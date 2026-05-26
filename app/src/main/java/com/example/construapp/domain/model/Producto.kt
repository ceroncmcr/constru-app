package com.example.construapp.domain.model

data class Producto(
    val id: Long = 0L,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val cantidad: Int
) {
    val estadoStock: EstadoStock
        get() = when {
            cantidad == 0 -> EstadoStock.SIN_STOCK
            cantidad <= UMBRAL_STOCK_BAJO -> EstadoStock.STOCK_BAJO
            else -> EstadoStock.NORMAL
        }

    companion object {
        const val UMBRAL_STOCK_BAJO = 5
    }
}

enum class EstadoStock { NORMAL, STOCK_BAJO, SIN_STOCK }
