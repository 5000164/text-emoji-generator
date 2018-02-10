package jp._5000164.slack_emoji_generator.interfaces

import japgolly.scalajs.react._
import jp._5000164.slack_emoji_generator.CssSettings._
import jp._5000164.slack_emoji_generator.domain.State
import org.scalajs.dom.document

import scala.scalajs.js
import scalacss.ScalaCssReact._

object Application extends App {
  document.body.style.margin = "0"
  document.body.style.padding = "0"

  val content = ScalaComponent.builder[Unit]("content")
    .initialState(State("", "000000"))
    .renderBackend[Backend]
    .build

  Styles.addToDocument()
  content().renderIntoDOM(document.getElementById("root"))

  js.Dynamic.global.document.getElementById("text").focus()
}
