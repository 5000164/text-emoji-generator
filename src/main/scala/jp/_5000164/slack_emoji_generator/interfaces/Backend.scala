package jp._5000164.slack_emoji_generator.interfaces

import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, CallbackTo, ReactEventFromInput}
import jp._5000164.slack_emoji_generator.domain.State

import scalacss.ScalaCssReact._

class Backend($: BackendScope[Unit, State]) {
  def render(state: State): VdomElement = {
    val f = $.zoomState(_.canvas)(value => _.copy(canvas = value))
    <.div(
      <.div(
        <.canvas(^.id := "canvas", Styles.canvas)
      ),
      <.div(
        <.textarea(^.value := state.text, ^.onChange ==> onChange),
        <.button(^.onClick --> Text.save(state), "保存")
      )
    )
  }

  def onChange(e: ReactEventFromInput): CallbackTo[Unit] = {
    val newValue = e.target.value
    $.modState(_.copy(text = newValue))
  }
}
