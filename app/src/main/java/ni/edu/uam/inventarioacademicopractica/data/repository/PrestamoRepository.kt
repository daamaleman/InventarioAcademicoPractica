package ni.edu.uam.inventarioacademicopractica.data.repository

import kotlinx.coroutines.flow.Flow
import ni.edu.uam.inventarioacademicopractica.data.local.dao.PrestamoDao
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Prestamo

class PrestamoRepository(private val prestamoDao: PrestamoDao) {
    val allPrestamos: Flow<List<Prestamo>> = prestamoDao.getAllPrestamos()

    suspend fun insert(prestamo: Prestamo) = prestamoDao.insert(prestamo)
    suspend fun update(prestamo: Prestamo) = prestamoDao.update(prestamo)
}
