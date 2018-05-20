package jp._5000164.text_emoji_generator.interfaces

import jp._5000164.text_emoji_generator.domain.{Center, Left}
import org.scalatest.FreeSpec

class CanvasSpec extends FreeSpec {
  "左寄せで 2 行で 4 文字の印字位置を計算" in {
    val text = """よん
                 |もじ""".stripMargin
    assert(Canvas.calculatePrintChar(text, Left) === List(
      PrintChar("よ", 32.0, 32.0, 64.0, 64.0),
      PrintChar("ん", 96.0, 32.0, 64.0, 64.0),
      PrintChar("も", 32.0, 96.0, 64.0, 64.0),
      PrintChar("じ", 96.0, 96.0, 64.0, 64.0)
    )
    )
  }

  "左寄せで 2 行で上が 2 文字で下が 3 文字の印字位置を計算" in {
    val text = """した
                 |３文字""".stripMargin
    assert(Canvas.calculatePrintChar(text, Left) === List(
      PrintChar("し", 21.0, 32.0, 42.0, 64.0),
      PrintChar("た", 63.0, 32.0, 42.0, 64.0),
      PrintChar("３", 21.0, 96.0, 42.0, 64.0),
      PrintChar("文", 63.0, 96.0, 42.0, 64.0),
      PrintChar("字", 105.0, 96.0, 42.0, 64.0)
    ))
  }

  "中央寄せで 2 行で上が 2 文字で下が 1 文字の印字位置を計算" in {
    val text = """一二
                 |三""".stripMargin
    assert(Canvas.calculatePrintChar(text, Center) === List(
      PrintChar("一", 32.0, 32.0, 64.0, 64.0),
      PrintChar("二", 96.0, 32.0, 64.0, 64.0),
      PrintChar("三", 64.0, 96.0, 64.0, 64.0)
    ))
  }

  "中央寄せで 2 行で上が 2 文字で下が 3 文字の印字位置を計算" in {
    val text = """一二
                 |三四五""".stripMargin
    assert(Canvas.calculatePrintChar(text, Center) === List(
      PrintChar("一", 43.0, 32.0, 42.0, 64.0),
      PrintChar("二", 85.0, 32.0, 42.0, 64.0),
      PrintChar("三", 22.0, 96.0, 42.0, 64.0),
      PrintChar("四", 64.0, 96.0, 42.0, 64.0),
      PrintChar("五", 106.0, 96.0, 42.0, 64.0)
    ))
  }
}
