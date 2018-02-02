package jp._5000164.slack_emoji_generator.interfaces

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import jp._5000164.slack_emoji_generator.CssSettings._
import jp._5000164.slack_emoji_generator.Styles
import org.scalajs.dom.document

import scalacss.ScalaCssReact._

object Application extends App {
  val hello = ScalaComponent.builder[String]("Hello")
    .render($ => <.div(Styles.content, "Hello ", $.props))
    .build

  Styles.addToDocument()
  hello("World").renderIntoDOM(document.getElementById("root"))
}
