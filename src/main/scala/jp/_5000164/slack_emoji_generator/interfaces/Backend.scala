package jp._5000164.slack_emoji_generator.interfaces

import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ReactEventFromInput, StateAccessPure}
import jp._5000164.slack_emoji_generator.domain.State
import org.scalajs.dom.document
import org.scalajs.dom.html.Canvas

import scalacss.ScalaCssReact._

class Backend($: BackendScope[Unit, State]) {
  def render(state: State): VdomElement = {
    val f = $.zoomState(_.canvas)(value => _.copy(canvas = value))
    <.div(
      <.div(
        <.canvas(^.id := "canvas", Styles.canvas)
      ),
      <.div(
        <.textarea(^.value := state.text, ^.onChange ==> onChange(state.canvas, f)),
        <.button(^.onClick --> Text.save(state), "保存")
      )
    )
  }

  def onChange(canvas: Option[Canvas], s: StateAccessPure[Option[Canvas]])(e: ReactEventFromInput): Callback = {
    val updatedText = e.target.value
    $.modState(_.copy(text = updatedText))
  } >> {
    val updatedText = e.target.value
    Text.generate(canvas.getOrElse(document.getElementById("canvas").asInstanceOf[Canvas]), updatedText, s)
  }
}
