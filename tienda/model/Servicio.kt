package com.example.tienda.model
//Hecho por galio
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servicios")
data class Servicio(
    @PrimaryKey(autoGenerate = true)
    val servicioId: Int = 0,
    val precio: Double,
    val disponible: Boolean = true,
    val duracionEstimadaMin: Int?,
    val fechaCreacion: Long = System.currentTimeMillis() // Fecha de registro del servicio
)
