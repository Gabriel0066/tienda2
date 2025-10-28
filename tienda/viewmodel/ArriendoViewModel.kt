package com.example.tienda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tienda.data.repository.ArriendoRepository
import com.example.tienda.model.Arriendo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

open class ArriendoViewModel(private val repository: ArriendoRepository) : ViewModel() {

    private val _arriendos = MutableStateFlow<List<Arriendo>>(emptyList())
    val arriendos: StateFlow<List<Arriendo>> = _arriendos.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect { list ->
                _arriendos.value = list
            }
        }
    }

    suspend fun insertar(arriendo: Arriendo): Long = repository.insert(arriendo)

    suspend fun actualizar(arriendo: Arriendo) = repository.update(arriendo)

    suspend fun eliminar(arriendo: Arriendo) = repository.delete(arriendo)

    suspend fun obtenerPorId(id: Int): Arriendo? = repository.getById(id)

    class Factory(private val repository: ArriendoRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArriendoViewModel::class.java)) {
                return ArriendoViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
