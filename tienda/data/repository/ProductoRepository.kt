package com.example.tienda.data.repository

import com.example.tienda.data.dao.ProductoDao
import com.example.tienda.model.Producto
import kotlinx.coroutines.flow.Flow

class ProductoRepository(private val productoDao: ProductoDao) {
    fun getAll(): Flow<List<Producto>> = productoDao.getAll()

    suspend fun getById(id: Int): Producto? = productoDao.getById(id)

    suspend fun insert(producto: Producto): Long = productoDao.insert(producto)

    suspend fun update(producto: Producto) = productoDao.update(producto)

    suspend fun delete(producto: Producto) = productoDao.delete(producto)
}
