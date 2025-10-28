package com.example.tienda.data.repository

import com.example.tienda.data.dao.UsuarioDao
import com.example.tienda.model.Usuario
import kotlinx.coroutines.flow.Flow

class UsuarioRepository(private val usuarioDao: UsuarioDao) {
    fun getAll(): Flow<List<Usuario>> = usuarioDao.getAll()

    suspend fun getById(id: Int): Usuario? = usuarioDao.getById(id)

    suspend fun getByCorreo(correo: String): Usuario? = usuarioDao.getByCorreo(correo)

    suspend fun insert(usuario: Usuario): Long = usuarioDao.insert(usuario)

    suspend fun update(usuario: Usuario) = usuarioDao.update(usuario)

    suspend fun delete(usuario: Usuario) = usuarioDao.delete(usuario)
}
