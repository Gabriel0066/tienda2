package com.example.tienda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tienda.data.repository.ServicioRepository
import com.example.tienda.model.Servicio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

open class ServicioViewModel(private val repository: ServicioRepository) : ViewModel() {

    private val _servicios = MutableStateFlow<List<Servicio>>(emptyList())
    val servicios: StateFlow<List<Servicio>> = _servicios.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect { list ->
                _servicios.value = list
            }
        }
    }

    suspend fun insertar(servicio: Servicio): Long = repository.insert(servicio)

    suspend fun actualizar(servicio: Servicio) = repository.update(servicio)

    suspend fun eliminar(servicio: Servicio) = repository.delete(servicio)

    suspend fun obtenerPorId(id: Int): Servicio? = repository.getById(id)

    class Factory(private val repository: ServicioRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ServicioViewModel::class.java)) {
                return ServicioViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
