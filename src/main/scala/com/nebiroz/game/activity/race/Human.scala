package com.nebiroz.game.activity.race

import com.nebiroz.game.activity.army.{Archer, Fighter, Mag}

/**
  * Раса Людей.
  * Определяем функции создания каждого типа воинов.
  *
  */
class Human extends Race(false, "Люди") {
  /**
    * Создаем лучника расы Людей.
    *
    * @param name - имя лучника
    * @return - объект лучника
    */
  override def createArcher(name: String): Archer    = new Archer(name, this)

  /**
    * Создаем мага расы Людей.
    *
    * @param name - имя мага
    * @return - объект мага
    */
  override def createMag(name: String): Mag          = new Mag(name, this)

  /**
    * Создаем воина Людей.
    *
    * @param name - имя воина
    * @return - объект воина
    */
  override def createFighter(name: String): Fighter  = new Fighter(name, this)
}
