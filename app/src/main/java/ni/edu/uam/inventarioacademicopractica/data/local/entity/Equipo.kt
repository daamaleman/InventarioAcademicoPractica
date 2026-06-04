package ni.edu.uam.inventarioacademicopractica.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipos")
data class Equipo(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,

    val categoria: String,

    val marca: String,

    val numeroSerie: String,

    val disponible: Boolean = true
)
