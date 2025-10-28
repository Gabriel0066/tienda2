package com.example.tienda.data.repository

import com.example.tienda.data.dao.ServicioDao
import com.example.tienda.model.Servicio
import kotlinx.coroutines.flow.Flow

class ServicioRepository(private val servicioDao: ServicioDao) {
    fun getAll(): Flow<List<Servicio>> = servicioDao.getAll()

    suspend fun getById(id: Int): Servicio? = servicioDao.getById(id)

    suspend fun insert(servicio: Servicio): Long = servicioDao.insert(servicio)

    suspend fun update(servicio: Servicio) = servicioDao.update(servicio)

    suspend fun delete(servicio: Servicio) = servicioDao.delete(servicio)
}
