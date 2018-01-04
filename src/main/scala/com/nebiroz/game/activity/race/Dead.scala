package com.nebiroz.game.activity.race
import com.nebiroz.game.activity.army.{Archer, Fighter, Mag}

class Dead extends Race(true, "Мертвые") {
  override def createArcher(name:String): Archer    = new Archer(name, this)
  override def createMag(name:String): Mag          = new Mag(name, this)
  override def createFighter(name:String): Fighter  = new Fighter(name, this)
}
