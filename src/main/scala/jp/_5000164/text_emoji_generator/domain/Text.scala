package jp._5000164.text_emoji_generator.domain

object Text {
  /** 配置できる枠の一辺の長さ */
  val side = 128

  def calculateFontSize(lines: List[String]): Int = {
    val maxRow = lines.length
    side / maxRow
  }

  def calculatePosition(text: String, align: Align): (Seq[String], Seq[Seq[CharSize]], Seq[Seq[CharPosition]]) = {
    val lines = text.split("\n").toSeq
    val charSizeMatrix = calculateCharSize(lines)
    val charPositionMatrix = calculateCharPosition(charSizeMatrix, align)
    (lines, charSizeMatrix, charPositionMatrix)
  }

  /**
    * 文字の大きさを計算する
    *
    * @param lines 入力された内容
    * @return 文字ごとの大きさのマトリックス
    */
  private def calculateCharSize(lines: Seq[String]): Seq[Seq[CharSize]] = {
    val maxLength = lines.map(_.length).max
    val maxRow = lines.length
    val width = side / (if (maxLength > maxRow) maxLength else maxRow)
    val height = calculateFontSize(lines.toList)

    for (line <- lines) yield
      for (_ <- line) yield {
        CharSize(width, height)
      }
  }

  /**
    * 文字の位置を計算する
    *
    * @param charSizeMatrix 文字ごとの大きさのマトリックス
    * @param align          文字の位置揃え
    * @return 文字ごとの位置のマトリックス
    */
  private def calculateCharPosition(charSizeMatrix: Seq[Seq[CharSize]], align: Align): Seq[Seq[CharPosition]] = {
    var x = 0.0
    var y = 0.0

    for (charSizeList <- charSizeMatrix) yield {
      x = 0.0
      val height = charSizeList.head.height
      y = y + height
      val margin = calculateMargin(align, charSizeList)
      for (charSize <- charSizeList) yield {
        val width = charSize.width
        x = x + width
        CharPosition(
          x = margin + x - (width / 2),
          y = y - (height / 2)
        )
      }
    }
  }

  /**
    * 行の開始位置までのマージンを計算する
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

case class CharSize(
                     width: Double,
                     height: Double
                   )

case class CharPosition(
                         x: Double,
                         y: Double
                       )
