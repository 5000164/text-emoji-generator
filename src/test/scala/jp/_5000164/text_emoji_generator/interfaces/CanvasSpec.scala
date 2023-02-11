package jp._5000164.text_emoji_generator.interfaces

import jp._5000164.text_emoji_generator.domain.{Center, Left}
import org.scalatest.featurespec.AnyFeatureSpec

class CanvasSpec extends AnyFeatureSpec {
  feature("左寄せの位置が計算できる") {
    scenario("1 行目と 2 行目が同じ文字数") {
      val text = """12
                   |34""".stripMargin
      assert(
        Canvas.calculatePrintChar(text, Left) === Seq(
          CharToPrint("1".codePointAt(0), 32.0, 32.0, 64.0, 64.0),
          CharToPrint("2".codePointAt(0), 96.0, 32.0, 64.0, 64.0),
          CharToPrint("3".codePointAt(0), 32.0, 96.0, 64.0, 64.0),
          CharToPrint("4".codePointAt(0), 96.0, 96.0, 64.0, 64.0)
        )
      )
    }

    scenario("1 行目よりも 2 行目の方が文字数が多い") {
      val text = """12
                   |345""".stripMargin
      assert(
        Canvas.calculatePrintChar(text, Left) === Seq(
          CharToPrint(
            "1".codePointAt(0),
            21.333333333333332,
            32.0,
            42.666666666666664,
            64.0
          ),
          CharToPrint("2".codePointAt(0), 64.0, 32.0, 42.666666666666664, 64.0),
          CharToPrint(
            "3".codePointAt(0),
            21.333333333333332,
            96.0,
            42.666666666666664,
            64.0
          ),
          CharToPrint("4".codePointAt(0), 64.0, 96.0, 42.666666666666664, 64.0),
          CharToPrint(
            "5".codePointAt(0),
            106.66666666666667,
            96.0,
            42.666666666666664,
            64.0
          )
        )
      )
    }
  }

  feature("中央寄せの計算ができる") {
    scenario("1 行目よりも 2 行目の方が文字数が少ない") {
      val text = """12
                   |3""".stripMargin
      assert(
        Canvas.calculatePrintChar(text, Center) === Seq(
          CharToPrint("1".codePointAt(0), 32.0, 32.0, 64.0, 64.0),
          CharToPrint("2".codePointAt(0), 96.0, 32.0, 64.0, 64.0),
          CharToPrint("3".codePointAt(0), 64.0, 96.0, 64.0, 64.0)
        )
      )
    }

    scenario("1 行目よりも 2 行目の方が文字数が多い") {
      val text = """12
                   |345""".stripMargin
      assert(
        Canvas.calculatePrintChar(text, Center) === Seq(
          CharToPrint(
            "1".codePointAt(0),
            42.66666666666667,
            32.0,
            42.666666666666664,
            64.0
          ),
          CharToPrint(
            "2".codePointAt(0),
            85.33333333333333,
            32.0,
            42.666666666666664,
            64.0
          ),
          CharToPrint(
            "3".codePointAt(0),
            21.333333333333332,
            96.0,
            42.666666666666664,
            64.0
          ),
          CharToPrint("4".codePointAt(0), 64.0, 96.0, 42.666666666666664, 64.0),
          CharToPrint(
            "5".codePointAt(0),
            106.66666666666667,
            96.0,
            42.666666666666664,
            64.0
          )
        )
      )
    }
  }

  feature("高さの計算ができる") {
    scenario("3 行") {
      val text = """1
                   |2
                   |3""".stripMargin
      assert(
        Canvas.calculatePrintChar(text, Left) === Seq(
          CharToPrint(
            "1".codePointAt(0),
            21.333333333333332,
            21.333333333333332,
            42.666666666666664,
            42.666666666666664
          ),
          CharToPrint(
            "2".codePointAt(0),
            21.333333333333332,
            64.0,
            42.666666666666664,
            42.666666666666664
          ),
          CharToPrint(
            "3".codePointAt(0),
            21.333333333333332,
            106.66666666666667,
            42.666666666666664,
            42.666666666666664
          )
        )
      )
    }
  }

  feature("空の入力でもエラーを起こさない") {
    scenario("入力が空") {
      val text = ""
      assert(
        Canvas.calculatePrintChar(text, Left) === Seq(
          CharToPrint(" ".codePointAt(0), 64.0, 64.0, 128.0, 128.0)
        )
      )
    }

    scenario("空行がある") {
      val text = """1
                   |
                   |2""".stripMargin
      assert(
        Canvas.calculatePrintChar(text, Left) === Seq(
          CharToPrint(
            "1".codePointAt(0),
            21.333333333333332,
            21.333333333333332,
            42.666666666666664,
            42.666666666666664
          ),
          CharToPrint(
            " ".codePointAt(0),
            21.333333333333332,
            64.0,
            42.666666666666664,
            42.666666666666664
          ),
          CharToPrint(
            "2".codePointAt(0),
            21.333333333333332,
            106.66666666666667,
            42.666666666666664,
            42.666666666666664
          )
        )
      )
    }
  }

  feature("[] で囲んだ部分は 1 文字分の幅になる") {
    scenario("行の先頭で挟む") {
      val text = "[12]3"
      assert(
        Canvas.calculatePrintChar(text, Left) === Seq(
          CharToPrint("1".codePointAt(0), 16.0, 64.0, 32.0, 128.0),
          CharToPrint("2".codePointAt(0), 48.0, 64.0, 32.0, 128.0),
          CharToPrint("3".codePointAt(0), 96.0, 64.0, 64.0, 128.0)
        )
      )
    }

    scenario("行の末尾で挟む") {
      val text = "1[23]"
      assert(
        Canvas.calculatePrintChar(text, Left) === Seq(
          CharToPrint("1".codePointAt(0), 32.0, 64.0, 64.0, 128.0),
          CharToPrint("2".codePointAt(0), 80.0, 64.0, 32.0, 128.0),
          CharToPrint("3".codePointAt(0), 112.0, 64.0, 32.0, 128.0)
        )
      )
    }

    scenario("行の中間で挟む") {
      val text = "1[23]4"
      assert(
        Canvas.calculatePrintChar(text, Left) === Seq(
          CharToPrint(
            "1".codePointAt(0),
            21.333333333333332,
            64.0,
            42.666666666666664,
            128.0
          ),
          CharToPrint(
            "2".codePointAt(0),
            53.333333333333336,
            64.0,
            21.333333333333332,
            128.0
          ),
          CharToPrint(
            "3".codePointAt(0),
            74.66666666666666,
            64.0,
            21.333333333333332,
            128.0
          ),
          CharToPrint(
            "4".codePointAt(0),
            106.66666666666667,
            64.0,
            42.666666666666664,
            128.0
          )
        )
      )
    }

    scenario("挟む文字数を 3 文字にする") {
      val text = "1[234]5"
      assert(
        Canvas.calculatePrintChar(text, Left) === Seq(
          CharToPrint(
            "1".codePointAt(0),
            21.333333333333332,
            64.0,
            42.666666666666664,
            128.0
          ),
          CharToPrint(
            "2".codePointAt(0),
            49.77777777777777,
            64.0,
            14.222222222222221,
            128.0
          ),
          CharToPrint(
            "3".codePointAt(0),
            64.0,
            64.0,
            14.222222222222221,
            128.0
          ),
          CharToPrint(
            "4".codePointAt(0),
            78.22222222222223,
            64.0,
            14.222222222222221,
            128.0
          ),
          CharToPrint(
            "5".codePointAt(0),
            106.66666666666667,
            64.0,
            42.666666666666664,
            128.0
          )
        )
      )
    }

    scenario("挟む文字数が空") {
      val text = "1[]"
      assert(
        Canvas.calculatePrintChar(text, Left) === Seq(
          CharToPrint("1".codePointAt(0), 64.0, 64.0, 128.0, 128.0)
        )
      )
    }

    scenario("[] だけで内容がない") {
      val text = "[]"
      assert(
        Canvas.calculatePrintChar(text, Left) === Seq(
          CharToPrint(" ".codePointAt(0), 64.0, 64.0, 128.0, 128.0)
        )
      )
    }
  }
}
