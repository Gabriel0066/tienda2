package com.example.tienda.data.dao

import androidx.room.*
import com.example.tienda.model.Arriendo
import kotlinx.coroutines.flow.Flow

@Dao
interface ArriendoDao {
    @Query("SELECT * FROM arriendos")
    fun getAll(): Flow<List<Arriendo>>

    @Query("SELECT * FROM arriendos WHERE arriendoId = :id")
    suspend fun getById(id: Int): Arriendo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(arriendo: Arriendo): Long

    @Update
    suspend fun update(arriendo: Arriendo)

    @Delete
    suspend fun delete(arriendo: Arriendo)
}
