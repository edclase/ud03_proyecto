package esq.dam

class VideogameCatalogService() {


    fun showAllVideogames() {
        val videogames = VideogamesDAO.listGames()
        if (videogames.isEmpty()) println("No hay videojuegos.")
        videogames.forEach { println(" - $it") }
    }

    fun findVideogame() {
        val id = readInt("ID del videojuego: ")
        println(VideogamesDAO.findById(id) ?: "No se encontro ningun videojuego con ese ID.")
    }

    fun addVideogame() {
        val title = readText("Titulo: ")
        val genre = readText("Genero: ")
        val year = readInt("Anio: ")
        val grade = readDouble("Nota: ")
        val id = VideogamesDAO.insertVideogame(Videogame(title = title, genre = genre, year = year, grade = grade))
        println(if (id != null) "Videojuego insertado con ID $id." else "No se pudo insertar.")
    }

    fun updateVideogame() {
        val id = readInt("ID del videojuego a modificar: ")
        val videogame = VideogamesDAO.findById(id)
        if (videogame == null) {
            println("No se encontro ningun videojuego con ese ID.")
            return
        }
        println("Actual: $videogame")
        val title = readText("Nuevo titulo: ")
        val genre = readText("Nuevo genero: ")
        val year = readInt("Nuevo anio: ")
        val grade = readDouble("Nueva nota: ")
        val ok = VideogamesDAO.updateVideogame(Videogame(id, title, genre, year, grade))
        println(if (ok) "Videojuego actualizado." else "No se pudo actualizar.")
    }

    fun deleteVideogame() {
        val id = readInt("ID del videojuego a eliminar: ")
        val ok = VideogamesDAO.deleteVideogame(id)
        println(if (ok) "Videojuego eliminado." else "No se encontro ningun videojuego con ese ID.")
    }

    fun addPlayer() {
        val username = readText("Nombre: ")
        val email = readText("Email: ")
        val id = PlayerDAO.insert(Player(username = username, email = email))
        if (id != null) USER = id
        println(if (id != null) "Jugador creado con ID $id." else "No se pudo crear el jugador.")
    }

    fun deletePlayer() {
        val id = readInt("ID del jugador a eliminar: ")
        val ok = PlayerDAO.delete(id)
        if (ok && id == USER) USER = null
        println(if (ok) "Jugador eliminado junto con sus listas." else "No se encontro ningun jugador con ese ID.")
    }

    fun addList() {
        val input = readInt("ID del jugador (-1 para usar el usuario en uso) : ")
        val playerId = if (input == -1) USER else input
        if (playerId == null) {
            println("No hay usuario en uso, crea un jugador o introduce un ID.")
            return
        }
        val id = ListDAO.insert(playerId)
        println(if (id != null) "Lista del usuario $playerId creada con ID $id." else "El jugador no existe, no se ha creado la lista.")
    }

    fun deleteList() {
        val id = readInt("ID de la lista a eliminar: ")
        val ok = ListDAO.delete(id)
        println(if (ok) "Lista eliminada." else "No se encontro ninguna lista con ese ID.")
    }

    fun addVideogameToList() {
        val listId = readInt("ID de la lista: ")
        val videogameId = readInt("ID del videojuego: ")
        val ok = ListVideogameDAO.insert(ListVideogame(listId, videogameId))
        println(if (ok) "Videojuego anadido a la lista." else "No se pudo anadir el videojuego a la lista.")
    }

    fun showVideogamesFromList() {
        val listId = readInt("ID de la lista: ")
        val videogames = ListVideogameDAO.getVideogamesFromList(listId)
        if (videogames.isEmpty()) println("La lista no existe o no tiene videojuegos.")
        videogames.forEach { println(" - $it") }
    }

}