package com.example.tienda.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tienda.data.dao.*
import com.example.tienda.model.*

@Database(
    entities = [
        Usuario::class,
        Producto::class,
        Servicio::class,
        Venta::class,
        Arriendo::class,
        Cliente::class,
        Trabajador::class,
        Administrador::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun productoDao(): ProductoDao
    abstract fun servicioDao(): ServicioDao
    abstract fun ventaDao(): VentaDao
    abstract fun arriendoDao(): ArriendoDao
}
