package me.sulaxan.zombies

import me.sulaxan.zombies.game.Zombies
import me.sulaxan.zombies.game.state.StateGame
import java.util.*

/**
 * Starts the game using any specified values by the user.
 */
fun main(args: Array<String>) {
    val PREFIX = "[ZOMBIES LAUNCHER]"

    println("-------------------------------------------")
    println("$PREFIX Starting Zombies via Kotlin...")

    val s = Scanner(System.`in`)
    println("$PREFIX Run through specialized game process (yes|no)?")
    val answer = s.next()

    if(answer.toLowerCase() == "yes") {
        println("Skip menu (yes|no)?")
        if(s.next() == "yes") {
            Zombies.getInstance().setState(Zombies.getInstance().getGameStateIndex(StateGame::class.java))
        } else {
            Zombies.getInstance()
        }
    } else {
        println("Starting default game!")
        Zombies.getInstance()
    }
}