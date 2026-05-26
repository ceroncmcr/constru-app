package com.example.construapp.data.repository

import com.example.construapp.data.local.ProductoDao
import com.example.construapp.data.local.ProductoEntity
import com.example.construapp.domain.model.Producto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductoRepositoryImpl @Inject constructor(
    private val dao: ProductoDao
) : ProductoRepository {

    override fun observarProductos(): Flow<List<Producto>> =
        dao.observarTodos().map { lista -> lista.map { it.toDomain() } }

    override suspend fun obtenerPorId(id: Long): Producto? =
        dao.obtenerPorId(id)?.toDomain()

    override suspend fun insertar(producto: Producto): Long =
        dao.insertar(ProductoEntity.fromDomain(producto))

    override suspend fun actualizar(producto: Producto) =
        dao.actualizar(ProductoEntity.fromDomain(producto))

    override suspend fun eliminar(producto: Producto) =
        dao.eliminar(ProductoEntity.fromDomain(producto))
}
