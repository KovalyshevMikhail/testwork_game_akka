package com.nebiroz.game.activity

import com.nebiroz.game.activity.army.{Archer, Fighter, Mag, Pawn}
import com.nebiroz.game.activity.race.Race

import scala.util.Random

class Troop(val race: Race) {
  // главный констуктор. надо создать отряд
  private val pawns: List[Pawn] = makeDefaultArmy()

  /**
    * Создаем отряд по-умолчанию
    *  - 1 маг
    *  - 3 лучника
    *  - 4 бойца
    *
    * @return
    */
  def makeDefaultArmy(): List[Pawn] = {
    List(
      race.createMag("Маг 1"),

      race.createArcher("Лучник 1"),
      race.createArcher("Лучник 2"),
      race.createArcher("Лучник 3"),

      race.createFighter("Воин 1"),
      race.createFighter("Воин 2"),
      race.createFighter("Воин 3"),
      race.createFighter("Воин 4")
    )
  }

  /**
    * Возвращаем случайного воина из отряда
    *
    * @return - случайный воин
    */
  def warrior(): Pawn = {
    pawns(Random.nextInt(pawns.size))
  }

  def nextWarrior(): Any = {
    if (isMoreAlive() && isMorePlayed()) {
      val whoCan = pawns filter((pawn: Pawn) => !pawn.isPlayed()) collect {
        case p: Pawn => p
      }
      whoCan(Random.nextInt(whoCan.size))
    }
    else {
      false
    }
  }

  /**
    * Проверяем есть ли живые в отряде
    *
    * @return - есть или нет
    */
  def isMoreAlive(): Boolean = pawns.count((p: Pawn) => p isAlive()) > 0

  /**
    * Метод начинает новый раунд.
    * Если воин жив - то ему сбрасывается флаг ходьбы
    * Если воин мертв - то флаг наоборот, взводим, чтобы он не мог ходить
    *
    */
  def newRound(): Unit = {
    pawns foreach((p: Pawn) => {
      if (p.isAlive()) {
        p.turnPlayOff()
      }
      else {
        p.turnPlayOn()
      }
    })
  }

  /**
    * Проверяем есть ли живые в отряде
    *
    * @return - есть или нет
    */
  def isMorePlayed(): Boolean = pawns.count((p: Pawn) => !p.isPlayed()) > 0

  /**
    * Возращаем статус по отряду
    *
    * @return - статус отряда
    */
  def status(): String = {
    val status: StringBuilder = new StringBuilder

    pawns foreach((p: Pawn) => {
      status append f"${p.health()}%10s"
      status append " | "
    })

    status toString()
  }

}
