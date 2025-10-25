package com.example.tienda.data.dao

import androidx.room.*
import com.example.tienda.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos")
    fun getAll(): Flow<List<Producto>>

    @Query("SELECT * FROM productos WHERE productoId = :id")
    suspend fun getById(id: Int): Producto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: Producto): Long

    @Update
    suspend fun update(producto: Producto)

    @Delete
    suspend fun delete(producto: Producto)
}
