package com.example.tienda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tienda.data.repository.UsuarioRepository
import com.example.tienda.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest

open class UsuarioViewModel(private val repository: UsuarioRepository) : ViewModel() {

    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios: StateFlow<List<Usuario>> = _usuarios.asStateFlow()

    private val _usuarioLogueado = MutableStateFlow<Usuario?>(null)
    val usuarioLogueado: StateFlow<Usuario?> = _usuarioLogueado.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect { list ->
                _usuarios.value = list
            }
        }
    }

    suspend fun registrarUsuario(
        nombre: String,
        correo: String,
        contrasenaPlain: String,
        telefono: String? = null,
        direccion: String? = null
    ): Boolean {
        if (nombre.isBlank() || correo.isBlank() || contrasenaPlain.isBlank()) return false

        val existing = repository.getByCorreo(correo)
        if (existing != null) return false

        val usuario = Usuario(
            nombre = nombre,
            correo = correo,
            contrasenaHash = hashPassword(contrasenaPlain),
            telefono = telefono,
            direccion = direccion
        )

        repository.insert(usuario)
        return true
    }

    suspend fun login(correo: String, contrasenaPlain: String): Boolean {
        val user = repository.getByCorreo(correo) ?: return false
        val hash = hashPassword(contrasenaPlain)
        return if (user.contrasenaHash == hash) {
            _usuarioLogueado.value = user
            true
        } else {
            false
        }
    }

    fun logout() {
        _usuarioLogueado.value = null
    }

    suspend fun actualizarUsuario(updated: Usuario): Boolean {
        val exists = repository.getById(updated.id) != null
        if (!exists) return false
        repository.update(updated)
        if (_usuarioLogueado.value?.id == updated.id) {
            _usuarioLogueado.value = updated
        }
        return true
    }

    suspend fun eliminarUsuario(id: Int): Boolean {
        val user = repository.getById(id) ?: return false
        repository.delete(user)
        if (_usuarioLogueado.value?.id == id) _usuarioLogueado.value = null
        return true
    }

    suspend fun obtenerUsuarioPorId(id: Int): Usuario? = repository.getById(id)

    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(password.toByteArray(Charsets.UTF_8))
        return digest.joinToString("") { "%02x".format(it) }
    }

    class Factory(private val repository: UsuarioRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UsuarioViewModel::class.java)) {
                return UsuarioViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}