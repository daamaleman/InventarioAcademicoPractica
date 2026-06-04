package ni.edu.uam.inventarioacademicopractica.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ni.edu.uam.inventarioacademicopractica.InventarioApplication
import ni.edu.uam.inventarioacademicopractica.ui.screen.*
import ni.edu.uam.inventarioacademicopractica.ui.viewmodel.*

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object ListaEquipos : Screen("lista_equipos")
    object FormularioEquipo : Screen("formulario_equipo/{equipoId}") {
        fun createRoute(equipoId: Int = -1) = "formulario_equipo/$equipoId"
    }
    object Prestamos : Screen("prestamos")
    object Historial : Screen("historial")
}

@Composable
fun InventarioNavGraph(navController: NavHostController) {
    val context = LocalContext.current
    val application = context.applicationContext as InventarioApplication
    val factory = ViewModelFactory(
        application.equipoRepository, 
        application.prestamoRepository,
        application.authRepository
    )

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            val loginViewModel: LoginViewModel = viewModel(factory = factory)
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = { 
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Dashboard.route) {
            val dashboardViewModel: DashboardViewModel = viewModel(factory = factory)
            DashboardScreen(
                viewModel = dashboardViewModel,
                onVerEquiposClick = { navController.navigate(Screen.ListaEquipos.route) },
                onVerHistorialClick = { navController.navigate(Screen.Historial.route) },
                onRegistrarPrestamoClick = { navController.navigate(Screen.Prestamos.route) }
            )
        }
        composable(Screen.ListaEquipos.route) {
            val equipoViewModel: EquipoViewModel = viewModel(factory = factory)
            ListaEquiposScreen(
                viewModel = equipoViewModel,
                onAgregarClick = { navController.navigate(Screen.FormularioEquipo.createRoute()) },
                onEditarClick = { id -> navController.navigate(Screen.FormularioEquipo.createRoute(id)) }
            )
        }
        composable(
            route = Screen.FormularioEquipo.route,
            arguments = listOf(navArgument("equipoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val equipoId = backStackEntry.arguments?.getInt("equipoId") ?: -1
            val equipoViewModel: EquipoViewModel = viewModel(factory = factory)
            FormularioEquipoScreen(
                equipoId = equipoId,
                onEquipoGuardado = { navController.popBackStack() },
                viewModel = equipoViewModel
            )
        }
        composable(Screen.Prestamos.route) {
            val equipoViewModel: EquipoViewModel = viewModel(factory = factory)
            val prestamoViewModel: PrestamoViewModel = viewModel(factory = factory)
            PrestamosScreen(
                equipoViewModel = equipoViewModel,
                prestamoViewModel = prestamoViewModel,
                onPrestamoRegistrado = { navController.popBackStack() }
            )
        }
        composable(Screen.Historial.route) {
            val prestamoViewModel: PrestamoViewModel = viewModel(factory = factory)
            val equipoViewModel: EquipoViewModel = viewModel(factory = factory)
            HistorialScreen(
                prestamoViewModel = prestamoViewModel,
                equipoViewModel = equipoViewModel
            )
        }
    }
}
