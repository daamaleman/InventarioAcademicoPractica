package ni.edu.uam.inventarioacademicopractica

import android.app.Application
import androidx.room.Room
import ni.edu.uam.inventarioacademicopractica.data.local.AppDatabase
import ni.edu.uam.inventarioacademicopractica.data.repository.AuthRepository
import ni.edu.uam.inventarioacademicopractica.data.repository.EquipoRepository
import ni.edu.uam.inventarioacademicopractica.data.repository.PrestamoRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class InventarioApplication : Application() {
    private val database by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "inventario_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    val equipoRepository by lazy { EquipoRepository(database.equipoDao()) }
    val prestamoRepository by lazy { PrestamoRepository(database.prestamoDao()) }
    val authRepository by lazy { AuthRepository(database.administradorDao()) }

    override fun onCreate() {
        super.onCreate()
        // Crear admin por defecto si no existe
        MainScope().launch {
            authRepository.ensureAdminExists()
        }
    }
}
