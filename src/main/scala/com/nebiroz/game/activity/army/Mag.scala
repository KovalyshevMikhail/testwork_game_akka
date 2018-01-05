package com.nebiroz.game.activity.army

import com.nebiroz.game.activity.actions.{Action, DowngradePower, SimpleAttack, UpgradePower}
import com.nebiroz.game.activity.race._

/**
  * Общий класс мага.
  *
  * @param magName - имя мага
  * @param magRace - раса
  */
class Mag(val magName: String, val magRace: Race) extends Pawn(magName, magRace) {

  /**
    * Возвращаем должность мага в соответствии с расой.
    *
    * @param race - раса
    * @return - название
    */
  override def name(race: Race): String = race match {
    case elf: Elf => "Маг"
    case hum: Human => "Маг"
    case dead: Dead => "Некромант"
    case ork: Ork => "Шаман"
  }

  /**
    * Возвращаем список активных навыков мага.
    *
    * @param race - раса
    * @return - список навыков
    */
  override def action(race: Race): List[Action] = race match {
    case elf: Elf => List(
      UpgradePower("Наложить улучшение на воина"),
      SimpleAttack("Нанести урон противнику магией", 10.0),
    )
    case hum: Human => List(
      UpgradePower("Наложить улучшение на воина"),
      SimpleAttack("Нанести урон магией по противнику", 4.0),
    )
    case dead: Dead => List(
      DowngradePower("Наслать недуг"),
      SimpleAttack("Магическая атака", 5.0),
    )
    case ork: Ork => List(
      UpgradePower("Наложить улучшение на воина"),
      DowngradePower("Наложить проклятие"),
    )
  }

  /**
    * Возвращаем объект в качестве строки
    *
    * @return - объект, пееведенный в строку
    */
  override def toString: String = s"Раса=${race.name} Тип=${name(race)} Здоровье=${health()} "
}
