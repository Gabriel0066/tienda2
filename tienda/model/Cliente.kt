package com.example.tienda.model
//Hecho por galio
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "clientes",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ]
)
data class Cliente(
    @PrimaryKey(autoGenerate = true)
    val clienteId: Int = 0,
    val usuarioId: Int,                  // 🔗 Relación con Usuario
    val puedeArrendar: Boolean = true,
    val puntosFidelidad: Int = 0,
    val fechaRegistro: Long = System.currentTimeMillis()
)

