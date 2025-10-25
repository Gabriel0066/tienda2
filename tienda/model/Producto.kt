package com.example.tienda.model
//Hecho por galio
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val productoId: Int = 0,
    val nombre: String,
    val descripcion: String?,
    val categoria: String,
    val talla: String?,
    val color: String?,
    val precioVenta: Double?,
    val precioArriendo: Double?,
    val disponibleVenta: Boolean = true,
    val disponibleArriendo: Boolean = false,
    val stock: Int = 0,
    val fechaCreacion: Long = System.currentTimeMillis() // Fecha de registro del producto
)