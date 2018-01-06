package com.nebiroz.game

import com.nebiroz.game.activity.exceptions.{EndGameException, NoMoreInArmyException}

import com.nebiroz.game.activity.{GameLoger, Player}
import com.nebiroz.game.activity.race.{Race, _}

import scala.util.Random

/**
  * Класс игры
  *
  */
class Game {
  /**
    * Количество раундов игры
    *
    */
  private val MAX_ROUND: Int = 35

  /**
    * Игровой цикл игры
    *
    */
  def gameLoop(): Unit = {
    GameLoger.info("Игровой цикл")

    // создаем игроков и сразу выводим инфо по ним
    val players = makePlayers
    dumpPlayers(players)

    GameLoger.info("Да начнется битва!!\n\n")
    val roundResult: StringBuilder = new StringBuilder

    var isEnd = false

    try {
      // запускаем игровой цикл
      for (round <- 1 to MAX_ROUND) {
        roundResult append f"|Раунд - [$round%03d] ---------------------------------------------------------------------------|\n"

        // по порядку выбираем игроков
        players.foreach((player: Player) => {
          // сразу помечаем, что игрок уже ходил
          player.played = true

          // выбираем противника, по которому можно ударить
          val enemies = players
            .filter((compare: Player) => (!compare.name.equals(player.name)) && compare.isAlive)
            .collect {
              case player: Player => player
            }

          try {
            // если противник есть, то бьем его
            if (enemies.nonEmpty) {
              val enemy = enemies(Random.nextInt(enemies.size))
              roundResult append f"| Игрок [${player.name}%20s - ${player.myRace().name}%10s] атакует [${enemy.name}%20s - ${enemy.myRace().name}%10s] ----------|\n"
              roundResult append s"${player.attack(enemy)}"
            }
            // иначе выходим из цикла и заканчиваем игру
            else {
              isEnd = true
            }
          }
          catch {
            case noMoreInTroop: NoMoreInArmyException => {
              isEnd = true
            }
          }
        })

        // проходим по списку игроков и всем сообщаем о новом раунде
        players foreach ((player: Player) => {
          player.newRound()
        })

        // добавляем завершающую строку раунда, выводим и очищаем буфер
        roundResult append s"|---------------------------------------------------------------------------------------------|\n"
        GameLoger.info(roundResult.toString())
        roundResult.clear()

        if (isEnd) {
          throw new EndGameException
        }
      }
    }
    catch {
      case _: EndGameException => {

      }
      case exception: Exception => {
        println(exception.getMessage)
        println(exception.getCause.getMessage)
        exception.printStackTrace()
      }
    }

    // выводим надписи и выводим победителя(ей)
    GameLoger.info("\n\nКонец битвы !\nРезультаты:")
    checkWinner(players)
  }


  /**
    * Метод выводит статистику по отряду у игрока
    *
    * @param players - список игроков, по которым нужно вывести статистику
    */
  def dumpPlayers(players: List[Player]): Unit = {
    println("|-------------------------------------------------------------------------------------------------------------------------|")
    println(f"|${"Раса"}%15s  | ${"Маг"}%10s | ${"Лучник1"}%10s | ${"Лучник2"}%10s | ${"Лучник3"}%10s | ${"Воин1"}%10s | ${"Воин2"}%10s | ${"Воин3"}%10s | ${"Воин4"}%10s |")
    println("|-----------------|------------|------------|------------|------------|------------|------------|------------|------------|")
    players foreach dumpPlayer
    println("|-------------------------------------------------------------------------------------------------------------------------|")
  }

  /**
    * Метод выводит статистику по отряду конкретного игрока
    *
    * @param player - конкретный игрок
    */
  def dumpPlayer(player: Player): Unit = {
    println(player.troopStatus())
  }

  /**
    * Метод проверяет список игроков и ищет тех игроков, у которых еще остались живые в отряде
    *
    * @param players - список проверяемых игроков
    */
  def checkWinner(players: List[Player]): Unit = {
    println("|-------------------------------------------------------------------------------------------------------------------------|")
    println(f"|${"Раса"}%10s  |${"Отряд"}%25s  | ${"Маг"}%10s | ${"Лучник1"}%10s | ${"Лучник2"}%10s | ${"Лучник3"}%10s | ${"Воин1"}%10s | ${"Воин2"}%10s | ${"Воин3"}%10s | ${"Воин4"}%10s |")
    println("|------------|---------------------------|------------|------------|------------|------------|------------|------------|------------|")
    players foreach((player: Player) => {
      if (player.isAlive) {
        println("|----!!!-----WINNER-----!!!-------!!!-----WINNER-----!!!----------------!!!-----WINNER-----!!!-------------------|")
        dumpPlayer(player)
        println("|-------------------------------------------------------------------------------------------------------------------------|")
      }
      else{
        dumpPlayer(player)
      }
    })
    println("|-------------------------------------------------------------------------------------------------------------------------|")
  }

  /**
    * Метод создает список игроков
    *
    * @return - список игроков
    */
  def makePlayers: List[Player] = {
    List[Player](
      new Player("Белые медведи", false, List(
        "Юные орлы",
        "Уничтожители ордена"
      )),
      new Player("Чертовы мертвяки", true, List(
        "Стремительные жильцы",
        "Отвратительные"
      ))
    )
  }
}

/**
  * Объект игры, запускается тут
  *
  */
object Game {
  /**
    * Список доступных рас для игры
    */
  val races: List[Race] = List(new Ork, new Dead, new Human, new Elf)

  /**
    * Метод возвращает список злых рас
    *
    * @return - список злых рас
    */
  def evilRaces(): List[Race] = races.filter((r: Race) => r.isEvil)

  /**
    * Метод возвращает список добрых рас
    *
    * @return - список добрых рас
    */
  def goodRaces(): List[Race] = races.filter((r: Race) => !r.isEvil)

  /**
    * Главная функция запуска игры
    *
    * @param args - список аргументов, передаваемые в игру
    */
  def main(args: Array[String]): Unit = {
    GameLoger.info("Стартуем игру")
    val game: Game = new Game()
    game.gameLoop()
  }
}