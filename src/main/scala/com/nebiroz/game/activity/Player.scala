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
class Player(val name: String, val isEvil: Boolean) {

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
    * Отряд игрока
    */
  private val troop: Troop = new Troop(this.race)

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
    val sizeAliveArmy = troop.getCountOfAlive

    // запускаем цикл по количеству живых воинов
    for(number <- 1 to sizeAliveArmy) {
      // берем воина
      val warrior = troop nextWarrior()

      // если вернулся воин, то проводим атаку
      if (warrior != null) {
        // выводим информацию в лог атаки и помечаем, что воин ходил
        attackLog.append(f"| $number Воин [${myRace().name} - ${warrior.name}%9s(${warrior.health()}%5s)]")
        warrior.turnPlayOn()

        // достаем активный навык воина
        warrior.action() match {
          case simple: SimpleAttack => {
            // вытаскиваем воина из отряда противника
            val enemyWarrior = enemy.troop.warrior()
            attackLog.append(f" атакует [${enemy.myRace().name} - ${enemyWarrior.name}%9s(${enemyWarrior.health()}%5s)] = ${simple.damage}%4s * ${warrior.power()}%3s == ${simple.damage * warrior.power()}%4s |\n")
            // проводим атаку
            enemy.takeDamage(warrior, simple)
          }
          case upgrade: UpgradePower => {
            // достаем воина из своего отряда
            val myWarrior = troop.warrior()
            attackLog.append(f" апгрейдит [${myRace().name} - ${myWarrior.name}%10s] |\n")
            // апгрейдим
            myWarrior.upgrade(upgrade.damage)
          }
          case downgrade: DowngradePower => {
            // достаем воина из отряда противника
            val enemyWarrior = enemy.troop.warrior()
            attackLog.append(f" даунгрейдит [${enemy.myRace().name} - ${enemyWarrior.name}%10s] |\n")
            // наводим ему даунгрейд
            enemyWarrior.downgrade(downgrade.damage)
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
    // возвращаем лог атаки
    attackLog.toString()
  }

  /**
    * Принимаем атаку от воина врага.
    * Выбираем случайного воина и наносим ему урон от воина
    *
    * @param pawn - воин вражеского игрока
    */
  def takeDamage(pawn: Pawn, action: SimpleAttack): Unit = {
    troop.warrior().takeDamage(action.damage * pawn.power())
    pawn.normalPower()
  }

  /**
    * Есть кто живой в отряде ?
    *
    * @return
    */
  def isAlive: Boolean = troop.isMoreAlive

  /**
    * Может кто еще играть в отряде ?
    *
    * @return
    */
  def isAnyoneToPlay: Boolean = troop.isMorePlayed

  /**
    * Достать свою расу
    *
    * @return - раса игрока
    */
  def myRace(): Race = this.race

  /**
    * Вывести статус отряда
    *
    * @return - статус отряда в одной строке
    */
  def troopStatus(): String = f"| ${troop.race.name}%15s | ${troop.status()}"

  /**
    * Начать новый раунд.
    * Сбрасывает флаги и флаги у отряда
    *
    */
  def newRound(): Unit = {
    played = false
    troop.newRound()
  }

  /**
    * Вывести объект в строку
    *
    * @return - объект в строку
    */
  override def toString: String = s"Имя команды[$name]"
}
