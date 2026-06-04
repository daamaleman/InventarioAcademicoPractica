package ni.edu.uam.inventarioacademicopractica.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa la tabla "equipos" en la base de datos Room.
 * Almacena la información técnica de los equipos tecnológicos del laboratorio.
 */
@Entity(tableName = "equipos")
data class Equipo(
    // Identificador único de cada equipo, se genera automáticamente.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Nombre descriptivo del equipo (ej. Laptop Dell, Proyector Epson).
    val nombre: String,

    // Categoría a la que pertenece el equipo (ej. Computación, Audiovisuales).
    val categoria: String,

    // Marca del fabricante.
    val marca: String,

    // Número de serie único del hardware para control de inventario.
    val numeroSerie: String,

    // Estado de disponibilidad del equipo para nuevos préstamos.
    val disponible: Boolean = true
)
