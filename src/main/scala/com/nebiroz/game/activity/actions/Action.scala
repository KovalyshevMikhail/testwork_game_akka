package com.nebiroz.game.activity.actions

/**
  * Общий класс активного навыка
  *
  * @param name - название навыка
  * @param damage - урон, который причинает навык
  */
class Action(val name: String, val damage: Double)

/**
  * Навык простой атаки.
  * Навык просто причиняет урон противнику.
  *
  * @param attackName - название атаки
  * @param attackDamage - причинаемый урон
  */
case class SimpleAttack(attackName: String, attackDamage: Double) extends Action(attackName, attackDamage)

/**
  * Навык апгрейда.
  * Навык увеличивает урон воина на процент, переданный в качестве урона.
  *
  * @param attackName - название навыка
  */
case class UpgradePower(attackName: String) extends Action(attackName, 50.0)

/**
  * Навык даунгрейда.
  * Навык уменьшает урон воина на процент, переданный в качестве урона.
  *
  * @param attackName - название навыка
  */
case class DowngradePower(attackName: String) extends Action(attackName, 50.0)