package esq.dam

import java.io.File
import java.sql.Connection
import java.sql.DriverManager

class DatabaseManager {

    val dbPath = "proyecto/datos/gamecatalog.sqlite"
    val dbFile = File(dbPath)
    val url = "jdbc:sqlite:${dbFile.absolutePath}"
    fun getConnection() : Connection? {
        try {
            val connection: Connection = DriverManager.getConnection(url)
            return connection
        } catch (e: Exception) {
            println("Error al conectar: ${e.message}")
            return null
        }
    }


}