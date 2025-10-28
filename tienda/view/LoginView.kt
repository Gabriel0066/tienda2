package com.example.tienda.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tienda.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

/**
 * Pantalla de Login, basada en el 'FormularioScreen.kt' de tu profesor.
 * Utiliza el UsuarioViewModel que hizo tu compañero.
 */
@Composable
fun LoginScreen(
    usuarioViewModel: UsuarioViewModel,
    onLoginSuccess: (rol: String) -> Unit, // Llama a esto cuando el login es exitoso
    onNavigateToRegister: () -> Unit // Nueva función para navegar al registro
) {
    // Estado para los campos de texto
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var mostrarError by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Ventas y Arriendos", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(32.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = clave,
            onValueChange = { clave = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))

        if (mostrarError) {
            Text(
                text = mensajeError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Button(
            onClick = {
                scope.launch {
                    if (correo.isBlank() || clave.isBlank()) {
                        mostrarError = true
                        mensajeError = "Por favor complete todos los campos"
                        return@launch
                    }
                    
                    val loginExitoso = usuarioViewModel.login(correo, clave)
                    if (loginExitoso) {
                        val usuarioActual = usuarioViewModel.usuarioLogueado.value
                        // Determinar el rol basado en el tipo de usuario
                        val rol = when (usuarioActual) {
                            is Administrador -> "admin"
                            is Trabajador -> "worker"
                            is Cliente -> "client"
                            else -> "client"
                        }
                        mostrarError = false
                        onLoginSuccess(rol)
                    } else {
                        mostrarError = true
                        mensajeError = "Correo o contraseña incorrectos"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = onNavigateToRegister,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿No tienes cuenta? Regístrate aquí")
        }

        // TODO: Añadir un botón de "Registrar" que navegue a una pantalla de registro
    }
}