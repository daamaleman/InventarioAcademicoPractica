package ni.edu.uam.inventarioacademicopractica

import android.app.Application
import androidx.room.Room
import ni.edu.uam.inventarioacademicopractica.data.local.AppDatabase
import ni.edu.uam.inventarioacademicopractica.data.repository.EquipoRepository
import ni.edu.uam.inventarioacademicopractica.data.repository.PrestamoRepository

class InventarioApplication : Application() {
    private val database by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "inventario_db").build()
    }

    val equipoRepository by lazy { EquipoRepository(database.equipoDao()) }
    val prestamoRepository by lazy { PrestamoRepository(database.prestamoDao()) }
}
