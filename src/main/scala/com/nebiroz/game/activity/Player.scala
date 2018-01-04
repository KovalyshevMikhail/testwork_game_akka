package com.nebiroz.game.activity

import com.nebiroz.game.Game
import com.nebiroz.game.activity.actions.{Action, DowngradePower, SimpleAttack, UpgradePower}
import com.nebiroz.game.activity.army.Pawn
import com.nebiroz.game.activity.exceptions.NoMoreInArmyException
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
  @throws(classOf[NoMoreInArmyException])
  def attack(enemy: Player): String = {
    val attackLog = new StringBuilder
    val sizeAliveArmy = troop.getCountOfAlive

    for(number <- 1 to sizeAliveArmy) {
      troop nextWarrior() match {
        case warrior: Pawn => {
          attackLog.append(f"| $number Воин [${myRace().name} - ${warrior.name}%9s(${warrior.health()}%5s)]")
          warrior.turnPlayOn()

          warrior.getAction() match {
            case simple: SimpleAttack => {
              val enemyWarrior = enemy.troop.warrior()
              attackLog.append(f" атакует [${enemy.myRace().name} - ${enemyWarrior.name}%9s(${enemyWarrior.health()}%5s)] = ${simple.damage}%4s * ${warrior.getPower()}%3s == ${simple.damage * warrior.getPower()}%4s |\n")
              enemy.takeDamage(warrior, simple)
            }
            case upgrade: UpgradePower => {
              val myWarrior = troop.warrior()
              attackLog.append(f" апгрейдит [${myRace().name} - ${myWarrior.name}%10s] |\n")
              myWarrior.upgrade(upgrade.damage)
            }
            case downgrade: DowngradePower => {
              val enemyWarrior = enemy.troop.warrior()
              attackLog.append(f" даунгрейдит [${enemy.myRace().name} - ${enemyWarrior.name}%10s] |\n")
              enemyWarrior.downgrade(downgrade.damage)
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
  def takeDamage(pawn: Pawn, action: SimpleAttack): Unit = {
    troop.warrior().takeDamage(action.damage * pawn.getPower())
    pawn.normalPower()
  }

  /**
    * Есть кто живой в отряде ?
    *
    * @return
    */
  def isAlive: Boolean = troop.isMoreAlive

  def isAnyoneToPlay: Boolean = troop.isMorePlayed

  override def toString: String = s"Имя команды[$name]"

  def myRace(): Race = this.race

  def troopStatus(): String = f"| ${troop.race.name}%15s | ${troop.status()}"

  var played: Boolean = false
  def newRound(): Unit = {
    troop.newRound()
  }
}
