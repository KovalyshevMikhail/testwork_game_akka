package com.nebiroz.game.activity.race

import com.nebiroz.game.activity.army.{Archer, Fighter, Mag}

class Elf extends Race(false, "Эльфы") {
  override def createArcher(name: String): Archer   = new Archer(name, 2.0, this)
  override def createMag(name: String): Mag         = new Mag(name, 2.0, this)
  override def createFighter(name: String): Fighter = new Fighter(name, 2.0, this)
}
