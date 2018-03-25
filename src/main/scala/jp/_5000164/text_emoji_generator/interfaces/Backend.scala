package jp._5000164.text_emoji_generator.interfaces

import japgolly.scalajs.react.vdom.html_<^.{<, _}
import japgolly.scalajs.react.{BackendScope, Callback, ReactEventFromInput, StateAccessPure}
import jp._5000164.text_emoji_generator.domain.{FontFace, Gothic, Mincho, State}
import jp._5000164.text_emoji_generator.interfaces.Canvas.colorList
import scalacss.ScalaCssReact._

import scala.scalajs.js
import scala.util.Random

class Backend($: BackendScope[Unit, State]) {
  def render(state: State): VdomElement = {
    val f = $.zoomState(_.color)(value => _.copy(color = value))
    <.div(
      Styles.background,
      <.div(Styles.titleBar),
      <.div(
        Styles.wrapper,
        <.div(
          Styles.canvasWrapper,
          <.textarea(
            ^.id := "text",
            ^.value := state.text,
            ^.placeholder := "ここに入力",
            ^.onChange ==> onChangeText(state.fontFace, f),
            Styles.text,
            if (state.fontFace == Gothic) ^.fontFamily := "Hiragino Kaku Gothic ProN" else ^.fontFamily := "Hiragino Mincho ProN"
          ),
          <.canvas(^.id := "canvas", Styles.canvas)
        ),
        <.div(
          Styles.saveButtonWrapper,
          <.button("保存", ^.onClick --> Canvas.save(state.text), Styles.saveButton)
        ),
        <.div(
          Styles.selectColorWrapper,
          <.input(^.value := state.color, ^.onChange ==> onChangeColor(state.text, state.fontFace), Styles.textColor),
          <.button("色をランダムで選択", ^.onClick --> onClickRandomColor(state.text, state.fontFace, f), Styles.randomButton)
        ),
        <.div(
          <.ul(
            Styles.colorList,
            Canvas.colorList.toVdomArray({
              case (key, value) => <.li(
                ^.key := key,
                ^.onClick --> onClickColor(state.text, value, state.fontFace, f),
                ^.style := js.Dictionary("backgroundColor" -> s"#$value").asInstanceOf[js.Object],
                Styles.colorListItem
              )
            })
          )
        ),
        <.div(
          Styles.fontFaceSelector,
          <.div("書体選択"),
          <.div(
            <.label(
              <.input.radio(^.name := "type-face", ^.value := "gothic", ^.checked := state.fontFace == Gothic, ^.onChange ==> onClickFontFace(state.text, state.color, Gothic)),
              "ゴシック体"
            )
          ),
          <.div(
            <.label(
              <.input.radio(^.name := "type-face", ^.value := "mincho", ^.checked := state.fontFace == Mincho, ^.onChange ==> onClickFontFace(state.text, state.color, Mincho)),
              "明朝体"
            )
          )
        )
      )
    )
  }

  def onChangeText(fontFace: FontFace, s: StateAccessPure[String])(e: ReactEventFromInput): Callback = {
    val updatedText = e.target.value
    val color = Random.shuffle(colorList).head._2

    {
      $.modState(_.copy(text = updatedText))
    } >> {
      Canvas.generate(updatedText, color, fontFace)
    } >> {
      s.setState(color)
    }
  }

  def onChangeColor(text: String, fontFace: FontFace)(e: ReactEventFromInput): Callback = {
    val updatedColor = e.target.value
    $.modState(_.copy(color = updatedColor))
  } >> {
    val updatedColor = e.target.value
    Canvas.generate(text, updatedColor, fontFace)
  }

  def onClickColor(text: String, color: String, fontFace: FontFace, s: StateAccessPure[String]): Callback = {
    Canvas.generate(text, color, fontFace)
  } >> {
    s.setState(color)
  }

  def onClickRandomColor(text: String, fontFace: FontFace, s: StateAccessPure[String]): Callback = {
    val color = Random.shuffle(colorList).head._2

    {
      Canvas.generate(text, color, fontFace)
    } >> {
      s.setState(color)
    }
  }

  def onClickFontFace(text: String, color: String, fontFace: FontFace)(e: ReactEventFromInput): Callback = {
    $.modState(_.copy(fontFace = fontFace))
  } >> {
    Canvas.generate(text, color, fontFace)
  }
}
