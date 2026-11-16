package esq.dam


var USER : Int? = null

fun main() {
    var option = -1
    var vgservice = VideogameCatalogService()
    while (option != 0) {
        showMenu()
        option = readInt("Elige una opcion: ")
        try {
            when (option) {
                1 -> vgservice.showAllVideogames()
                2 -> vgservice.findVideogame()
                3 -> vgservice.addVideogame()
                4 -> vgservice.updateVideogame()
                5 -> vgservice.deleteVideogame()
                6 -> vgservice.addPlayer()
                7 -> vgservice.deletePlayer()
                8 -> vgservice.addList()
                9 -> vgservice.deleteList()
                10 -> vgservice.addVideogameToList()
                11 -> vgservice.showVideogamesFromList()
                0 -> println("Hasta luego.")
                else -> println("Opcion no valida.")
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}

fun showMenu() {
    println("Usuario: $USER")
    println("===== CATALOGO DE VIDEOJUEGOS =====")
    println("-- Videojuegos --")
    println("1. Ver todos los videojuegos")
    println("2. Buscar videojuego por ID")
    println("3. Anadir videojuego")
    println("4. Modificar videojuego")
    println("5. Eliminar videojuego")
    println("-- Jugadores --")
    println("6. Crear jugador")
    println("7. Eliminar jugador")
    println("-- Listas --")
    println("8. Crear lista")
    println("9. Eliminar lista")
    println("10. Anadir videojuego a una lista")
    println("11. Ver videojuegos de una lista")
    println("0. Salir")
}

fun readText(message: String): String {
    var text = ""
    while (text.isBlank()) {
        print(message)
        text = readln().trim()
    }
    return text
}

fun readInt(message: String): Int {
    while (true) {
        print(message)
        val number = readln().trim().toIntOrNull()
        if (number != null) return number
        println("Introduce un numero entero.")
    }
}

fun readDouble(message: String): Double {
    while (true) {
        print(message)
        val number = readln().trim().replace(",", ".").toDoubleOrNull()
        if (number != null) return number
        println("Introduce un numero.")
    }
}
