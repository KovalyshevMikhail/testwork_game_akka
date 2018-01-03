package com.nebiroz.game.activity

import com.nebiroz.game.Game
import com.nebiroz.game.activity.actions.{Action, DowngradePower, SimpleAttack, UpgradePower}
import com.nebiroz.game.activity.army.Pawn
import com.nebiroz.game.activity.race.Race

import scala.util.Random

class Player(val name: String, val isEvil: Boolean) {
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
    * Команда атаки врага.
    * Выбираем случайно воина и атакуем врага
    *
    * @param enemy - вражеский игрок
    */
  def attack(enemy: Player): String = {
    val attackLog = new StringBuilder

    while (isAnyoneToPlay()) {
      troop nextWarrior() match {
        case warrior: Pawn => {
          attackLog.append(f"| Воин [${getRace().name} - ${warrior.name}%10s]")
          warrior.turnPlayOn()

          warrior.getAction() match {
            case simple: SimpleAttack => {
              val enemyWarrior = enemy.troop.warrior()
              attackLog.append(f" атакует [${enemy.getRace().name} - ${enemyWarrior.name}%10s] = ${simple.damage} * ${warrior.getPower()} |\n")
              enemyWarrior.takeDamage(simple.damage * warrior.getPower())
            }
            case _: UpgradePower => {
              val myWarrior = troop.warrior()
              attackLog.append(f" апгрейдит [${getRace().name} - ${myWarrior.name}%10s] |\n")
              myWarrior.upgrade()
            }
            case _: DowngradePower => {
              val enemyWarrior = enemy.troop.warrior()
              attackLog.append(f" даунгрейдит [${enemy.getRace().name} - ${enemyWarrior.name}%10s] |\n")
              enemyWarrior.downgrade()
            }
            case _: Action => {
              attackLog.append("Пришел какой-то другой навык |\n")
            }
          }
        }
        case _ => attackLog.append("Нет воинов")
      }
    }
    attackLog.toString()
  }

  /**
    * Принимаем атаку от воина врага.
    * Выбираем случайного воина и наносим ему урон от воина
    *
    * @param pawn - воин вражеского игрока
    */
  def takeDamage(pawn: Pawn): Unit = {
    troop.warrior().takeDamage(pawn.damage * pawn.getPower())
    pawn.normalPower()
  }

  /**
    * Есть кто живой в отряде ?
    *
    * @return
    */
  def isAlive(): Boolean = troop isMoreAlive()

  def isAnyoneToPlay(): Boolean = troop.isMorePlayed()

  override def toString: String = s"Имя команды[$name]"

  def getRace(): Race = this.race

  def troopStatus(): String = f"| ${troop.race.name}%15s | ${troop.status()}"

  var played: Boolean = false
  def newRound(): Unit = {
    troop.newRound()
  }
}
