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
import ni.edu.uam.inventarioacademicopractica.ui.viewmodel.DashboardViewModel
import ni.edu.uam.inventarioacademicopractica.ui.viewmodel.EquipoViewModel
import ni.edu.uam.inventarioacademicopractica.ui.viewmodel.PrestamoViewModel
import ni.edu.uam.inventarioacademicopractica.ui.viewmodel.ViewModelFactory

sealed class Screen(val route: String) {
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
    val factory = ViewModelFactory(application.equipoRepository, application.prestamoRepository)

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            val dashboardViewModel: DashboardViewModel = viewModel(factory = factory)
            DashboardScreen(
                viewModel = dashboardViewModel,
                onVerEquiposClick = { navController.navigate(Screen.ListaEquipos.route) }
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
            HistorialScreen()
        }
    }
}
