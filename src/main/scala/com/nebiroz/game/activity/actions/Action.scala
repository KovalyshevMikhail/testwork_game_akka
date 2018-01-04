package com.nebiroz.game.activity.actions

class Action(val name: String, val damage: Double)

case class SimpleAttack(attackName: String, attackDamage: Double) extends Action(attackName, attackDamage)
case class UpgradePower(attackName: String) extends Action(attackName, 50.0)
case class DowngradePower(attackName: String) extends Action(attackName, 50.0)