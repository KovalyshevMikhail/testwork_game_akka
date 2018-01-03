package com.nebiroz.game.activity.race

import com.nebiroz.game.activity.army.{Archer, Fighter, Mag, Pawn}

abstract class Race(val isEvil: Boolean, val name: String) {
  def createArcher(name:String): Archer
  def createMag(name:String): Mag
  def createFighter(name:String): Fighter
}
