package jp._5000164.text_emoji_generator.domain

import org.scalatest.FreeSpec

class TextTest extends FreeSpec {
  "1 行の時フォントサイズは 128" in {
    assert(Text.calculateFontSize(List("１行")) === 128)
  }

  "3 行の時フォントサイズは 42" in {
    assert(Text.calculateFontSize(List("さ", "ん", "行")) === 42)
  }
}
