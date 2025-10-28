package com.example.tienda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tienda.data.repository.ProductoRepository
import com.example.tienda.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

open class ProductoViewModel(private val repository: ProductoRepository) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect { list ->
                _productos.value = list
            }
        }
    }

    suspend fun insertar(producto: Producto): Long = repository.insert(producto)

    suspend fun actualizar(producto: Producto) = repository.update(producto)

    suspend fun eliminar(producto: Producto) = repository.delete(producto)

    suspend fun obtenerPorId(id: Int): Producto? = repository.getById(id)

    class Factory(private val repository: ProductoRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductoViewModel::class.java)) {
                return ProductoViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
