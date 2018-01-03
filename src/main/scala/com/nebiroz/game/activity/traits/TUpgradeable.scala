package com.nebiroz.game.activity.traits

trait TUpgradeable {
  private var morePower: Double = 100.0

  def upgrade(): Unit = morePower += 50
  def downgrade(): Unit = morePower -= 50
  def normalPower(): Unit = morePower = 100.0
  def getPower(): Double = morePower / 100.0
}
