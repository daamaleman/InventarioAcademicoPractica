package ni.edu.uam.inventarioacademicopractica.data.repository

import kotlinx.coroutines.flow.Flow
import ni.edu.uam.inventarioacademicopractica.data.local.dao.EquipoDao
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Equipo

class EquipoRepository(private val equipoDao: EquipoDao) {
    val allEquipos: Flow<List<Equipo>> = equipoDao.getAllEquipos()
    val equiposDisponibles: Flow<List<Equipo>> = equipoDao.getEquiposDisponibles()

    suspend fun insert(equipo: Equipo) = equipoDao.insert(equipo)
    suspend fun update(equipo: Equipo) = equipoDao.update(equipo)
    suspend fun delete(equipo: Equipo) = equipoDao.delete(equipo)
    suspend fun getEquipoById(id: Int) = equipoDao.getEquipoById(id)
    fun searchEquipos(query: String) = equipoDao.searchEquipos(query)
}
