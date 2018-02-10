package jp._5000164.slack_emoji_generator.interfaces

import jp._5000164.slack_emoji_generator.CssSettings._

import scala.language.postfixOps

object Styles extends StyleSheet.Inline {

  import dsl._

  val noMargin: Styles.dsl.StyleS = mixin(
    margin(0 px),
    padding(0 px)
  )

  val defaultBorder: Styles.dsl.StyleS = mixin(
    border(1 px, solid, rgb(238, 238, 238))
  )

  val defaultButton: Styles.dsl.StyleS = mixin(
    height(30 px),
    margin(5 px),
    fontSize(16 px),
    backgroundColor(rgb(242, 242, 242)),
    border.none,
    cursor.pointer
  )

  val wrapper = style(
    width(300 px),
    height(278 px),
    margin(0 px),
    padding(0 px),
    backgroundColor(rgb(255, 255, 255))
  )

  val titleBar = style(
    height(22 px)
  )

  val canvasWrapper = style(
    width(260 px),
    margin(0 px, auto),
    padding(0 px),
    unsafeChild("textarea::placeholder")(
      color(rgb(238, 238, 238))
    )
  )

  val canvas = style(
    display.inlineBlock,
    width(128 px),
    height(128 px),
    noMargin,
    defaultBorder
  )

  val text = style(
    display.inlineBlock,
    width(128 px),
    height(128 px),
    noMargin,
    fontSize(32 px),
    defaultBorder
  )

  val saveButtonWrapper = style(
    textAlign.right
  )

  val saveButton = style(
    defaultButton
  )

  val selectColorWrapper = style(
    textAlign.right
  )

  val textColor = style(
    width(100 px),
    height(26 px),
    noMargin,
    defaultBorder
  )

  val randomButton = style(
    defaultButton
  )

  val colorList = style(
    margin(0 px),
    padding(0 px),
    fontSize(0 px)
  )

  val colorListItem = style(
    display.inlineBlock,
    width(30 px),
    height(30 px),
    margin(0 px),
    padding(0 px),
    cursor.pointer,
    userSelect := "none"
  )
}
