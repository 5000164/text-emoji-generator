package jp._5000164.text_emoji_generator.interfaces

import jp._5000164.text_emoji_generator.CssSettings._

import scala.language.postfixOps

object Styles extends StyleSheet.Inline {

  import dsl._

  val noMargin: Styles.dsl.StyleS = mixin(margin(0 px), padding(0 px))

  val defaultBorder: Styles.dsl.StyleS = mixin(
    border(1 px, solid, rgb(238, 238, 238))
  )

  val defaultButton: Styles.dsl.StyleS = mixin(
    height(30 px),
    margin(0 px),
    fontSize(16 px),
    backgroundColor(rgb(242, 242, 242)),
    border.none,
    cursor.pointer
  )

  val background = style(
    width(500 px),
    height(500 px),
    margin(0 px),
    padding(0 px),
    backgroundColor(rgb(238, 238, 238))
  )

  val titleBar = style(height(22 px))
  val wrapper = style(
    display.grid,
    gridTemplateColumns := "1fr 1fr",
    width(400 px),
    height(450 px),
    margin(0 px),
    padding(3 px, 50 px, 25 px),
  )

  val canvasWrapper = style(
    gridColumnStart := "1",
    gridColumnEnd := "3",
    width(260 px),
    margin(20 px, auto, 5 px),
    unsafeChild("textarea::placeholder")(color(rgb(238, 238, 238)))
  )

  val canvas = style(
    display.inlineBlock,
    width(128 px),
    height(128 px),
    noMargin,
    backgroundColor(rgb(255, 255, 255)),
    borderRadius(16 px)
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
    gridColumnStart := "1",
    gridColumnEnd := "3",
    margin(5 px, 0 px),
    textAlign.right
  )

  val saveButton = style(defaultButton)

  val selectColorWrapper = style(
    gridColumnStart := "1",
    gridColumnEnd := "3",
    margin(5 px, 0 px),
    textAlign.right
  )

  val textColor = style(width(100 px), height(28 px), noMargin, defaultBorder)

  val randomButton = style(defaultButton)

  val colorList = style(
    gridColumnStart := "1",
    gridColumnEnd := "3",
    margin(5 px, 0 px),
    padding(0 px),
    fontSize(0 px)
  )

  val colorListItem = style(
    display.inlineBlock,
    width(40 px),
    height(40 px),
    margin(0 px),
    padding(0 px),
    cursor.pointer,
    userSelect := "none"
  )

  val fontFaceSelector =
    style(gridColumnStart := "1", gridColumnEnd := "2", margin(5 px, 0 px))

  val fontFaceButton = style(cursor.pointer)

  val alignSelector =
    style(gridColumnStart := "2", gridColumnEnd := "3", margin(5 px, 0 px))

  val alignButton = style(cursor.pointer)
}
