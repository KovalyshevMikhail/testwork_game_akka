package com.nebiroz.game

import com.nebiroz.game.activity.{Loger, Player}
import com.nebiroz.game.activity.race.{Race, _}

class Game {
  def gameLoop(): Unit = {
    Loger.info("Игровой цикл")

    val players: List[Player] = List(
      new Player("Белые медведи", false),
      new Player("Чертовы мертвяки", true)
    )

    dumpPlayers(players)
    Loger.info("Да начнется битва!!\n\n")
    val roundResult: StringBuilder = new StringBuilder

    for (round <- 1 to 5) {
      roundResult append f"|Раунд - [$round%05d] -------------------------------|\n"
      players.foreach((player: Player) => {
        player.played = true
        players
          .filter((compare: Player) => !compare.name.equals(player.name))
          .foreach((enemy: Player) => {
            roundResult append f"| Игрок [${player.name}%20s - ${player.getRace().name}%10s] атакует [${enemy.name}%20s - ${enemy.getRace().name}%10s] ----------|\n"
            try {
              roundResult append s"${player.attack(enemy)}"
            }
            catch {
              case e: Exception => {
                Loger.info(s"Возникла ошибка ${e.getMessage}")
                e.printStackTrace()
              }
            }
          })
      })
      players foreach((player: Player) => {
        player.played = false
        player.newRound()
      })

      roundResult append s"|-----------------------------------------------|\n"
      Loger.info(roundResult.toString())
      roundResult.clear()
    }

    Loger.info("\n\nКонец битвы !")
    Loger.info("Результаты:")
    dumpPlayers(players)

    // выводим победителя
    Loger.info("Игра окончена!")
  }

  def dumpPlayer(player: Player): Unit = {
    println(player.troopStatus())
  }

  def dumpPlayers(players: List[Player]): Unit = {
    println("|-------------------------------------------------------------------------------------------------------------------------|")
    println(f"|${"Раса"}%15s  | ${"Маг"}%10s | ${"Лучник1"}%10s | ${"Лучник2"}%10s | ${"Лучник3"}%10s | ${"Воин1"}%10s | ${"Воин2"}%10s | ${"Воин3"}%10s | ${"Воин4"}%10s |")
    println("|-----------------|------------|------------|------------|------------|------------|------------|------------|------------|")
    players foreach dumpPlayer
    println("|-------------------------------------------------------------------------------------------------------------------------|")
  }
}

object Game {
  val races: List[Race] = List(new Ork, new Dead, new Human, new Elf)

  def evilRaces(): List[Race] = races.filter((r: Race) => r.isEvil)
  def goodRaces(): List[Race] = races.filter((r: Race) => !r.isEvil)

  def main(args: Array[String]): Unit = {
    Loger.info("Стартуем игру")
    val game: Game = new Game()
    game.gameLoop()
  }
}