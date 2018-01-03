package com.nebiroz.game.activity.army

import com.nebiroz.game.activity.actions.{Action, SimpleAttack}
import com.nebiroz.game.activity.race._

class Fighter(val fighterName: String, val fighterDamage: Double, val fighterRace: Race)
  extends Pawn(fighterName, fighterDamage, fighterRace) {
  override def name(race: Race): String = race match {
    case elf: Elf => "Воин"
    case hum: Human => "Воин"
    case dead: Dead => "Зомби"
    case ork: Ork => "Гоблин"
  }

  override def action(race: Race): List[Action] = race match {
    case elf: Elf => List(
      SimpleAttack("Атака мечом", 15.0),
    )
    case hum: Human => List(
      SimpleAttack("Атака мечом", 18.0),
    )
    case dead: Dead => List(
      SimpleAttack("Удар копьем", 18.0),
    )
    case ork: Ork => List(
      SimpleAttack("Атака дубиной", 20.0),
    )
  }

  override def toString: String = s"Раса=${race.name} Тип=${name(race)} Здоровье=$healthLevel "
}
