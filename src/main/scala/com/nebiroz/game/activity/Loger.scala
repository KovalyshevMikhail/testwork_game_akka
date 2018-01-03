package com.nebiroz.game.activity

object Loger {
  private val log: StringBuilder = new StringBuilder

  def info(line: String): Unit = {
    log append line
    println(line)
  }
}
