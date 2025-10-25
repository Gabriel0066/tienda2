package com.example.tienda.model
// Hecho por galio

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "arriendos",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = Servicio::class,
            parentColumns = ["servicioId"],
            childColumns = ["servicioId"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ]
)
data class Arriendo(
    @PrimaryKey(autoGenerate = true)
    val arriendoId: Int = 0,
    val servicioId: Int,                  // 🔗 relación con Servicio
    val productoId: Int,
    val clienteId: Int,
    val precio: Double,
    val fechaInicio: Long,
    val fechaFin: Long?,
    val devuelto: Boolean = false,
    val duracionEstimadaMin: Int?,
    val fechaCreacion: Long = System.currentTimeMillis()
)

