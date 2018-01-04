package com.nebiroz.game

import scala.util.control.Breaks._

import com.nebiroz.game.activity.exceptions.NoMoreInArmyException

import com.nebiroz.game.activity.{Loger, Player}
import com.nebiroz.game.activity.race.{Race, _}

class Game {
  private val MAX_ROUND: Int = 15

  def gameLoop(): Unit = {
    Loger.info("Игровой цикл")

    val players: List[Player] = List(
      new Player("Белые медведи", false),
      new Player("Чертовы мертвяки", true)
    )

    dumpPlayers(players)
    Loger.info("Да начнется битва!!\n\n")
    val roundResult: StringBuilder = new StringBuilder

    try {
      for (round <- 1 to MAX_ROUND) {
        roundResult append f"|Раунд - [$round%03d] ---------------------------------------------------------------------------|\n"
        players.foreach((player: Player) => {
          player.played = true

          val enemies = players
            .filter((compare: Player) => (!compare.name.equals(player.name)) && compare.isAlive)

          if (enemies.nonEmpty) {
            enemies.foreach((enemy: Player) => {
              roundResult append f"| Игрок [${player.name}%20s - ${player.myRace().name}%10s] атакует [${enemy.name}%20s - ${enemy.myRace().name}%10s] ----------|\n"
              roundResult append s"${player.attack(enemy)}"
            })
          }
          else {
            roundResult append s"|---------------------------------------------------------------------------------------------|\n"
            Loger.info(roundResult.toString())
            roundResult.clear()

            break
          }
        })
        players foreach((player: Player) => {
          player.played = false
          player.newRound()
        })

        roundResult append s"|---------------------------------------------------------------------------------------------|\n"
        Loger.info(roundResult.toString())
        roundResult.clear()
      }
    }
    catch {
      case _: NoMoreInArmyException => {
        roundResult append s"|-----------------------------------------------|\n"
        Loger.info(roundResult.toString())
        roundResult.clear()
      }
      case exception: Exception => {
        roundResult append s"|-----------------------------------------------|\n"
        Loger.info(roundResult.toString())
        roundResult.clear()
        Loger.info(exception.getMessage)
        Loger.info(exception.getCause.getMessage)
        exception.printStackTrace()
      }
    }

    Loger.info("\n\nКонец битвы !")
    Loger.info("Результаты:")
    checkWinner(players)

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

  def checkWinner(players: List[Player]): Unit = {
    println("|-------------------------------------------------------------------------------------------------------------------------|")
    println(f"|${"Раса"}%15s  | ${"Маг"}%10s | ${"Лучник1"}%10s | ${"Лучник2"}%10s | ${"Лучник3"}%10s | ${"Воин1"}%10s | ${"Воин2"}%10s | ${"Воин3"}%10s | ${"Воин4"}%10s |")
    println("|-----------------|------------|------------|------------|------------|------------|------------|------------|------------|")
    players foreach((player: Player) => {
      if (player.isAlive) {
        println("|----!!!-----WINNER-----!!!--------------------------------------------------------------------------------------|")
        dumpPlayer(player)
        println("|-------------------------------------------------------------------------------------------------------------------------|")
      }
      else{
        dumpPlayer(player)
      }
    })
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