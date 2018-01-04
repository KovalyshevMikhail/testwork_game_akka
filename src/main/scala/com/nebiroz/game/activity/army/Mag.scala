package com.nebiroz.game.activity.army

import com.nebiroz.game.activity.actions.{Action, DowngradePower, SimpleAttack, UpgradePower}
import com.nebiroz.game.activity.race._

class Mag(val magName: String, val magRace: Race) extends Pawn(magName, magRace) {
  override def name(race: Race): String = race match {
    case elf: Elf => "Маг"
    case hum: Human => "Маг"
    case dead: Dead => "Некромант"
    case ork: Ork => "Шаман"
  }

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

  override def toString: String = s"Раса=${race.name} Тип=${name(race)} Здоровье=${health()} "
}
