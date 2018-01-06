package com.nebiroz.game.activity

import com.nebiroz.game.Game
import com.nebiroz.game.activity.actions.{Action, DowngradePower, SimpleAttack, UpgradePower}
import com.nebiroz.game.activity.army.Pawn
import com.nebiroz.game.activity.exceptions.NoMoreInArmyException
import com.nebiroz.game.activity.race.Race

import scala.util.Random

/**
  * Класс игрока.
  *
  * @param name - имя игрока
  * @param isEvil - добрый или злой игрок
  */
class Player(val name: String, val isEvil: Boolean, val troopNames: List[String]) {

  /**
    * Объявляем расу игрока.
    * Выбираем случайно из списка хороших \ плохих рас
    *
    */
  private val race: Race = {
    if (isEvil)
      Game.evilRaces()(Random.nextInt(Game.evilRaces().size))
    else
      Game.goodRaces()(Random.nextInt(Game.goodRaces().size))
  }
  /**
    * Отряды игрока
    */
  private val troops: List[Troop] = {
    troopNames.collect{
      case name: String => new Troop(name, this.race)
    }
  }

  /**
    * Флаг, означающий ходил игрок или еще нет
    */
  var played: Boolean = false

  /**
    * Команда атаки врага.
    * Выбираем случайно воина и атакуем врага
    *
    * @param enemy - вражеский игрок
    */
  @throws(classOf[NoMoreInArmyException])
  def attack(enemy: Player): String = {
    // инициализируем лог атаки и количество воинов, кто живой еще
    val attackLog = new StringBuilder
    // случайно выбираем отряд для битвы
    val troop = forPlayTroop
    if (troop != null) {
      attackLog append f"| Игрок [${this.name}%20s - ${this.myRace().name}%10s] атакует [${enemy.name}%20s - ${enemy.myRace().name}%10s] ----------|\n"
      // смотрим количество войск в отряде
      val sizeAliveArmy = troop.getCountOfAlive

      // запускаем цикл по количеству живых воинов
      for(number <- 1 to sizeAliveArmy) {
        // берем воина
        val warrior = troop nextWarrior()

        // если вернулся воин, то проводим атаку
        if (warrior != null) {
          // выводим информацию в лог атаки и помечаем, что воин ходил
          attackLog.append(f"| $number Воин [${myRace().name} - ${troop.name}%25s - ${warrior.name}%9s(${warrior.health()}%5s)]")
          warrior.turnPlayOn()

          // достаем активный навык воина
          warrior.action() match {
            case simple: SimpleAttack => {
              // вытаскиваем случайный отряд
              val enemyTroop = enemy.aliveTroop

              if (enemyTroop != null) {
                // вытаскиваем воина из случайного отряда противника
                val enemyWarrior = enemyTroop.warrior()

                if (enemyWarrior != null) {
                  attackLog.append(f" атакует [${enemy.myRace().name} - ${enemyTroop.name}%25s - ${enemyWarrior.name}%9s(${enemyWarrior.health()}%5s)] = ${simple.damage}%4s * ${warrior.power()}%3s == ${simple.damage * warrior.power()}%4s |\n")
                  // проводим атаку
                  enemy.takeDamage(warrior, simple)
                }
                else {
                  attackLog.append("\n")
                }
              }
              else {
                attackLog.append("\n")
              }
            }
            case upgrade: UpgradePower => {
              // достаем воина из своего отряда, в котором и тот, кто апгрейдит
              val myWarrior = troop.warrior()

              if (myWarrior != null) {
                attackLog.append(f" апгрейдит [${myRace().name} - ${myWarrior.name}%10s] |\n")
                // апгрейдим
                myWarrior.upgrade(upgrade.damage)
              }
              else {
                attackLog.append("\n")
              }
            }
            case downgrade: DowngradePower => {
              // вытаскиваем случайный отряд
              val enemyTroop = enemy.aliveTroop

              if (enemyTroop != null) {
                // вытаскиваем воина из случайного отряда противника
                val enemyWarrior = enemyTroop.warrior()

                if (enemyWarrior != null) {
                  attackLog.append(f" даунгрейдит [${enemy.myRace().name} - ${enemyTroop.name}%25s - ${enemyWarrior.name}%10s] |\n")
                  // наводим ему даунгрейд
                  enemyWarrior.downgrade(downgrade.damage)
                }
                else {
                  attackLog.append("\n")
                }
              }
              else {
                attackLog.append("\n")
              }
            }
            case _: Action => {
              // пришел значит не обработанный навык, надо проверять что не так получилось...
              attackLog.append("Пришел какой-то другой навык |\n")
            }
          }
        }
        else {
          attackLog.append("Нет воинов")
        }
      }
    }

    // возвращаем лог атаки
    attackLog.toString()
  }

  /**
    * Принимаем атаку от воина врага.
    * Выбираем случайного воина из случайного, живого отряда и наносим ему урон от воина
    *
    * @param pawn - воин вражеского игрока
    */
  def takeDamage(pawn: Pawn, action: SimpleAttack): Unit = {
    aliveTroop.warrior().takeDamage(action.damage * pawn.power())
    pawn.normalPower()
  }

  /**
    * Доступный отряд, который живой и может ходить.
    *
    * @return - отряд
    */
  def forPlayTroop: Troop = {
    val availableTroops = troops filter((troop: Troop) => troop.isMoreAlive && troop.isMorePlayed) collect {
      case troop: Troop => troop
    }

    if (availableTroops.nonEmpty) {
      availableTroops(Random.nextInt(availableTroops.size))
    }
    else {
      null
    }
  }

  /**
    * Доступный живой отряд
    *
    * @return - живой отряд
    */
  def aliveTroop: Troop = {
    val availableTroops = troops filter((troop: Troop) => troop.isMoreAlive) collect {
      case troop: Troop => troop
    }

    if (availableTroops.nonEmpty) {
      availableTroops(Random.nextInt(availableTroops.size))
    }
    else {
      null
    }
  }

  /**
    * Есть ли отряды, в которых кто-то жив ?
    *
    * @return
    */
  def isAlive: Boolean = {
    troops.count((troop: Troop) => troop.isMoreAlive) > 0
  }

  /**
    * Есть ли отряды, в которых кто-то может ходить ?
    *
    * @return
    */
  def isAnyoneToPlay: Boolean = {
    troops.count((troop: Troop) => troop.isMorePlayed) > 0
  }

  /**
    * Достать свою расу
    *
    * @return - раса игрока
    */
  def myRace(): Race = this.race

  /**
    * Вывести статус каждого отряда отдельно
    *
    * @return - статус всех отрядов в одной строке
    */
  def troopStatus(): String = {
    val status = new StringBuilder("")
    troops.foreach((troop: Troop) => {
      if (status.isEmpty) {
        status.append(f"| ${troop.race.name}%10s | ${troop.name}%25s | ${troop.status()}\n")
      }
      else {
        status.append(f"| ${""}%10s | ${troop.name}%25s | ${troop.status()}")
      }
    })
    status.toString()
  }

  /**
    * Начать новый раунд.
    * Сбрасывает флаги и флаги у отряда
    *
    */
  def newRound(): Unit = {
    played = false
    troops foreach((troop: Troop) => {
      troop.newRound()
    })
  }

  /**
    * Вывести объект в строку
    *
    * @return - объект в строку
    */
  override def toString: String = s"Имя команды[$name]"
}
