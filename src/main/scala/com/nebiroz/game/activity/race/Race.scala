package com.nebiroz.game.activity.race

import com.nebiroz.game.activity.army.{Archer, Fighter, Mag, Pawn}

/**
  * Общий класс расы.
  *
  * @param isEvil - добрая или злая раса
  * @param name - название расы
  */
abstract class Race(val isEvil: Boolean, val name: String) {
  /**
    * Создаем лучника
    *
    * @param name - имя лучника
    * @return - объект лучника
    */
  def createArcher(name: String): Archer

  /**
    * Создаем мага
    *
    * @param name - имя мага
    * @return - объект мага
    */
  def createMag(name: String): Mag

  /**
    * Создаем воина
    *
    * @param name - имя воина
    * @return - объект воина
    */
  def createFighter(name: String): Fighter
}
