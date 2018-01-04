package com.nebiroz.game.activity

import com.nebiroz.game.activity.army.Pawn
import com.nebiroz.game.activity.exceptions.NoMoreInArmyException
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
  @throws(classOf[NoMoreInArmyException])
  def warrior(): Pawn = {
    val localTroop = pawns filter((pawn: Pawn) => pawn.isAlive) collect {
      case p: Pawn => p
    }
    if (localTroop.isEmpty) {
      throw new NoMoreInArmyException("У врага нет войск")
    }
    localTroop(Random.nextInt(localTroop.size))
  }

  /**
    * Метод возвращает следующего воина в отряде.
    * Если есть живые и те, кто могут ходить, то выбираем.
    * Выбираем сначала тех, кто проапргреден, и выбираем случайного воина из них.
    * Выбираем затем из остальных.
    *
    * @return
    */
  def nextWarrior(): Any = {
    val morePower = pawns filter((pawn: Pawn) => (!pawn.isPlayed) && pawn.getPower() > 1.0) collect {
      case p: Pawn => p
    }
    if (morePower.nonEmpty) {
      morePower(Random.nextInt(morePower.size))
    }
    else {
      val whoCan = pawns filter((pawn: Pawn) => (!pawn.isPlayed) && pawn.isAlive) collect {
        case p: Pawn => p
      }
      if (whoCan.nonEmpty) {
        whoCan(Random.nextInt(whoCan.size))
      }
      else {
        false
      }
    }
  }

  /**
    * Проверяем есть ли живые в отряде
    *
    * @return - есть или нет
    */
  def isMoreAlive: Boolean = pawns.count((p: Pawn) => p.isAlive) > 0

  /**
    * Метод начинает новый раунд.
    * Если воин жив - то ему сбрасывается флаг ходьбы
    * Если воин мертв - то флаг наоборот, взводим, чтобы он не мог ходить
    *
    */
  def newRound(): Unit = {
    pawns foreach((p: Pawn) => {
      if (p.isAlive) {
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
  def isMorePlayed: Boolean = pawns.count((p: Pawn) => !p.isPlayed) > 0

  def getCountOfAlive: Int = pawns.count((p: Pawn) => p.isAlive)

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
