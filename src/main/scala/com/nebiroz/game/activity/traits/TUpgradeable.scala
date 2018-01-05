package com.nebiroz.game.activity.traits

/**
  * Трейт, добавляющий функционал апгрейда воина
  *
  */
trait TUpgradeable {
  /**
    * Текущий уровень мощности активного навыка
    *
    */
  private var morePower: Double = 100.0

  /**
    * Добавляем мощности
    *
    * @param power - дополнительная мощность
    */
  def upgrade(power: Double): Unit = morePower += power

  /**
    * Уменьшаем уровень мощности
    *
    * @param power - уровень уменьшения мощности
    */
  def downgrade(power: Double): Unit = morePower -= power

  /**
    * Возвращаем мощность в нормальный уровень
    *
    */
  def normalPower(): Unit = morePower = 100.0

  /**
    * Получить мощность в качестве коэффициента
    *
    * @return - коэффициент мощности
    */
  def power(): Double = morePower / 100.0
}
