package com.example.tienda.data.dao

import androidx.room.*
import com.example.tienda.model.Servicio
import kotlinx.coroutines.flow.Flow

@Dao
interface ServicioDao {
    @Query("SELECT * FROM servicios")
    fun getAll(): Flow<List<Servicio>>

    @Query("SELECT * FROM servicios WHERE servicioId = :id")
    suspend fun getById(id: Int): Servicio?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(servicio: Servicio): Long

    @Update
    suspend fun update(servicio: Servicio)

    @Delete
    suspend fun delete(servicio: Servicio)
}
