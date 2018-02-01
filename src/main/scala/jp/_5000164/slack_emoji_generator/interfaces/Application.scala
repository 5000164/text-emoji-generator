package jp._5000164.slack_emoji_generator.interfaces

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.document

object Application extends App {
  val hello = ScalaComponent.builder[String]("Hello")
    .render($ => <.div(^.className := "hello", "Hello ", $.props))
    .build

  hello("World").renderIntoDOM(document.getElementById("root"))
}
