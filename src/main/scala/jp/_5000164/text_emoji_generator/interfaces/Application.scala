package jp._5000164.text_emoji_generator.interfaces

import japgolly.scalajs.react._
import jp._5000164.text_emoji_generator.CssSettings._
import jp._5000164.text_emoji_generator.domain.State
import org.scalajs.dom.document

import scala.scalajs.js
import scalacss.ScalaCssReact._

object Application extends App {
  val content = ScalaComponent.builder[Unit]("content")
    .initialState(State("", "000000"))
    .renderBackend[Backend]
    .build

  Styles.addToDocument()
  content().renderIntoDOM(document.getElementById("root"))

  js.Dynamic.global.document.getElementById("text").focus()
}
