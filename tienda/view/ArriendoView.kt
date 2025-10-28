package com.example.tienda.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tienda.model.Arriendo
import com.example.tienda.viewmodel.ArriendoViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ArriendoScreen(viewModel: ArriendoViewModel) {
    val arriendos by viewModel.arriendos.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedArriendo by remember { mutableStateOf<Arriendo?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Arriendos") },
                actions = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar Arriendo")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(arriendos) { arriendo ->
                ArriendoItem(
                    arriendo = arriendo,
                    onEdit = { selectedArriendo = it },
                    onDelete = {
                        LaunchedEffect(Unit) {
                            viewModel.eliminar(it)
                        }
                    }
                )
            }
        }

        if (showDialog) {
            ArriendoDialog(
                arriendo = selectedArriendo,
                onDismiss = {
                    showDialog = false
                    selectedArriendo = null
                },
                onConfirm = { arriendo ->
                    LaunchedEffect(Unit) {
                        if (selectedArriendo == null) {
                            viewModel.insertar(arriendo)
                        } else {
                            viewModel.actualizar(arriendo)
                        }
                    }
                    showDialog = false
                    selectedArriendo = null
                }
            )
        }
    }
}

@Composable
fun ArriendoItem(
    arriendo: Arriendo,
    onEdit: (Arriendo) -> Unit,
    onDelete: (Arriendo) -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = "ID Servicio: ${arriendo.servicioId}")
            Text(text = "ID Producto: ${arriendo.productoId}")
            Text(text = "ID Cliente: ${arriendo.clienteId}")
            Text(text = "Precio: $${arriendo.precio}")
            Text(text = "Fecha Inicio: ${dateFormat.format(Date(arriendo.fechaInicio))}")
            arriendo.fechaFin?.let {
                Text(text = "Fecha Fin: ${dateFormat.format(Date(it))}")
            }
            Text(text = "Estado: ${if (arriendo.devuelto) "Devuelto" else "En arriendo"}")
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { onEdit(arriendo) }) {
                    Text("Editar")
                }
                TextButton(onClick = { onDelete(arriendo) }) {
                    Text("Eliminar")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArriendoDialog(
    arriendo: Arriendo?,
    onDismiss: () -> Unit,
    onConfirm: (Arriendo) -> Unit
) {
    var servicioId by remember { mutableStateOf(arriendo?.servicioId?.toString() ?: "") }
    var productoId by remember { mutableStateOf(arriendo?.productoId?.toString() ?: "") }
    var clienteId by remember { mutableStateOf(arriendo?.clienteId?.toString() ?: "") }
    var precio by remember { mutableStateOf(arriendo?.precio?.toString() ?: "") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (arriendo == null) "Nuevo Arriendo" else "Editar Arriendo") },
        text = {
            Column {
                TextField(
                    value = servicioId,
                    onValueChange = { servicioId = it },
                    label = { Text("ID Servicio") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = productoId,
                    onValueChange = { productoId = it },
                    label = { Text("ID Producto") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = clienteId,
                    onValueChange = { clienteId = it },
                    label = { Text("ID Cliente") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val newArriendo = Arriendo(
                        arriendoId = arriendo?.arriendoId ?: 0,
                        servicioId = servicioId.toIntOrNull() ?: 0,
                        productoId = productoId.toIntOrNull() ?: 0,
                        clienteId = clienteId.toIntOrNull() ?: 0,
                        precio = precio.toDoubleOrNull() ?: 0.0,
                        fechaInicio = System.currentTimeMillis(),
                        fechaFin = null,
                        devuelto = false,
                        duracionEstimadaMin = null
                    )
                    onConfirm(newArriendo)
                }
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}