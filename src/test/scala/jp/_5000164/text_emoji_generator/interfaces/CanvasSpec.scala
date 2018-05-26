package jp._5000164.text_emoji_generator.interfaces

import jp._5000164.text_emoji_generator.domain.{Center, Left}
import org.scalatest.FreeSpec

class CanvasSpec extends FreeSpec {
  "左寄せで 2 行で 4 文字の印字位置を計算" in {
    val text = """よん
                 |もじ""".stripMargin
    assert(Canvas.calculatePrintChar(text, Left) === Seq(
      PrintChar("よ", 32.0, 32.0, 64.0, 64.0),
      PrintChar("ん", 96.0, 32.0, 64.0, 64.0),
      PrintChar("も", 32.0, 96.0, 64.0, 64.0),
      PrintChar("じ", 96.0, 96.0, 64.0, 64.0)))
  }

  "左寄せで 2 行で上が 2 文字で下が 3 文字の印字位置を計算" in {
    val text = """した
                 |３文字""".stripMargin
    assert(Canvas.calculatePrintChar(text, Left) === Seq(
      PrintChar("し", 21.333333333333332, 32.0, 42.666666666666664, 64.0),
      PrintChar("た", 64.0, 32.0, 42.666666666666664, 64.0),
      PrintChar("３", 21.333333333333332, 96.0, 42.666666666666664, 64.0),
      PrintChar("文", 64.0, 96.0, 42.666666666666664, 64.0),
      PrintChar("字", 106.66666666666667, 96.0, 42.666666666666664, 64.0)))
  }

  "中央寄せで 2 行で上が 2 文字で下が 1 文字の印字位置を計算" in {
    val text = """一二
                 |三""".stripMargin
    assert(Canvas.calculatePrintChar(text, Center) === Seq(
      PrintChar("一", 32.0, 32.0, 64.0, 64.0),
      PrintChar("二", 96.0, 32.0, 64.0, 64.0),
      PrintChar("三", 64.0, 96.0, 64.0, 64.0)))
  }

  "中央寄せで 2 行で上が 2 文字で下が 3 文字の印字位置を計算" in {
    val text = """一二
                 |三四五""".stripMargin
    assert(Canvas.calculatePrintChar(text, Center) === Seq(
      PrintChar("一", 42.66666666666667, 32.0, 42.666666666666664, 64.0),
      PrintChar("二", 85.33333333333333, 32.0, 42.666666666666664, 64.0),
      PrintChar("三", 21.333333333333332, 96.0, 42.666666666666664, 64.0),
      PrintChar("四", 64.0, 96.0, 42.666666666666664, 64.0),
      PrintChar("五", 106.66666666666667, 96.0, 42.666666666666664, 64.0)))
  }

  "[]で囲んだ部分は 1 文字分の幅になる" in {
    val text = """かこ
                 |む[!!]""".stripMargin
    assert(Canvas.calculatePrintChar(text, Left) === Seq(
      PrintChar("か", 32.0, 32.0, 64.0, 64.0),
      PrintChar("こ", 96.0, 32.0, 64.0, 64.0),
      PrintChar("む", 32.0, 96.0, 64.0, 64.0),
      PrintChar("!", 80.0, 96.0, 32.0, 64.0),
      PrintChar("!", 112.0, 96.0, 32.0, 64.0)))
  }

  "[]で囲んだ部分は 3 文字でも 1 文字分の幅になる" in {
    val text = """かこ
                 |む[!!!]""".stripMargin
    assert(Canvas.calculatePrintChar(text, Left) === Seq(
      PrintChar("か", 32.0, 32.0, 64.0, 64.0),
      PrintChar("こ", 96.0, 32.0, 64.0, 64.0),
      PrintChar("む", 32.0, 96.0, 64.0, 64.0),
      PrintChar("!", 74.66666666666666, 96.0, 21.333333333333332, 64.0),
      PrintChar("!", 95.99999999999999, 96.0, 21.333333333333332, 64.0),
      PrintChar("!", 117.33333333333331, 96.0, 21.333333333333332, 64.0)))
  }

  "[]は行の先頭でも機能する" in {
    val text = "[!!]!"
    assert(Canvas.calculatePrintChar(text, Left) === Seq(
      PrintChar("!", 16.0, 64.0, 32.0, 128.0),
      PrintChar("!", 48.0, 64.0, 32.0, 128.0),
      PrintChar("!", 96.0, 64.0, 64.0, 128.0)))
  }

  "[]は行の末尾でも機能する" in {
    val text = "![!!]"
    assert(Canvas.calculatePrintChar(text, Left) === Seq(
      PrintChar("!", 32.0, 64.0, 64.0, 128.0),
      PrintChar("!", 80.0, 64.0, 32.0, 128.0),
      PrintChar("!", 112.0, 64.0, 32.0, 128.0)))
  }
}
