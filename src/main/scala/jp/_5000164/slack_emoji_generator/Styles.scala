package jp._5000164.slack_emoji_generator

import jp._5000164.slack_emoji_generator.CssSettings._

import scala.language.postfixOps

object Styles extends StyleSheet.Inline {

  import dsl._

  val canvas = style(
    width(128 px),
    height(128 px)
  )
}
