package jp._5000164.text_emoji_generator.interfaces

import japgolly.scalajs.react.Callback
import jp._5000164.text_emoji_generator.domain.{
  Align,
  CharPosition,
  CharSize,
  Gothic,
  RichChar,
  State,
  Text => DomainText
}
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html.Canvas

import scala.scalajs.js

object Canvas {
  def get: Canvas = document.getElementById("canvas").asInstanceOf[Canvas]

  def generate(state: State): Callback = Callback {
    val printCharList = calculatePrintChar(state.text, state.align)
    printChar(printCharList, state)
  }

  /**
    * 表示するために計算する。
    *
    * @param text  入力された内容
    * @param align 文字の位置揃え
    * @return 表示用の情報
    */
  def calculatePrintChar(text: String, align: Align): Seq[PrintChar] = {
    val (charMatrix, charSizeMatrix, charPositionMatrix) =
      DomainText.calculatePosition(text, align)
    toPrintChar(charMatrix, charSizeMatrix, charPositionMatrix)
  }

  /**
    * 表示用のデータ構造に変換する。
    *
    * @param charMatrix         解析した文字
    * @param charSizeMatrix     文字ごとの大きさのマトリックス
    * @param charPositionMatrix 文字ごとの位置のマトリックス
    * @return 表示用のデータ
    */
  private def toPrintChar(
    charMatrix: Seq[Seq[RichChar]],
    charSizeMatrix: Seq[Seq[CharSize]],
    charPositionMatrix: Seq[Seq[CharPosition]]
  ): Seq[PrintChar] =
    (for ((charList, rowIndex) <- charMatrix.zipWithIndex) yield {
      for ((char, columnIndex) <- charList.zipWithIndex) yield {
        PrintChar(
          char.char.toString,
          charPositionMatrix(rowIndex)(columnIndex).x,
          charPositionMatrix(rowIndex)(columnIndex).y,
          charSizeMatrix(rowIndex)(columnIndex).width,
          charSizeMatrix(rowIndex)(columnIndex).height
        )
      }
    }).flatten

  def printChar(charList: Seq[PrintChar], state: State): Unit = {
    val canvas = get
    canvas.width = 128
    canvas.height = 128
    type Ctx2D = dom.CanvasRenderingContext2D
    val ctx = canvas.getContext("2d").asInstanceOf[Ctx2D]

    ctx.strokeStyle = "rgb(255, 255, 255)"
    ctx.lineJoin = "round"
    ctx.lineWidth = 32
    ctx.strokeRect(16, 16, 96, 96)
    ctx.fillStyle = "rgb(255, 255, 255)"
    ctx.fillRect(16, 16, 96, 96)

    ctx.textAlign = "center"
    ctx.textBaseline = "middle"

    val selectedFontFace =
      if (state.fontFace == Gothic) "Hiragino Sans" else "Hiragino Mincho ProN"

    ctx.fillStyle = s"#${state.color}"
    charList.foreach(char => {
      val fontSize = char.height
      ctx.font = s"bold ${fontSize}px '$selectedFontFace'"
      ctx.fillText(char.content, char.x, char.y, char.width)
    })
  }

  def save(text: String): Callback = Callback {
    val canvas = get
    val fileName = text.replace("\n", "")
    val image =
      canvas.toDataURL("image/png").drop("data:image/png;base64,".length)
    dom.window
      .asInstanceOf[js.Dynamic]
      .electron
      .save(js.Dynamic.literal("fileName" -> fileName, "data" -> image))
  }
}

case class PrintChar(content: String,
                     x: Double,
                     y: Double,
                     width: Double,
                     height: Double)
