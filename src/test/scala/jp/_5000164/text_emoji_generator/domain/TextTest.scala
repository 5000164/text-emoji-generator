package jp._5000164.text_emoji_generator.domain

import org.scalatest.FreeSpec

class TextTest extends FreeSpec {
  "左寄せで 2 行で 4 文字の印字位置を計算" in {
    assert(Text.calculatePosition(List("よん", "もじ"), Left) == List(
      PrintChar("よ", 32.0, 32.0, 64.0),
      PrintChar("ん", 96.0, 32.0, 64.0),
      PrintChar("も", 32.0, 96.0, 64.0),
      PrintChar("じ", 96.0, 96.0, 64.0)
    ))
  }

  "左寄せで 2 行で上が 2 文字で下が 3 文字の印字位置を計算" in {
    assert(Text.calculatePosition(List("した", "３文字"), Left) == List(
      PrintChar("し", 21.0, 32.0, 42.0),
      PrintChar("た", 63.0, 32.0, 42.0),
      PrintChar("３", 21.0, 96.0, 42.0),
      PrintChar("文", 63.0, 96.0, 42.0),
      PrintChar("字", 105.0, 96.0, 42.0)
    ))
  }

  "中央寄せで 2 行で上が 2 文字で下が 1 文字の印字位置を計算" in {
    assert(Text.calculatePosition(List("一二", "三"), Center) == List(
      PrintChar("一", 32.0, 32.0, 64.0),
      PrintChar("二", 96.0, 32.0, 64.0),
      PrintChar("三", 64.0, 96.0, 64.0)
    ))
  }

  "中央寄せで 2 行で上が 2 文字で下が 3 文字の印字位置を計算" in {
    assert(Text.calculatePosition(List("一二", "三四五"), Center) == List(
      PrintChar("一", 43.0, 32.0, 42.0),
      PrintChar("二", 85.0, 32.0, 42.0),
      PrintChar("三", 22.0, 96.0, 42.0),
      PrintChar("四", 64.0, 96.0, 42.0),
      PrintChar("五", 106.0, 96.0, 42.0)
    ))
  }

  "2 行の時フォントサイズは 64" in {
    assert(Text.calculateFontSize(List("２", "行")) == 64)
  }

  "1 行の時フォントサイズは 128" in {
    assert(Text.calculateFontSize(List("１行")) == 128)
  }

  "3 行の時フォントサイズは 42" in {
    assert(Text.calculateFontSize(List("さ", "ん", "行")) == 42)
  }
}
