package jp._5000164.slack_emoji_generator.interfaces

import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ReactEventFromInput}
import jp._5000164.slack_emoji_generator.domain.State

import scala.scalajs.js
import scalacss.ScalaCssReact._

class Backend($: BackendScope[Unit, State]) {
  def render(state: State): VdomElement = {
    <.div(
      <.div(
        <.canvas(^.id := "canvas", Styles.canvas)
      ),
      <.div(
        <.textarea(^.value := state.text, ^.onChange ==> onChange)
      ),
      <.div(
        <.button(^.onClick --> Canvas.save(state.text), "保存")
      ),
      <.div(
        <.ul(Canvas.colorList.toVdomArray({
          case (key, value) => <.li(
            ^.key := key,
            ^.onClick --> Canvas.generateWithColor(state.text, value),
            ^.style := js.Dictionary("backgroundColor" -> s"#$value").asInstanceOf[js.Object],
            Styles.colorItem
          )
        }))
      )
    )
  }

  def onChange(e: ReactEventFromInput): Callback = {
    val updatedText = e.target.value
    $.modState(_.copy(text = updatedText))
  } >> {
    val updatedText = e.target.value
    Canvas.generate(updatedText)
  }
}
