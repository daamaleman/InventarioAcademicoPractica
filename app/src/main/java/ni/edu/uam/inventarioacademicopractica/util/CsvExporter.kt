package ni.edu.uam.inventarioacademicopractica.util

import android.content.Context
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Equipo
import java.io.File

object CsvExporter {
    fun exportEquiposToCsv(context: Context, equipos: List<Equipo>): String? {
        val fileName = "inventario_equipos.csv"
        val file = File(context.filesDir, fileName)
        
        return try {
            val header = "ID,Nombre,Categoria,Marca,Serie,Disponible\n"
            val content = equipos.joinToString("\n") { equipo ->
                "${equipo.id},${equipo.nombre},${equipo.categoria},${equipo.marca},${equipo.numeroSerie},${equipo.disponible}"
            }
            
            file.writeText(header + content)
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
