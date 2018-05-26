package jp._5000164.text_emoji_generator.domain

object Text {
  /** 配置できる枠の一辺の長さ */
  val side = 128

  /**
    * 位置を計算する。
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

  /**
    * 文字を解析する。
    *
    * @param text 入力された文字
    * @return 解析した文字
    */
  private def analysisText(text: String): Seq[Seq[RichChar]] = {
    val lines = text.split("\n").toSeq
    for (line <- lines) yield {
      if (line.contains("[") && line.contains("]")) {
        val dividedByStart = line.split('[')
        if (dividedByStart.length == 1) {
          val dividedByEnd = dividedByStart.head.split(']')
          if (dividedByEnd.length == 1) {
            line.map(RichChar(_, 1))
          } else {
            val surrounded = dividedByEnd.head
            val tail = dividedByEnd.last
            var result: Seq[RichChar] = Seq()
            result = result ++ surrounded.map(RichChar(_, surrounded.length))
            result = result ++ tail.map(RichChar(_, 1))
            result
          }
        } else {
          val dividedByEnd = dividedByStart.last.split(']')
          if (dividedByEnd.length == 1) {
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
        }
      } else {
        line.map(RichChar(_, 1))
      }
    }
  }

  /**
    * 文字の大きさを計算する。
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

  /**
    * 基本となる文字の高さを計算する。
    *
    * @param charMatrix 解析した文字
    * @return 基本となる文字の高さ
    */
  private def calculateUnitHeight(charMatrix: Seq[Seq[RichChar]]): Double = side / charMatrix.length

  /**
    * 基本となる文字の幅を計算する。
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

  /**
    * 文字の位置を計算する。
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

  /**
    * 行の開始位置までのマージンを計算する。
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
    ("Red", "F44336"),
    ("Pink", "E91E63"),
    ("Purple", "9C27B0"),
    ("Deep Purple", "673AB7"),
    ("Indigo", "3F51B5"),
    ("Blue", "2196F3"),
    ("Light Blue", "03A9F4"),
    ("Cyan", "00BCD4"),
    ("Teal", "009688"),
    ("Green", "4CAF50"),
    ("Light Green", "8BC34A"),
    ("Lime", "CDDC39"),
    ("Yellow", "FFEB3B"),
    ("Amber", "FFC107"),
    ("Orange", "FF9800"),
    ("Deep Orange", "FF5722"),
    ("Brown", "795548"),
    ("Grey", "9E9E9E"),
    ("Blue Grey", "607D8B"),
    ("Black", "000000")
  )
}

case class RichChar(
    char: Char,
    divisionNumber: Int)

case class CharSize(
    width: Double,
    height: Double)

case class CharPosition(
    x: Double,
    y: Double)
