package com.example.tienda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tienda.data.repository.VentaRepository
import com.example.tienda.model.Venta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

open class VentaViewModel(private val repository: VentaRepository) : ViewModel() {

    private val _ventas = MutableStateFlow<List<Venta>>(emptyList())
    val ventas: StateFlow<List<Venta>> = _ventas.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect { list ->
                _ventas.value = list
            }
        }
    }

    suspend fun insertar(venta: Venta): Long = repository.insert(venta)

    suspend fun actualizar(venta: Venta) = repository.update(venta)

    suspend fun eliminar(venta: Venta) = repository.delete(venta)

    suspend fun obtenerPorId(id: Int): Venta? = repository.getById(id)

    class Factory(private val repository: VentaRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VentaViewModel::class.java)) {
                return VentaViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
