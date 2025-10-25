package com.example.tienda.model
// Hecho por galio

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "ventas",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = Servicio::class,
            parentColumns = ["servicioId"],
            childColumns = ["servicioId"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ]
)
data class Venta(
    @PrimaryKey(autoGenerate = true)
    val ventaId: Int = 0,
    val servicioId: Int,                  // 🔗 relación con Servicio
    val productoId: Int,
    val clienteId: Int,
    val precio: Double,
    val cantidad: Int = 1,
    val fechaVenta: Long = System.currentTimeMillis(),
    val duracionEstimadaMin: Int? = null,
    val fechaCreacion: Long = System.currentTimeMillis()
)

