package com.nebiroz.game.activity.army

import com.nebiroz.game.activity.actions.{Action, SimpleAttack}
import com.nebiroz.game.activity.race._

class Archer(val archerName: String, val archerRace: Race) extends Pawn(archerName, archerRace) {
  override def name(race: Race): String = race match {
    case elf: Elf => "Лучник"
    case hum: Human => "Арбалетчик"
    case dead: Dead => "Охотник"
    case ork: Ork => "Лучник"
  }

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

  override def toString: String = s"Раса=${race.name} Тип=${name(race)} Здоровье=${health()} "
}
