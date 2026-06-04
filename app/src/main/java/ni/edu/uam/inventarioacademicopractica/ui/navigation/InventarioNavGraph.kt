package ni.edu.uam.inventarioacademicopractica.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ni.edu.uam.inventarioacademicopractica.ui.screen.DashboardScreen
import ni.edu.uam.inventarioacademicopractica.ui.screen.FormularioEquipoScreen
import ni.edu.uam.inventarioacademicopractica.ui.screen.HistorialScreen
import ni.edu.uam.inventarioacademicopractica.ui.screen.ListaEquiposScreen
import ni.edu.uam.inventarioacademicopractica.ui.screen.PrestamosScreen

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object ListaEquipos : Screen("lista_equipos")
    object FormularioEquipo : Screen("formulario_equipo")
    object Prestamos : Screen("prestamos")
    object Historial : Screen("historial")
}

@Composable
fun InventarioNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen()
        }
        composable(Screen.ListaEquipos.route) {
            ListaEquiposScreen()
        }
        composable(Screen.FormularioEquipo.route) {
            FormularioEquipoScreen()
        }
        composable(Screen.Prestamos.route) {
            PrestamosScreen()
        }
        composable(Screen.Historial.route) {
            HistorialScreen()
        }
    }
}
