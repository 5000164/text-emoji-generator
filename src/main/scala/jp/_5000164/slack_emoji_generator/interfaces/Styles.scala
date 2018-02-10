package jp._5000164.slack_emoji_generator.interfaces

import jp._5000164.slack_emoji_generator.CssSettings._

import scala.language.postfixOps

object Styles extends StyleSheet.Inline {

  import dsl._

  val canvas = style(
    width(128 px),
    height(128 px)
  )

  val colorItem = style(
    display.inlineBlock,
    width(20 px),
    height(20 px),
    cursor.pointer,
    userSelect := "none"
  )
}
