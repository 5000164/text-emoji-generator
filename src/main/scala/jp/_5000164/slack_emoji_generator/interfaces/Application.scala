package jp._5000164.slack_emoji_generator.interfaces

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import jp._5000164.slack_emoji_generator.CssSettings._
import jp._5000164.slack_emoji_generator.Styles
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html.Canvas

import scala.scalajs.js
import scala.scalajs.js.DynamicImplicits._
import scala.util.Random
import scalacss.ScalaCssReact._

object Application extends App {
  val content = ScalaComponent.builder[Unit]("content")
    .initialState(State(""))
    .renderBackend[Backend]
    .build

  Styles.addToDocument()
  content().renderIntoDOM(document.getElementById("root"))
}

case class State(text: String)

class Backend($: BackendScope[Unit, State]) {
  val colorList = Map(
    "Red" -> "F44336",
    "Pink" -> "E91E63",
    "Purple" -> "9C27B0",
    "Deep Purple" -> "673AB7",
    "Indigo" -> "3F51B5",
    "Blue" -> "2196F3",
    "Light Blue" -> "03A9F4",
    "Cyan" -> "00BCD4",
    "Teal" -> "009688",
    "Green" -> "4CAF50",
    "Light Green" -> "8BC34A",
    "Lime" -> "CDDC39",
    "Yellow" -> "FFEB3B",
    "Amber" -> "FFC107",
    "Orange" -> "FF9800",
    "Deep Orange" -> "FF5722",
    "Brown" -> "795548",
    "Grey" -> "9E9E9E",
    "Blue Grey" -> "607D8B"
  )

  def render(state: State) = <.div(
    <.div(
      <.canvas(^.id := "canvas", Styles.canvas)
    ),
    <.div(
      <.input(^.value := state.text, ^.onChange ==> onChange),
      <.button(^.onClick --> generate(state.text), "生成")
    ),
    <.div(
      <.button(^.onClick --> save, "保存")
    )
  )

  def onChange(e: ReactEventFromInput): CallbackTo[Unit] = {
    val newValue = e.target.value
    $.modState(_.copy(text = newValue))
  }

  def generate(text: String) = Callback {
    val c = document.getElementById("canvas").asInstanceOf[Canvas]
    c.width = 128
    c.height = 128
    type Ctx2D = dom.CanvasRenderingContext2D
    val ctx = c.getContext("2d").asInstanceOf[Ctx2D]
    ctx.font = "bold 64px 'Hiragino Kaku Gothic Pro'"
    ctx.textAlign = "center"
    ctx.textBaseline = "middle"

    val color = Random.shuffle(colorList.values).head
    ctx.fillStyle = s"#$color"

    ctx.fillText(text.charAt(0).toString, 32, 32)
    ctx.fillText(text.charAt(1).toString, 96, 32)
    ctx.fillText(text.charAt(2).toString, 32, 96)
    ctx.fillText(text.charAt(3).toString, 96, 96)
  }

  def save = Callback {
    val c = document.getElementById("canvas").asInstanceOf[Canvas]
    val dialog = js.Dynamic.global.require("electron").remote.dialog
    val option = js.Dynamic.literal("defaultPath" -> "emoji.png")
    val callback = (x: String) => {
      val image = c.toDataURL("image/png").drop("data:image/png;base64,".length)
      val fs = js.Dynamic.global.require("fs")
      fs.writeFile(x, image, js.Dynamic.literal("encoding" -> "base64"), { (err: js.Dynamic) =>
        if (err) println(err)
        else println("saved")
      })
    }
    dialog.showSaveDialog(null, option, callback)
  }
}
