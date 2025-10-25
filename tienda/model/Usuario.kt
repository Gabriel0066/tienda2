package com.example.tienda.model
//Hecho por galio
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val correo: String,
    val contrasenaHash: String,
    val telefono: String?,
    val direccion: String?,
    val fechaCreacion: Long = System.currentTimeMillis() // Fecha de registro
)
