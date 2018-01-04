package com.nebiroz.game.activity.traits

trait TUpgradeable {
  private var morePower: Double = 100.0

  def upgrade(power: Double): Unit = morePower += power
  def downgrade(power: Double): Unit = morePower -= power
  def normalPower(): Unit = morePower = 100.0
  def getPower(): Double = morePower / 100.0
}
