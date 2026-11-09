package esq.dam

fun main() {
    println(" Listado inicial ")
    VideogamesDAO.listGames().forEach { println(" - $it") }

    println(" Consulta por ID (4) ")
    println(VideogamesDAO.findById(4) ?: "No se encontró ningún videojuego con ese ID.")

    println(" Insertar con género válido ")
    val eldenRing = Videogame(title = "Elden Ring", genre = "Aventura", year = 2022, grade = 9.6)
    val newId = VideogamesDAO.insertVideogame(eldenRing)
    println(if (newId != null) "Insertado con ID $newId" else "No se pudo insertar.")

    println(" Insertar con género inexistente (debe rechazarse sin dejar basura) ")
    val fake = Videogame(title = "Fake Game", genre = "Souls-like", year = 2023, grade = 9.0)
    val fakeId = VideogamesDAO.insertVideogame(fake)
    println(if (fakeId == null) "Inserción rechazada correctamente." else "Insertado con ID $fakeId")

    println("Actualizar nota del videojuego insertado ")
    if (newId != null) {
        val ok = VideogamesDAO.updateVideogame(eldenRing.copy(id = newId, grade = 9.9))
        println(if (ok) "Nota actualizada a 9.9." else "No se encontró el ID $newId.")
    }

    println(" Eliminar el videojuego insertado ")
    if (newId != null) {
        val ok = VideogamesDAO.deleteVideogame(newId)
        println(if (ok) "Eliminado el videojuego con ID $newId." else "No se encontró el ID $newId.")
    }

    println(" Listado final ")
    VideogamesDAO.listGames().forEach { println(" - $it") }
}
