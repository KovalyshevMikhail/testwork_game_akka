package com.nebiroz.game.activity.actions

import com.nebiroz.game.activity.army.Pawn

class Action(val name: String, val damage: Double) {
  def activate(enemy: Pawn): Unit = {
    enemy.takeDamage(damage)
  }
}

case class SimpleAttack(attackName: String, attackDamage: Double) extends Action(attackName, attackDamage)
case class UpgradePower(attackName: String) extends Action(attackName, 0.0) {
  override def activate(myWarrior: Pawn): Unit = {
    myWarrior.upgrade()
  }
}
case class DowngradePower(attackName: String) extends Action(attackName, 0.0) {
  override def activate(enemy: Pawn): Unit = {
    enemy.downgrade()
  }
}