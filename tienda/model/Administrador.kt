package com.example.tienda.model
//Hecho por galio
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "administradores",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ]
)
data class Administrador(
    @PrimaryKey(autoGenerate = true)
    val administradorId: Int = 0,
    val usuarioId: Int,                  // 🔗 Relación con Usuario
    val nivelAcceso: Int = 1,
    val fechaRegistro: Long = System.currentTimeMillis()
)
