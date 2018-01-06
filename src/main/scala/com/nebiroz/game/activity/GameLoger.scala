package com.nebiroz.game.activity

/**
  * Простой объект логера, который просто выводит переданные надписи.
  *
  */
object GameLoger {
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
    println(line)
  }
}
