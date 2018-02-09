package jp._5000164.slack_emoji_generator.interfaces

import japgolly.scalajs.react.vdom.html_<^.{<, _}
import japgolly.scalajs.react.{BackendScope, Callback, ReactEventFromInput, StateAccessPure}
import jp._5000164.slack_emoji_generator.domain.State
import jp._5000164.slack_emoji_generator.interfaces.Canvas.colorList

import scala.scalajs.js
import scala.util.Random
import scalacss.ScalaCssReact._

class Backend($: BackendScope[Unit, State]) {
  def render(state: State): VdomElement = {
    val f = $.zoomState(_.color)(value => _.copy(color = value))
    <.div(
      <.div(
        <.canvas(^.id := "canvas", Styles.canvas)
      ),
      <.div(
        <.textarea(^.value := state.text, ^.onChange ==> onChangeText(f)),
        <.input(^.value := state.color, ^.onChange ==> onChangeColor(state.text))
      ),
      <.div(
        <.button(^.onClick --> Canvas.save(state.text), "保存")
      ),
      <.div(
        <.ul(Canvas.colorList.toVdomArray({
          case (key, value) => <.li(
            ^.key := key,
            ^.onClick --> onClickColor(state.text, value, f),
            ^.style := js.Dictionary("backgroundColor" -> s"#$value").asInstanceOf[js.Object],
            Styles.colorItem
          )
        }))
      )
    )
  }

  def onChangeText(s: StateAccessPure[String])(e: ReactEventFromInput): Callback = {
    val updatedText = e.target.value
    val color = Random.shuffle(colorList).head._2

    {
      $.modState(_.copy(text = updatedText))
    } >> {
      Canvas.generate(updatedText, color)
    } >> {
      s.setState(color)
    }
  }

  def onChangeColor(text: String)(e: ReactEventFromInput): Callback = {
    val updatedColor = e.target.value
    $.modState(_.copy(color = updatedColor))
  } >> {
    val updatedColor = e.target.value
    Canvas.generate(text, updatedColor)
  }

  def onClickColor(text: String, color: String, s: StateAccessPure[String]): Callback = {
    Canvas.generate(text, color)
  } >> {
    s.setState(color)
  }
}
