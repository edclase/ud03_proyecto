package esq.dam

import java.sql.Connection



fun main() {
    val connection : Connection? = DatabaseManager().getConnection()
    if (connection != null) {
        println("Conectado a base de datos")
    }

}