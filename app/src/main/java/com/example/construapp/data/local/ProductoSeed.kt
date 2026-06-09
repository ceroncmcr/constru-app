package com.example.construapp.data.local

/**
 * Datos semilla que se insertan una sola vez cuando Room crea la base de datos.
 * El stock es variado a propósito para que se aprecien los tres estados de HU08:
 * normal (> 5), stock bajo (<= 5) y sin stock (0).
 */
object ProductoSeed {

    val PRODUCTOS: List<ProductoEntity> = listOf(
        ProductoEntity(nombre = "Martillo de uña 16oz", descripcion = "Martillo de carpintero con mango de fibra", precio = 12500.0, cantidad = 14),
        ProductoEntity(nombre = "Cinta métrica 5m", descripcion = "Cinta métrica metálica con freno", precio = 9200.0, cantidad = 8),
        ProductoEntity(nombre = "Cemento gris 25kg", descripcion = "Cemento Portland tipo I", precio = 18700.0, cantidad = 22),
        ProductoEntity(nombre = "Taladro inalámbrico 18V", descripcion = "Taladro percutor a batería con cargador", precio = 189900.0, cantidad = 6),
        ProductoEntity(nombre = "Caja de tornillos 1/4\" x100", descripcion = "Tornillos para madera de 1/4 de pulgada", precio = 8900.0, cantidad = 3),
        ProductoEntity(nombre = "Guantes de seguridad", descripcion = "Guantes de nitrilo reforzado, talla L", precio = 6500.0, cantidad = 40),
        ProductoEntity(nombre = "Pintura blanca 1gal", descripcion = "Pintura vinílica lavable para interiores", precio = 45000.0, cantidad = 11),
        ProductoEntity(nombre = "Llave inglesa 10\"", descripcion = "Llave ajustable de acero al cromo vanadio", precio = 15800.0, cantidad = 5),
        ProductoEntity(nombre = "Brocha 3\"", descripcion = "Brocha de cerda natural para pintura", precio = 4200.0, cantidad = 0),
        ProductoEntity(nombre = "Tubo PVC 1/2\" x3m", descripcion = "Tubo de PVC para agua a presión", precio = 7300.0, cantidad = 18)
    )
}
