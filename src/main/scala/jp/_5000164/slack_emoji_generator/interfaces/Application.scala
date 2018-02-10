package jp._5000164.slack_emoji_generator.interfaces

import japgolly.scalajs.react._
import jp._5000164.slack_emoji_generator.CssSettings._
import jp._5000164.slack_emoji_generator.domain.State
import org.scalajs.dom.document

import scalacss.ScalaCssReact._

object Application extends App {
  val content = ScalaComponent.builder[Unit]("content")
    .initialState(State("", "000000"))
    .renderBackend[Backend]
    .build

  Styles.addToDocument()
  content().renderIntoDOM(document.getElementById("root"))
}
