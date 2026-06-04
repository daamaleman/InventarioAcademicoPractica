package ni.edu.uam.inventarioacademicopractica.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ni.edu.uam.inventarioacademicopractica.data.local.dao.AdministradorDao
import ni.edu.uam.inventarioacademicopractica.data.local.dao.EquipoDao
import ni.edu.uam.inventarioacademicopractica.data.local.dao.PrestamoDao
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Administrador
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Equipo
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Prestamo

@Database(
    entities = [
        Equipo::class,
        Prestamo::class,
        Administrador::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun equipoDao(): EquipoDao
    abstract fun prestamoDao(): PrestamoDao
    abstract fun administradorDao(): AdministradorDao
}
