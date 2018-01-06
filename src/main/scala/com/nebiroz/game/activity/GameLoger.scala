package com.nebiroz.game.activity

import java.io.{File, PrintWriter}
import java.nio.file.Files

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

    println(line)
  }

  def close: Unit = {
    writer.close()
  }
}
