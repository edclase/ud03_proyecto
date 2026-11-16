package esq.dam

import java.io.File
import java.sql.Connection
import java.sql.DriverManager

object DatabaseManager {

    private const val HOST = "localhost"
    private const val PORT = 3306
    private const val DATABASE = "videogamescatalog"
    private const val USER = "dam2026"
    private const val PASSWORD = "dam2026"

    private val url = "jdbc:mysql://$HOST:$PORT/$DATABASE?useSSL=false&serverTimezone=Europe/Madrid"

    fun getConnection() : Connection? {
        return try {
            DriverManager.getConnection(url, USER, PASSWORD)
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