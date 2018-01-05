package com.nebiroz.game.activity.army

import com.nebiroz.game.activity.actions.{Action, SimpleAttack}
import com.nebiroz.game.activity.race._

/**
  * Общий класс лучника
  *
  * @param archerName - имя лучника
  * @param archerRace - раса
  */
class Archer(val archerName: String, val archerRace: Race) extends Pawn(archerName, archerRace) {

  /**
    * Возвращаем должность лучника в соответствии с расой
    *
    * @param race - раса
    * @return - название
    */
  override def name(race: Race): String = race match {
    case elf: Elf => "Лучник"
    case hum: Human => "Арбалетчик"
    case dead: Dead => "Охотник"
    case ork: Ork => "Лучник"
  }

  /**
    * Возвращаем список активных навыков
    *
    * @param race - раса
    * @return - список навыков
    */
  override def action(race: Race): List[Action] = race match {
    case elf: Elf => List(
      SimpleAttack("Выстрел из лука", 7.0),
      SimpleAttack("Простая атака", 3.0),
    )
    case hum: Human => List(
      SimpleAttack("Выстрел из арбалета", 5.0),
      SimpleAttack("Простая атака", 3.0),
    )
    case dead: Dead => List(
      SimpleAttack("Выстрел из лука", 4.0),
      SimpleAttack("Простая атака", 2.0),
    )
    case ork: Ork => List(
      SimpleAttack("Выстрел из лука", 3.0),
      SimpleAttack("Атака клинком", 2.0),
    )
  }

  /**
    * Возвращаем объект в качестве строки
    *
    * @return - объект, пееведенный в строку
    */
  override def toString: String = s"Раса=${race.name} Тип=${name(race)} Здоровье=${health()} "
}
