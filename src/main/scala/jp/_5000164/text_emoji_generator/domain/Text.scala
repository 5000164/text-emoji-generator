package jp._5000164.text_emoji_generator.domain

object Text {

  /** 配置できる枠の一辺の長さ */
  val side = 128.0

  /** 位置を計算する。
    *
    * @param text  入力された文字
    * @param align 位置揃え
    * @return 印字する文字、文字の大きさ、文字の位置
    */
  def calculatePosition(text: String, align: Align): (Seq[Seq[RichChar]], Seq[Seq[CharSize]], Seq[Seq[CharPosition]]) = {
    val charMatrix = analysisText(text)
    val charSizeMatrix = calculateCharSize(charMatrix)
    val charPositionMatrix = calculateCharPosition(charSizeMatrix, align)
    (charMatrix, charSizeMatrix, charPositionMatrix)
  }

  /** 文字を解析する。
    *
    * @param text 入力された文字
    * @return 解析した文字
    */
  private def analysisText(text: String): Seq[Seq[RichChar]] = {
    val lines = text.split("\n").toSeq
    for (line <- lines) yield {
      if (line == "") {
        Seq(RichChar(' ', 1))
      } else if (line.contains("[") && line.contains("]")) {
        val dividedByStart = line.split('[')
        val dividedByEnd = dividedByStart.last.split(']')
        if (dividedByEnd.isEmpty) {
          if (dividedByStart.head.isEmpty) {
            Seq(RichChar(' ', 1))
          } else {
            val head = dividedByStart.head
            var result: Seq[RichChar] = Seq()
            result = result ++ head.map(RichChar(_, 1))
            result
          }
        } else if (dividedByEnd.length == 1) {
          val head = dividedByStart.head
          val surrounded = dividedByEnd.head
          var result: Seq[RichChar] = Seq()
          result = result ++ head.map(RichChar(_, 1))
          result = result ++ surrounded.map(RichChar(_, surrounded.length))
          result
        } else {
          val head = dividedByStart.head
          val surrounded = dividedByEnd.head
          val tail = dividedByEnd.last

          var result: Seq[RichChar] = Seq()
          result = result ++ head.map(RichChar(_, 1))
          result = result ++ surrounded.map(RichChar(_, surrounded.length))
          result = result ++ tail.map(RichChar(_, 1))
          result
        }
      } else {
        // サロゲートペアを考慮
        var result = Seq[RichChar]()
        var skip = false
        for (index <- 0 until line.length) {
          if (skip) {
            skip = false
          } else {
            result :+= RichChar(line.codePointAt(index), 1)
          }

          if (
            line
              .substring(index, index + 1)
              .charAt(0)
              .isHighSurrogate
          ) {
            skip = true
          }
        }
        result
      }
    }
  }

  /** 文字の大きさを計算する。
    *
    * @param charMatrix 解析した文字
    * @return 文字ごとの大きさのマトリックス
    */
  private def calculateCharSize(charMatrix: Seq[Seq[RichChar]]): Seq[Seq[CharSize]] = {
    val unitHeight = calculateUnitHeight(charMatrix)
    val unitWidth = calculateUnitWidth(charMatrix, unitHeight)

    for (charList <- charMatrix) yield {
      for (char <- charList) yield {
        CharSize(unitWidth / char.divisionNumber, unitHeight)
      }
    }
  }

  /** 基本となる文字の高さを計算する。
    *
    * @param charMatrix 解析した文字
    * @return 基本となる文字の高さ
    */
  private def calculateUnitHeight(charMatrix: Seq[Seq[RichChar]]): Double = side / charMatrix.length

  /** 基本となる文字の幅を計算する。
    *
    * @param charMatrix 解析した文字
    * @param unitHeight 基本となる文字の高さ
    * @return 基本となる文字の幅
    */
  private def calculateUnitWidth(charMatrix: Seq[Seq[RichChar]], unitHeight: Double): Double = {
    val maxLength = charMatrix.map(_.map(1.0 / _.divisionNumber).sum).max
    val provisionalUnitWidth = side / maxLength

    // 文字の幅は文字の高さを超えて指定することはできない
    if (provisionalUnitWidth <= unitHeight) provisionalUnitWidth else unitHeight
  }

  /** 文字の位置を計算する。
    *
    * @param charSizeMatrix 文字ごとの大きさのマトリックス
    * @param align          文字の位置揃え
    * @return 文字ごとの位置のマトリックス
    */
  private def calculateCharPosition(charSizeMatrix: Seq[Seq[CharSize]], align: Align): Seq[Seq[CharPosition]] = {
    var endX = 0.0
    var endY = 0.0

    for (charSizeList <- charSizeMatrix) yield {
      endX = 0.0
      val height = charSizeList.head.height
      endY = endY + height
      val margin = calculateMargin(align, charSizeList)
      for (charSize <- charSizeList) yield {
        val width = charSize.width
        endX = endX + width
        CharPosition(
          x = margin + endX - (width / 2),
          y = endY - (height / 2)
        )
      }
    }
  }

  /** 行の開始位置までのマージンを計算する。
    *
    * @param align        文字の位置揃え
    * @param charSizeList 文字ごとの大きさのリスト
    * @return 行の開始位置までのマージン
    */
  private def calculateMargin(align: Align, charSizeList: Seq[CharSize]): Double = align match {
    case Left => 0
    case Center =>
      val totalWidth = charSizeList.map(_.width).sum
      (side - totalWidth) / 2
  }

  val colorList = List(
    ("Red", "ff3b30"),
    ("Orange", "ff9500"),
    ("Yellow", "ffcc00"),
    ("Green", "28cd41"),
    ("Mint", "00c7be"),
    ("Teal", "59adc4"),
    ("Cyan", "55bef0"),
    ("Blue", "007aff"),
    ("Indigo", "5856d6"),
    ("Purple", "af52de"),
    ("Pink", "ff2d55"),
    ("Brown", "a2845e"),
    ("Gray", "8e8e93"),
    ("Black", "000000")
  )
}

case class RichChar(codePoint: Int, divisionNumber: Int)

case class CharSize(width: Double, height: Double)

case class CharPosition(x: Double, y: Double)
