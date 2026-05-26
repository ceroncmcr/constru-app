package com.example.construapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.construapp.domain.model.Producto

@Entity(tableName = "productos")
data class ProductoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val cantidad: Int
) {
    fun toDomain(): Producto = Producto(
        id = id,
        nombre = nombre,
        descripcion = descripcion,
        precio = precio,
        cantidad = cantidad
    )

    companion object {
        fun fromDomain(p: Producto): ProductoEntity = ProductoEntity(
            id = p.id,
            nombre = p.nombre,
            descripcion = p.descripcion,
            precio = p.precio,
            cantidad = p.cantidad
        )
    }
}
