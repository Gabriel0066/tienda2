package com.example.tienda.data.repository

import com.example.tienda.data.dao.VentaDao
import com.example.tienda.model.Venta
import kotlinx.coroutines.flow.Flow

class VentaRepository(private val ventaDao: VentaDao) {
    fun getAll(): Flow<List<Venta>> = ventaDao.getAll()

    suspend fun getById(id: Int): Venta? = ventaDao.getById(id)

    suspend fun insert(venta: Venta): Long = ventaDao.insert(venta)

    suspend fun update(venta: Venta) = ventaDao.update(venta)

    suspend fun delete(venta: Venta) = ventaDao.delete(venta)
}
