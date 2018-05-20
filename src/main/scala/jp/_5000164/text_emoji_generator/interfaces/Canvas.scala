package jp._5000164.text_emoji_generator.interfaces

import japgolly.scalajs.react.Callback
import jp._5000164.text_emoji_generator.domain.{Gothic, State, Text => DomainText}
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html.Canvas

import scala.scalajs.js

object Canvas {
  def get: Canvas = document.getElementById("canvas").asInstanceOf[Canvas]

  def generate(state: State): Callback = Callback {
    val charList = DomainText.calculatePosition(state.text, state.align)
    printChar(charList, state)
  }

  def printChar(charList: List[PrintChar], state: State): Unit = {
    val canvas = get
    canvas.width = 128
    canvas.height = 128
    type Ctx2D = dom.CanvasRenderingContext2D
    val ctx = canvas.getContext("2d").asInstanceOf[Ctx2D]
    ctx.textAlign = "center"
    ctx.textBaseline = "middle"

    val selectedFontFace = if (state.fontFace == Gothic) "Hiragino Kaku Gothic ProN" else "Hiragino Mincho ProN"

    ctx.fillStyle = s"#${state.color}"
    charList.foreach(char => {
      val fontSize = char.height
      ctx.font = s"bold ${fontSize}px '$selectedFontFace'"
      ctx.fillText(char.content, char.x, char.y, char.width)
    })
  }

  def save(text: String) = Callback {
    val canvas = get
    val fileName = text.replace("\n", "")
    val dialog = js.Dynamic.global.require("electron").remote.dialog
    val option = js.Dynamic.literal("defaultPath" -> s"$fileName.png")
    val callback = (x: Any) => {
      if (x.isInstanceOf[String]) {
        val fileName = x.toString
        val image = canvas.toDataURL("image/png").drop("data:image/png;base64,".length)
        val fs = js.Dynamic.global.require("fs")
        fs.writeFile(fileName, image, js.Dynamic.literal("encoding" -> "base64"))
      }
    }
    dialog.showSaveDialog(null, option, callback)
  }
}

case class PrintChar(
                      content: String,
                      x: Double,
                      y: Double,
                      width: Double,
                      height: Double
                    )
