package esq.dam

import java.io.File
import java.sql.Connection
import java.sql.DriverManager

object DatabaseManager {

    val dbPath = "proyecto/datos/gamecatalog.sqlite"
    val dbFile = File(dbPath)
    val url = "jdbc:sqlite:${dbFile.absolutePath}"

    fun getConnection() : Connection? {
        return try {
            DriverManager.getConnection(url)
        } catch (e: Exception) {
            println("Error al conectar: ${e.message}")
            null
        }
    }
    fun closeConnection(connection: Connection?){
        try {
            connection?.close()
        } catch (e: Exception) {
            println("Error al cerrar la conexion: ${e.message}")
        }
    }

}