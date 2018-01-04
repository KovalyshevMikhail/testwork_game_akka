package com.nebiroz.game.activity.army

import com.nebiroz.game.activity.actions.Action
import com.nebiroz.game.activity.race.Race
import com.nebiroz.game.activity.traits.{THealth, TUpgradeable}

import scala.util.Random

/**
* Любой игральный объект, который атакует
*/
abstract class Pawn(val name: String, val race: Race) extends THealth with TUpgradeable {
  /**
    * Список навыков игрового НПС
    */
  private val actions: List[Action] = action(race)
  /**
    * Флаг, показывающий ходил ли игрок
    */
  private var played: Boolean = false

  /**
    *
    * @param race
    * @return
    */
  def name(race: Race): String
  def action(race: Race): List[Action]
  def getAction(): Action = actions(Random.nextInt(actions.size))

  def turnPlayOff(): Unit = played = false
  def turnPlayOn(): Unit = played = true
  def isPlayed: Boolean = played
}

object Pawn extends Enumeration {
  val Archer, Mag, Fighter = Value
}