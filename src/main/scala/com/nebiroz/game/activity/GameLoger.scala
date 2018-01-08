package com.nebiroz.game.activity

import java.io.{File, PrintWriter}
import java.nio.file.Files

import com.nebiroz.game.activity.actions.SimpleAttack
import com.nebiroz.game.activity.army.Pawn

import scala.io.Source

/**
  * Простой объект логера, который просто выводит переданные надписи.
  *
  */
object GameLoger {
  val directoryPath = new File("./game/log/full_log.log")
  directoryPath.getParentFile.mkdirs()
  val writer = new PrintWriter(directoryPath)

  /**
    * Общий буфер лога.
    */
  private val log: StringBuilder = new StringBuilder

  /**
    * Добавляем информацию к буферу
    *
    * @param line - информация
    */
  def info(line: String): Unit = {
    log append line

    writer.write(line)

    print(line)
  }

  def beginRound(round: Int): Unit = {
    info(f"\n|Раунд - [$round%03d] ---------------------------------------------------------------------------|\n")
  }

  def end(): GameLoger.type = {
    info("|-------------------------------------------------------------------------------------------------------------------------|")
    this
  }

  def attackPlayer(playerName: String, playerRace: String, enemyName: String, enemyRace: String): GameLoger.type = {
    info(f"| Игрок [$playerName%10s - $playerRace%7s] атакует [$enemyName%10s - $enemyRace%7s] ----------|")
    this
  }

  def attackBegin(number: Int): GameLoger.type = {
    info(f"| $number Воин ")
    this
  }

  def attackWarrior(raceName: String, troopName: String, warrior: Pawn): GameLoger.type = {
    info(f"[$raceName%7s - $troopName%10s - ${warrior.name}%9s(${warrior.health()}%5s)]")
    this
  }

  def attackTo(): GameLoger.type = {
    info(" атакует ")
    this
  }

  def attackSimple(action: SimpleAttack, warrior: Pawn): GameLoger.type = {
    info(f"= ${action.damage}%4s * ${warrior.power()}%3s == ${action.damage * warrior.power()}%4s |")
    this
  }

  def attackUpgrade(raceName: String, warriorName: String): GameLoger.type = {
    info(f" апгрейдит [$raceName%7s - $warriorName%10s] |")
    this
  }

  def attackDowngrade(raceName: String, troopName: String, warriorName: String): GameLoger.type = {
    info(f" даунгрейдит [$raceName%7s - $troopName%10s - $warriorName%10s] |")
    this
  }

  def endLine(): GameLoger.type = {
    info("\n")
    this
  }

  def close(): Unit = {
    writer.close()
  }
}
