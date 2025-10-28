package com.example.tienda.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tienda.model.Venta
import kotlinx.coroutines.flow.Flow

@Dao
interface VentaDao {
    @Query("SELECT * FROM ventas")
    fun getAll(): Flow<List<Venta>>

    @Query("SELECT * FROM ventas WHERE ventaId = :id")
    suspend fun getById(id: Int): Venta?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(venta: Venta): Long

    @Update
    suspend fun update(venta: Venta)

    @Delete
    suspend fun delete(venta: Venta)
}
