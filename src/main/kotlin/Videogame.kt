package esq.dam

import java.sql.Connection

data class Videogame(
    val id: Int? = null,
    val title: String,
    val genre: String,
    val year: Int,
    val grade: Double,
)

object VideogamesDAO {

    fun listGames(): List<Videogame> {
        val list = mutableListOf<Videogame>()
        val sql = "SELECT vg.id, vg.title, g.name AS genre, vg.year, vg.grade " +
                "FROM videogames vg JOIN genres g ON vg.genre_id = g.id"
        DatabaseManager.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { statement ->
                val rs = statement.executeQuery()
                while (rs.next()) {
                    list.add(
                        Videogame(
                            id = rs.getInt("id"),
                            title = rs.getString("title"),
                            genre = rs.getString("genre"),
                            year = rs.getInt("year"),
                            grade = rs.getDouble("grade"),
                        )
                    )
                }
            }
        }
        return list
    }

    fun findById(id: Int): Videogame? {
        val sql = "SELECT vg.id, vg.title, g.name AS genre, vg.year, vg.grade " +
                "FROM videogames vg JOIN genres g ON vg.genre_id = g.id WHERE vg.id = ?"
        DatabaseManager.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { statement ->
                statement.setInt(1, id)
                val rs = statement.executeQuery()
                if (rs.next()) {
                    return Videogame(
                        id = rs.getInt("id"),
                        title = rs.getString("title"),
                        genre = rs.getString("genre"),
                        year = rs.getInt("year"),
                        grade = rs.getDouble("grade"),
                    )
                }
            }
        }
        return null
    }

    fun insertVideogame(videogame: Videogame): Int? {
        DatabaseManager.getConnection()?.use { conn ->
            val genreId = getGenreId(conn, videogame.genre) ?: run {
                println("Error: el género '${videogame.genre}' no existe en la tabla genres.")
                return null
            }
            val sql = "INSERT INTO videogames(title, genre_id, year, grade) VALUES (?, ?, ?, ?)"
            conn.prepareStatement(sql).use { statement ->
                statement.setString(1, videogame.title)
                statement.setInt(2, genreId)
                statement.setInt(3, videogame.year)
                statement.setDouble(4, videogame.grade)
                if (statement.executeUpdate() > 0) {
                    conn.createStatement().use { keyStatement ->
                        keyStatement.executeQuery("SELECT last_insert_rowid()").use { rs ->
                            if (rs.next()) return rs.getInt(1)
                        }
                    }
                }
            }
        }
        return null
    }

    fun updateVideogame(videogame: Videogame): Boolean {
        val id = videogame.id
        if (id == null) {
            println("Error: no se puede actualizar un videojuego sin ID.")
            return false
        }
        DatabaseManager.getConnection()?.use { conn ->
            val genreId = getGenreId(conn, videogame.genre) ?: run {
                println("Error: el género '${videogame.genre}' no existe en la tabla genres.")
                return false
            }
            val sql = "UPDATE videogames SET title = ?, genre_id = ?, year = ?, grade = ? WHERE id = ?"
            conn.prepareStatement(sql).use { statement ->
                statement.setString(1, videogame.title)
                statement.setInt(2, genreId)
                statement.setInt(3, videogame.year)
                statement.setDouble(4, videogame.grade)
                statement.setInt(5, id)
                return statement.executeUpdate() > 0
            }
        }
        return false
    }

    fun deleteVideogame(id: Int): Boolean {
        val sql = "DELETE FROM videogames WHERE id = ?"
        DatabaseManager.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { statement ->
                statement.setInt(1, id)
                return statement.executeUpdate() > 0
            }
        }
        return false
    }

    private fun getGenreId(conn: Connection, genre: String): Int? {
        val sql = "SELECT id FROM genres WHERE name = ?"
        conn.prepareStatement(sql).use { statement ->
            statement.setString(1, genre)
            val rs = statement.executeQuery()
            if (rs.next()) return rs.getInt("id")
        }
        return null
    }
}
