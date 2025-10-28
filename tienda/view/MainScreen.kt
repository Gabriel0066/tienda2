package com.example.tienda.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tienda.data.repository.ArriendoRepository
import com.example.tienda.data.repository.ProductoRepository
import com.example.tienda.data.repository.UsuarioRepository
import com.example.tienda.data.repository.VentaRepository
import com.example.tienda.viewmodel.ArriendoViewModel
import com.example.tienda.viewmodel.ProductoViewModel
import com.example.tienda.viewmodel.UsuarioViewModel
import com.example.tienda.viewmodel.VentaViewModel

/**
 * Gestiona toda la navegación de la app.
 * Este Composable decide qué pantalla mostrar.
 */
@Composable
fun AppNavigation(
    // Los ViewModels se pasan como argumentos desde MainActivity.
    usuarioViewModel: UsuarioViewModel,
    productoViewModel: ProductoViewModel,
    ventaViewModel: VentaViewModel,
    arriendoViewModel: ArriendoViewModel
) {
    val navController = rememberNavController()

    // El NavHost define las "rutas" (pantallas) de tu app
    NavHost(
        navController = navController,
        startDestination = "login" // Inicia en la pantalla de login
    ) {

        // Ruta 1: Pantalla de Login (implementada en LoginView.kt)
        composable(route = "login") {
            LoginScreen(
                usuarioViewModel = usuarioViewModel,
                onLoginSuccess = { rol ->
                    // Navega a la pantalla correcta según el ROL
                    when (rol) {
                        "admin" -> navController.navigate("admin") { popUpTo("login") { inclusive = true } }
                        "worker" -> navController.navigate("worker") { popUpTo("login") { inclusive = true } }
                        else -> navController.navigate("client") { popUpTo("login") { inclusive = true } }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("registro")
                }
            )
        }

        composable(route = "registro") {
            RegistroScreen(
                usuarioViewModel = usuarioViewModel,
                onRegistroSuccess = {
                    navController.navigate("login") {
                        popUpTo("registro") { inclusive = true }
                    }
                },
                onBackToLogin = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = "admin") {
            AdministradorView(
                usuarioViewModel = usuarioViewModel,
                productoViewModel = productoViewModel,
                ventaViewModel = ventaViewModel,
                arriendoViewModel = arriendoViewModel
            )
        }

        composable(route = "client") {
            HomeScreen(
                usuarioViewModel = usuarioViewModel,
                productoViewModel = productoViewModel,
                ventaViewModel = ventaViewModel,
                arriendoViewModel = arriendoViewModel
            )
        }

        composable(route = "worker") {
            // Aquí puedes agregar una vista específica para trabajadores si la necesitas
            HomeScreen(
                usuarioViewModel = usuarioViewModel,
                productoViewModel = productoViewModel,
                ventaViewModel = ventaViewModel,
                arriendoViewModel = arriendoViewModel
            )
        }
                        navController.navigate("client") { popUpTo("login") { inclusive = true } }
                    }
                }
            )
        }

        // Ruta 2: Pantalla de Cliente (Placeholder en este archivo)
        composable(route = "client") {
            ClientScreen(
                productoViewModel = productoViewModel,
                ventaViewModel = ventaViewModel,
                arriendoViewModel = arriendoViewModel,
                onViewCartClicked = {
                    // TODO: Navegar a la pantalla de carrito
                }
            )
        }

        // Ruta 3: Pantalla de Administrador (implementada en AdministradorView.kt)
        composable(route = "admin") {
            AdminScreen(
                productoViewModel = productoViewModel,
                onProductSaved = {
                    navController.popBackStack() // Regresa después de guardar
                }
            )
        }

        // TODO: Añadir ruta para "registro", "carrito", etc.
    }
}

/**
 * Placeholder temporal para la pantalla de cliente.
 */
@Composable
fun ClientScreen(
    productoViewModel: ProductoViewModel,
    ventaViewModel: VentaViewModel,
    arriendoViewModel: ArriendoViewModel,
    onViewCartClicked: () -> Unit
) {
    // Implementación básica para evitar el fallo de 'TODO()'
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Pantalla de Cliente - (Implementación Pendiente)")
    }
}


@Preview (showBackground = true)
@Composable
fun AppNavigationPreview(){
    // La CORRECCIÓN MÁS SIMPLE: Llama a AppNavigation sin argumentos.
    // Como ahora los argumentos son opcionales (null) y usan viewModel() si son null,
    // el Preview puede crearlos sin el error de 'repository'.
    AppNavigation(
        ArriendoRepository,
        ProductoRepository,
        VentaRepository,
        UsuarioRepository
    )
}