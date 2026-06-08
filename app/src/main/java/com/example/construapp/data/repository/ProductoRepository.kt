package com.example.construapp.data.repository

import com.example.construapp.domain.model.Producto
import kotlinx.coroutines.flow.Flow

interface ProductoRepository {
    fun observarProductos(): Flow<List<Producto>>
    fun observarPorId(id: Long): Flow<Producto?>
    suspend fun obtenerPorId(id: Long): Producto?
    suspend fun insertar(producto: Producto): Long
    suspend fun actualizar(producto: Producto)
    suspend fun eliminar(producto: Producto)
}
