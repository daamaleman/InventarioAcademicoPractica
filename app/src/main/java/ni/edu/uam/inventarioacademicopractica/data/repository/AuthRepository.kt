package ni.edu.uam.inventarioacademicopractica.data.repository

import ni.edu.uam.inventarioacademicopractica.data.local.dao.AdministradorDao
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Administrador

class AuthRepository(private val adminDao: AdministradorDao) {
    suspend fun login(usuario: String, password: String) = adminDao.login(usuario, password)
    
    suspend fun ensureAdminExists() {
        if (adminDao.countAdmins() == 0) {
            adminDao.insert(Administrador(usuario = "admin", password = "admin"))
        }
    }
}
