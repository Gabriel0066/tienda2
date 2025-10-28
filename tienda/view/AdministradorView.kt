package com.example.tienda.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.unit.dp
import com.example.tienda.model.Producto
import com.example.tienda.viewmodel.ProductoViewModel
import kotlinx.coroutines.launch

/**
 * Pantalla para que el Administrador cree o edite productos.
 * Basada en el 'FormularioScreen.kt' de tu profesor.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    productoViewModel: ProductoViewModel,
    onProductSaved: () -> Unit
) {
    // Estado local para manejar el formulario
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var talla by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var precioVenta by remember { mutableStateOf("") }
    var precioArriendo by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Gestión de Productos") })
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            Arrangement.spacedBy(12.dp)
        ) {
            Text("Crear Nuevo Producto", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del Producto") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text("Categoría (ej. Fiesta, Disfraz)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = talla,
                onValueChange = { talla = it },
                label = { Text("Talla (Opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = color,
                onValueChange = { color = it },
                label = { Text("Color (Opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = precioVenta,
                onValueChange = { precioVenta = it },
                label = { Text("Precio Venta (ej. 99990)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = precioArriendo,
                onValueChange = { precioArriendo = it },
                label = { Text("Precio Arriendo (ej. 19990)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Stock") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    // TODO: Añadir validación como la de tu profesor
                    val nuevoProducto = Producto(
                        nombre = nombre,
                        descripcion = descripcion,
                        categoria = categoria,
                        talla = talla,
                        color = color,
                        precioVenta = precioVenta.toDoubleOrNull(),
                        precioArriendo = precioArriendo.toDoubleOrNull(),
                        disponibleVenta = precioVenta.isNotBlank(),
                        disponibleArriendo = precioArriendo.isNotBlank(),
                        stock = stock.toIntOrNull() ?: 0
                    )

                    scope.launch {
                        productoViewModel.insertar(nuevoProducto)
                        onProductSaved() // Navega hacia atrás
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Producto")
            }
        }
    }
}
