package com.nebiroz.game.activity.traits

trait THealth {
  private val MAX_HEALTH: Double = 100.0
  private var healthLevel: Double = MAX_HEALTH

  def takeDamage(damage: Double): Unit = {
    if (healthLevel > 0) {
      healthLevel -= damage
    }
    if (healthLevel < 0) {
      healthLevel = 0
    }
  }

  def restoreHealth(): Unit = {
    if (healthLevel > 0) {
      healthLevel = MAX_HEALTH
    }
  }

  def isAlive: Boolean = healthLevel > 0

  def health(): Double = healthLevel
}
