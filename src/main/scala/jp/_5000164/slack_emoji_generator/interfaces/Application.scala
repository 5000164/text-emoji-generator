package jp._5000164.slack_emoji_generator.interfaces

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import jp._5000164.slack_emoji_generator.CssSettings._
import jp._5000164.slack_emoji_generator.Styles
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html.Canvas

import scalacss.ScalaCssReact._

object Application extends App {
  val canvas = ScalaComponent.builder[Unit]("Canvas")
    .renderStatic(<.canvas(Styles.canvas, ^.id := "canvas"))
    .build

  Styles.addToDocument()
  canvas().renderIntoDOM(document.getElementById("root"))

  val c = document.getElementById("canvas").asInstanceOf[Canvas]
  c.width = 128
  c.height = 128
  type Ctx2D = dom.CanvasRenderingContext2D
  val ctx = c.getContext("2d").asInstanceOf[Ctx2D]
  ctx.font = "bold 64px 'Hiragino Kaku Gothic Pro'"
  ctx.textAlign = "center"
  ctx.textBaseline = "middle"
  ctx.fillText("あ", 32, 32)
  ctx.fillText("あ", 96, 32)
  ctx.fillText("あ", 32, 96)
  ctx.fillText("あ", 96, 96)
}
