package com.example.tienda.model
//Hecho por galio
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "trabajadores",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ]
)
data class Trabajador(
    @PrimaryKey(autoGenerate = true)
    val trabajadorId: Int = 0,
    val usuarioId: Int,                  // 🔗 Relación con Usuario
    val rol: String,
    val salario: Double?,
    val fechaContratacion: Long = System.currentTimeMillis()
)

