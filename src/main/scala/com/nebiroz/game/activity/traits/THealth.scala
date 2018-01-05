package com.nebiroz.game.activity.traits

/**
  * Трейт здоровья.
  * Трейт добавляет функционал по работе со здоровьем.
  *
  */
trait THealth {
  /**
    * Максимальный уровен здоровья
    *
    */
  private val MAX_HEALTH: Double = 100.0

  /**
    * Текущий уровень здоровья
    *
    */
  private var healthLevel: Double = MAX_HEALTH

  /**
    * Принять атаку от противника
    *
    * @param damage - урон
    */
  def takeDamage(damage: Double): Unit = {
    if (healthLevel <= 0) {
      healthLevel = 0
    }
    else {
      healthLevel -= damage
    }
  }

  /**
    * Восстановить уровень здоровья до максимального, если есть здоровье.
    *
    */
  def restoreHealth(): Unit = {
    if (healthLevel > 0) {
      healthLevel = MAX_HEALTH
    }
  }

  /**
    * Жив ли субъект со здоровьем или нет ?
    *
    * @return
    */
  def isAlive: Boolean = healthLevel > 0

  /**
    * Выдать текущий уровень здоровья
    *
    * @return - текущий уровень здоровья
    */
  def health(): Double = healthLevel
}
