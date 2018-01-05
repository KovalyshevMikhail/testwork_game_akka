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
    * Вернут имя в соответствии с расой
    *
    * @param race - раса
    * @return - название
    */
  def name(race: Race): String

  /**
    * Вернуть список навыков в соответствии с расой
    *
    * @param race - раса
    * @return - список навыков
    */
  def action(race: Race): List[Action]

  /**
    * Вернуть случайный навык из списка доступных.
    *
    * @return
    */
  def action(): Action = actions(Random.nextInt(actions.size))

  /**
    * Отметить, что игрок не ходил
    *
    */
  def turnPlayOff(): Unit = played = false

  /**
    * Отметить, что игрок ходил
    *
    */
  def turnPlayOn(): Unit = played = true

  /**
    * Ходил ли игрок ?
    *
    * @return - флаг хождения игрока
    */
  def isPlayed: Boolean = played
}