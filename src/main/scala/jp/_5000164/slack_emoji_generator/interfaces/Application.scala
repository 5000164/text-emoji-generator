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
import scalacss.ScalaCssReact._

object Application extends App {
  val content = ScalaComponent.builder[Unit]("content")
    .renderStatic(<.div(
      <.div(
        <.canvas(^.id := "canvas", Styles.canvas)
      ),
      <.div(
        <.input(),
        <.button(^.onClick --> generate, "生成")
      ),
      <.div(
        <.button(^.onClick --> save, "保存")
      )
    ))
    .build

  Styles.addToDocument()
  content().renderIntoDOM(document.getElementById("root"))

  val c = document.getElementById("canvas").asInstanceOf[Canvas]
  c.width = 128
  c.height = 128
  type Ctx2D = dom.CanvasRenderingContext2D
  val ctx = c.getContext("2d").asInstanceOf[Ctx2D]
  ctx.font = "bold 64px 'Hiragino Kaku Gothic Pro'"
  ctx.textAlign = "center"
  ctx.textBaseline = "middle"

  def generate = Callback {
    ctx.fillText("あ", 32, 32)
    ctx.fillText("あ", 96, 32)
    ctx.fillText("あ", 32, 96)
    ctx.fillText("あ", 96, 96)

  }

  def save = Callback {
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
